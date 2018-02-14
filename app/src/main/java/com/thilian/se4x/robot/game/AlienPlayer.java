package com.thilian.se4x.robot.game;

//import android.support.annotation.Nullable;
//import android.util.Log;

import static com.thilian.se4x.robot.game.enums.FleetType.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.PlayerColor;
import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

public class AlienPlayer {

	private List<Fleet> fleets = new ArrayList<>();
	private AlienEconomicSheet economicSheet;
	private Game game;
	private PlayerColor color;
	private Map<Technology, Integer> technologyLevels = new HashMap<>();

	private boolean purchasedCloakThisTurn = false;

	public AlienPlayer(AlienEconomicSheet sheet, Game game, PlayerColor color) {
		this.economicSheet = sheet;
		this.game = game;
		this.color = color;

		for (Technology technology : game.technologyPrices.getAvailableTechs()) {
			int startingLevel = game.technologyPrices.getStartingLevel(technology);
			technologyLevels.put(technology, startingLevel);
		}
	}

	public AlienEconomicSheet getEconomicSheet() {
		return this.economicSheet;
	}

	public Fleet makeEconRoll(int turn) {
		for (int i = 0; i < economicSheet.getEconRolls(turn) + economicSheet.getExtraEcon(turn); i++)
			economicSheet.applyRoll(turn, game.roller.roll());
		Fleet fleet = rollFleetLaunch(turn);
		if (fleet != null){
			buyNextMoveLevel();
		}
	return fleet;
	}

	void buyTechs(Fleet fleet, Map<String, Object> params) {
		setJustPurchasedCloaking(false);
		game.techBuyer.buyTechs(fleet, params);
	}

	public void buildFleet(Fleet fleet, Map<String, Object> params) {
		buyTechs(fleet, params);
		game.fleetBuilder.buildFleet(fleet);
		economicSheet.addFleetCP(fleet.getFleetCP() - fleet.getBuildCost());
	}

	public void destroyFleet(Fleet fleet) {
		fleets.remove(fleet);
	}

	Fleet rollFleetLaunch(int turn) {
		int currentFleetCP = economicSheet.getFleetCP();
		if (currentFleetCP >= ShipType.SCOUT.getCost()) {
			int roll = getFleetLaunchRoll(currentFleetCP);
			if (roll <= economicSheet.getFleetLaunch(turn)) {
				Fleet fleet = new Fleet(this, REGULAR_FLEET, currentFleetCP);
				if (shouldBuildRaiderFleet(currentFleetCP)) {
					fleet.setFleetType(RAIDER_FLEET);
					game.fleetBuilder.buildFleet(fleet);
				}
				int cpSpent = fleet.getFleetType().equals(RAIDER_FLEET) ? fleet.getBuildCost() : fleet.getFleetCP();
				economicSheet.spendFleetCP(cpSpent);
				return fleet;
			}
		}
		return null;
	}

	public List<Fleet> buildHomeDefense(Map<String, Object> params) {
		List<Fleet> newFleets = new ArrayList<>();
		int currentFleetCP = economicSheet.getFleetCP();
		if (currentFleetCP >= ShipType.SCOUT.getCost()) {
			Fleet fleet = new Fleet(this, DEFENSE_FLEET, currentFleetCP);
			buyTechs(fleet, params);
			game.fleetBuilder.buildFleet(fleet);
			fleet.setFleetType(REGULAR_FLEET);
			economicSheet.spendFleetCP(fleet.getBuildCost());
			newFleets.add(fleet);
		}

		if (economicSheet.getDefCP() >= ShipType.MINE.getCost()) {
			Fleet fleet = game.defenseBuilder.buildHomeDefense(this);
//			if(newFleets.isEmpty()){
//				buyTechs(fleet, params); //TODO unitTest this
//			}
			economicSheet.spendDefCP(fleet.getBuildCost());
			newFleets.add(fleet);
		}
		return newFleets;
	}

	void buyNextMoveLevel() {
		if (game.roller.roll() <= 4) {
			game.techBuyer.buyNextLevel(this, Technology.MOVE);
		}
	}

	private int getFleetLaunchRoll(int currentFleetCP) {
		int roll = game.roller.roll();
		if ((currentFleetCP >= 25
				&& technologyLevels.get(Technology.FIGHTERS) > game.getSeenLevel(Technology.POINT_DEFENSE))
				|| shouldBuildRaiderFleet(currentFleetCP))
			roll -= 2;
		return roll;
	}

	private boolean shouldBuildRaiderFleet(int currentFleetCP) {
		return currentFleetCP >= 12 && technologyLevels.get(Technology.CLOAKING) > game.getSeenLevel(Technology.SCANNER);
	}

	public List<Fleet> getFleets() {
		return fleets;
	}

	boolean isJustPurchasedCloaking() {
		return purchasedCloakThisTurn;
	}

	public void setJustPurchasedCloaking(boolean purchasedCloakThisTurn) {
		this.purchasedCloakThisTurn = purchasedCloakThisTurn;
	}

	public int getLevel(Technology technology) {
		return technologyLevels.get(technology);
	}

	public void setLevel(Technology technology, int level) {
		technologyLevels.put(technology, level);
	}

	public PlayerColor getColor() {
		return color;
	}

	public String findFleetName(FleetType fleetType) {
		for (int i = 1; i < 100; i++) {
			if (findFleetByName(String.valueOf(i), fleetType) == null) {
				return String.valueOf(i);
			}
		}
		return "?";
	}

//	@Nullable
	private Fleet findFleetByName(String name, FleetType fleetType) {
		for(Fleet fleet : fleets){
			if(fleet.getName().equals(name) && fleet.getFleetType().equals(fleetType))
				return fleet;
		}
		return null;
	}

}
