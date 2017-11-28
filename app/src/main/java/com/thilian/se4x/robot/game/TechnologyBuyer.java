package com.thilian.se4x.robot.game;

import static com.thilian.se4x.robot.game.enums.FleetType.DEFENSE_FLEET;
import static com.thilian.se4x.robot.game.enums.FleetType.RAIDER_FLEET;
import static com.thilian.se4x.robot.game.enums.Technology.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.Technology;

public class TechnologyBuyer {

	private static int[] SHIP_SIZE_ROLL_TABLE = new int[] { 0, 10, 7, 6, 5, 3 };

	private static Map<Technology, Integer[]> TECHNOLOGY_ROLL_TABLE = new HashMap<>();
	{
		addToRollTable(ATTACK, 1, 2);
		addToRollTable(DEFENSE, 3, 4);
		addToRollTable(TACTICS, 5);
		addToRollTable(CLOAKING, 6);
		addToRollTable(SCANNER, 7);
		addToRollTable(FIGHTERS, 8);
		addToRollTable(POINT_DEFENSE, 9);
		addToRollTable(MINE_SWEEP, 10);
	}

	private static void addToRollTable(Technology technology, Integer... values) {
		TECHNOLOGY_ROLL_TABLE.put(technology, values);
	}

	private Game game;

	public TechnologyBuyer(Game game) {
		this.game = game;
	}

	public void buyTechs(AlienPlayer ap, FleetType fleetType) {
		buyPointDefenseIfNeeded(ap);
		buyMineSweepIfNeeded(ap);
		buyScannerIfNeeded(ap);
		buyShipSizeIfRolled(ap);
		buyFightersIfNeeded(ap);
		buyCloakingIfNeeded(ap, fleetType);

		spendRemainingTechCP(ap, fleetType);
	}

	void spendRemainingTechCP(AlienPlayer ap, FleetType fleetType) {
		while (true) {
			List<Integer> buyable = findBuyableTechs(ap, fleetType);
			if(buyable.isEmpty())
				break;
			int roll = game.roller.roll(buyable.size());
			switch (buyable.get(roll - 1)) {
			case 1:
			case 2:
				buyNextLevel(ap, ATTACK);
				break;
			case 3:
			case 4:
				buyNextLevel(ap, DEFENSE);
				break;
			case 5:
				if (ap.getLevel(ATTACK) < 2 && canBuyNextLevel(ap, ATTACK))
					buyNextLevel(ap, ATTACK);
				else if (ap.getLevel(DEFENSE) < 2 && canBuyNextLevel(ap, DEFENSE))
					buyNextLevel(ap, DEFENSE);
				else
					buyNextLevel(ap, TACTICS);
				break;
			case 6:
				buyNextLevel(ap, CLOAKING);
				ap.setJustPurchasedCloaking(true);
				break;
			case 7:
				buyNextLevel(ap, SCANNER);
				break;
			case 8:
				buyNextLevel(ap, FIGHTERS);
				break;
			case 9:
				buyNextLevel(ap, POINT_DEFENSE);
				break;
			case 10:
				buyNextLevel(ap, MINE_SWEEP);
				break;
			}
		}
	}

	private List<Integer> findBuyableTechs(AlienPlayer ap, FleetType fleetType) {
		List<Integer> buyable = new ArrayList<>();
		for (Technology technology : TECHNOLOGY_ROLL_TABLE.keySet()) {
			if (canBuyNextLevel(ap, fleetType, technology)) {
				buyable.addAll(Arrays.asList(TECHNOLOGY_ROLL_TABLE.get(technology)));
			}
		}
		Collections.sort(buyable);
		return buyable;
	}

	void buyCloakingIfNeeded(AlienPlayer ap, FleetType fleetType) {
		if (fleetType.equals(RAIDER_FLEET) && ap.getLevel(CLOAKING) != 0) {
			if (game.roller.roll() <= 6)
				buyNextLevel(ap, CLOAKING);
		}
	}

	void buyFightersIfNeeded(AlienPlayer ap) {
		if (ap.getSeenLevel(POINT_DEFENSE) == 0 && ap.getLevel(FIGHTERS) != 0)
			if (game.roller.roll() <= 6)
				buyNextLevel(ap, FIGHTERS);
	}

	void buyShipSizeIfRolled(AlienPlayer ap) {
		if (ap.getLevel(SHIP_SIZE) < game.technologyPrices.getMaxLevel(SHIP_SIZE))
			if (game.roller.roll() <= SHIP_SIZE_ROLL_TABLE[ap.getLevel(SHIP_SIZE)])
				buyNextLevel(ap, SHIP_SIZE);
	}

	void buyScannerIfNeeded(AlienPlayer ap) {
		if (ap.getSeenLevel(CLOAKING) > ap.getLevel(SCANNER)) {
			if (game.roller.roll() <= 4) {
				int levelsNeeded = ap.getSeenLevel(CLOAKING) - ap.getLevel(SCANNER);
				for (int i = 0; i < levelsNeeded; i++)
					buyNextLevel(ap, SCANNER);
			}
		}
	}

	void buyMineSweepIfNeeded(AlienPlayer ap) {
		if (ap.getSeenLevel(MINES) > 0 && ap.getLevel(MINE_SWEEP) == 0) {
			buyNextLevel(ap, MINE_SWEEP);
		}
	}

	void buyPointDefenseIfNeeded(AlienPlayer ap) {
		if (ap.getSeenLevel(FIGHTERS) > 0 && ap.getLevel(POINT_DEFENSE) == 0) {
			buyNextLevel(ap, POINT_DEFENSE);
		}
	}

	public void buyNextLevel(AlienPlayer ap, Technology technology) {
		int currentLevel = ap.getLevel(technology);
		if (canBuyNextLevel(ap, technology)) {
			int nextLevel = currentLevel + 1;
			int cost = game.technologyPrices.getCost(technology, nextLevel);
			ap.setLevel(technology, nextLevel);
			ap.getEconomicSheet().techCP -= cost;
		}
	}

	boolean canBuyNextLevel(AlienPlayer ap, Technology technology) {
		int currentLevel = ap.getLevel(technology);
		if (technology.equals(TACTICS)) {
			int min = Math.min(ap.getLevel(ATTACK), ap.getLevel(DEFENSE));
			if (min < 2) {
				return canBuyNextLevel(ap, ATTACK) | canBuyNextLevel(ap, DEFENSE);
			}
		}
		if (technology.equals(CLOAKING) && ap.getSeenLevel(SCANNER) == game.technologyPrices.getMaxLevel(SCANNER)) {
			return false;
		}

		return currentLevel < game.technologyPrices.getMaxLevel(technology)
				&& ap.getEconomicSheet().techCP >= game.technologyPrices.getCost(technology,currentLevel + 1);
	}

	boolean canBuyNextLevel(AlienPlayer ap, FleetType fleetType, Technology technology) {
		if(fleetType.equals(DEFENSE_FLEET) && technology.equals(MINE_SWEEP))
			return false;
		else
			return canBuyNextLevel(ap, technology);
	}
}
