package com.thilian.se4x.robot.game;

import static com.thilian.se4x.robot.game.enums.FleetType.RAIDER_FLEET;
import static com.thilian.se4x.robot.game.enums.Technology.ATTACK;
import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static com.thilian.se4x.robot.game.enums.Technology.DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.FIGHTERS;
import static com.thilian.se4x.robot.game.enums.Technology.MINE_SWEEP;
import static com.thilian.se4x.robot.game.enums.Technology.POINT_DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.SCANNER;
import static com.thilian.se4x.robot.game.enums.Technology.TACTICS;

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

	public void buyTechs(Fleet fleet) {
		AlienPlayer ap = fleet.getAp();
		buyPointDefenseIfNeeded(ap);
		buyMineSweepIfNeeded(ap);
		buyScannerIfNeeded(ap);
		buyShipSizeIfRolled(ap);
		buyFightersIfNeeded(ap);
		buyCloakingIfNeeded(fleet);

		spendRemainingTechCP(fleet);
	}

	void spendRemainingTechCP(Fleet fleet) {
		AlienPlayer ap = fleet.getAp();
		while (true) {
			List<Integer> buyable = findBuyableTechs(fleet);
			if(buyable.isEmpty())
				break;
			int roll = game.roller.roll(buyable.size());
			switch (buyable.get(roll - 1)) {
			case 1:
			case 2:
				buyNextLevel(ap, Technology.ATTACK);
				break;
			case 3:
			case 4:
				buyNextLevel(ap, Technology.DEFENSE);
				break;
			case 5:
				if (ap.getLevel(Technology.ATTACK) < 2 && canBuyNextLevel(ap, Technology.ATTACK))
					buyNextLevel(ap, Technology.ATTACK);
				else if (ap.getLevel(Technology.DEFENSE) < 2 && canBuyNextLevel(ap, Technology.DEFENSE))
					buyNextLevel(ap, Technology.DEFENSE);
				else
					buyNextLevel(ap, Technology.TACTICS);
				break;
			case 6:
				buyNextLevel(ap, Technology.CLOAKING);
				ap.setJustPurchasedCloaking(true);
				break;
			case 7:
				buyNextLevel(ap, Technology.SCANNER);
				break;
			case 8:
				buyNextLevel(ap, Technology.FIGHTERS);
				break;
			case 9:
				buyNextLevel(ap, Technology.POINT_DEFENSE);
				break;
			case 10:
				buyNextLevel(ap, Technology.MINE_SWEEP);
				break;
			}
		}
	}

	private List<Integer> findBuyableTechs(Fleet fleet) {
		List<Integer> buyable = new ArrayList<>();
		for (Technology technology : TECHNOLOGY_ROLL_TABLE.keySet()) {
			if (canBuyNextLevel(fleet, technology)) {
				buyable.addAll(Arrays.asList(TECHNOLOGY_ROLL_TABLE.get(technology)));
			}
		}
		Collections.sort(buyable);
		return buyable;
	}

	void buyCloakingIfNeeded(Fleet fleet) {
		AlienPlayer ap = fleet.getAp();
		if (fleet.getFleetType().equals(RAIDER_FLEET) && ap.getLevel(Technology.CLOAKING) != 0) {
			if (game.roller.roll() <= 6)
				buyNextLevel(ap, Technology.CLOAKING);
		}
	}

	void buyFightersIfNeeded(AlienPlayer ap) {
		if (ap.getSeenLevel(Technology.POINT_DEFENSE) == 0 && ap.getLevel(Technology.FIGHTERS) != 0)
			if (game.roller.roll() <= 6)
				buyNextLevel(ap, Technology.FIGHTERS);
	}

	void buyShipSizeIfRolled(AlienPlayer ap) {
		if (ap.getLevel(Technology.SHIP_SIZE) < Technology.SHIP_SIZE.getMaxLevel())
			if (game.roller.roll() <= SHIP_SIZE_ROLL_TABLE[ap.getLevel(Technology.SHIP_SIZE)])
				buyNextLevel(ap, Technology.SHIP_SIZE);
	}

	void buyScannerIfNeeded(AlienPlayer ap) {
		if (ap.getSeenLevel(Technology.CLOAKING) > ap.getLevel(Technology.SCANNER)) {
			if (game.roller.roll() <= 4) {
				int levelsNeeded = ap.getSeenLevel(Technology.CLOAKING) - ap.getLevel(Technology.SCANNER);
				for (int i = 0; i < levelsNeeded; i++)
					buyNextLevel(ap, Technology.SCANNER);
			}
		}
	}

	void buyMineSweepIfNeeded(AlienPlayer ap) {
		if (ap.getSeenLevel(Technology.MINES) > 0 && ap.getLevel(Technology.MINE_SWEEP) == 0) {
			buyNextLevel(ap, Technology.MINE_SWEEP);
		}
	}

	void buyPointDefenseIfNeeded(AlienPlayer ap) {
		if (ap.getSeenLevel(Technology.FIGHTERS) > 0 && ap.getLevel(Technology.POINT_DEFENSE) == 0) {
			buyNextLevel(ap, Technology.POINT_DEFENSE);
		}
	}

	public void buyNextLevel(AlienPlayer ap, Technology technology) {
		int currentLevel = ap.getLevel(technology);
		if (canBuyNextLevel(ap, technology)) {
			int nextLevel = currentLevel + 1;
			int cost = technology.getCost(nextLevel);
			ap.setLevel(technology, nextLevel);
			ap.getEconomicSheet().techCP -= cost;
		}
	}

	boolean canBuyNextLevel(AlienPlayer ap, Technology technology) {
		int currentLevel = ap.getLevel(technology);
		if (technology.equals(Technology.TACTICS)) {
			int min = Math.min(ap.getLevel(Technology.ATTACK), ap.getLevel(Technology.DEFENSE));
			if (min < 2) {
				return canBuyNextLevel(ap, Technology.ATTACK) | canBuyNextLevel(ap, Technology.DEFENSE);
			}
		}
		if (technology.equals(Technology.CLOAKING) && ap.getSeenLevel(Technology.SCANNER) == Technology.SCANNER.getMaxLevel()) {
			return false;
		}

		return currentLevel < technology.getMaxLevel()
				&& ap.getEconomicSheet().techCP >= technology.getCost(currentLevel + 1);
	}

	boolean canBuyNextLevel(Fleet fleet, Technology technology) {
		if(fleet.getFleetType().equals(FleetType.DEFENSE_FLEET) && technology.equals(MINE_SWEEP))
			return false;
		else
			return canBuyNextLevel(fleet.getAp(), technology);
	}
}
