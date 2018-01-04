package com.thilian.se4x.robot.game;

import static com.thilian.se4x.robot.game.enums.FleetType.DEFENSE_FLEET;
import static com.thilian.se4x.robot.game.enums.FleetType.RAIDER_FLEET;
import static com.thilian.se4x.robot.game.enums.ShipType.CARRIER;
import static com.thilian.se4x.robot.game.enums.ShipType.DESTROYER;
import static com.thilian.se4x.robot.game.enums.ShipType.FIGHTER;
import static com.thilian.se4x.robot.game.enums.ShipType.RAIDER;
import static com.thilian.se4x.robot.game.enums.ShipType.SCOUT;
import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static com.thilian.se4x.robot.game.enums.Technology.FIGHTERS;
import static com.thilian.se4x.robot.game.enums.Technology.POINT_DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.SCANNER;
import static com.thilian.se4x.robot.game.enums.Technology.SHIP_SIZE;

import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

public class FleetBuilder {

	private Game game;

	public FleetBuilder(Game game) {
		this.game = game;
	}

	public void buildFleet(Fleet fleet) { // WOWOWO THIS IS WRONG
		if (fleet.getFleetType().equals(RAIDER_FLEET)) {
			buildRaiderFleet(fleet);
		} else {
			if (shouldBuildCarrierFleet(fleet)) {
				buildCarrierFleet(fleet);
			}
			if (shouldBuildRaiderFleet(fleet)) {
				buildRaiderFleet(fleet);
			} else {
				buildFlaship(fleet);
				buildPossibleDD(fleet);
				buildRemainderFleet(fleet);
			}
		}

	}

	private void buildRemainderFleet(Fleet fleet) {
		if (fleet.canBuyMoreShips()) {
			AlienPlayer ap = fleet.getAp();
			int fleetCompositionRoll = game.roller.roll();
			boolean canUsePD = ap.getLevel(Technology.POINT_DEFENSE) > 0 && game.getSeenLevel(Technology.FIGHTERS) > 0;
			if (canUsePD)
				fleetCompositionRoll -= 2;

			if (fleetCompositionRoll <= 3) {
				buildBallanced(fleet, 1);
			} else {
				if (canUsePD && fleet.findGroup(CARRIER) == null) {
					int scoutsToBuild = Math.min(2, fleet.getRemainigCP() / SCOUT.getCost());
					fleet.addGroup(new Group(SCOUT, scoutsToBuild));
				}
				if (fleetCompositionRoll <= 6) {
					if (fleet.canBuyMoreShips())
						buildBallanced(fleet,
								Math.max(ap.getLevel(Technology.ATTACK), ap.getLevel(Technology.DEFENSE)));
				} else {
					while (fleet.canBuyMoreShips()) {
						ShipType shipType = ShipType.findBiggest(fleet.getRemainigCP(), ap.getLevel(SHIP_SIZE));
						fleet.addGroup(new Group(shipType, 1));
					}
				}
			}
		}
	}

	private void buildCarrierFleet(Fleet fleet) {
		int fleetCP = fleet.getFleetCP();
		int cost = CARRIER.getCost() + FIGHTER.getCost() * 3;
		int shipsToBuild = fleetCP / cost;
		fleet.addGroup(new Group(CARRIER, shipsToBuild));
		fleet.addGroup(new Group(FIGHTER, shipsToBuild * 3));
	}

	private void buildBallanced(Fleet fleet, int minHullSize) {
		for (int i = minHullSize; i >= 0; i--) {
			ShipType cheapestType = ShipType.findCheapest(i);
			int apShipSize = fleet.getAp().getLevel(SHIP_SIZE);
			if (apShipSize >= cheapestType.getRequiredShipSize()) {
				for (ShipType biggerType : ShipType.getBiggerTypesInReverse(cheapestType)) {
					if (apShipSize >= biggerType.getRequiredShipSize()) {
						int remainder = fleet.getRemainigCP() % cheapestType.getCost();
						int difference = biggerType.getCost() - cheapestType.getCost();
						int shipType2ToBuy = remainder / difference;
						fleet.addGroup(new Group(biggerType, shipType2ToBuy));
					}
				}
				buildRemaining(fleet, cheapestType);
			}
		}
	}

	private void buildPossibleDD(Fleet fleet) {
		if (fleet.getRemainigCP() >= 9) {
			AlienPlayer ap = fleet.getAp();
			if (ShipType.canBuild(fleet.getRemainigCP(), ap.getLevel(SHIP_SIZE), DESTROYER)
					&& game.getSeenLevel(CLOAKING) <= ap.getLevel(SCANNER) && fleet.findGroup(DESTROYER) == null)
				fleet.addGroup(new Group(DESTROYER, 1));
		}
	}

	private void buildRaiderFleet(Fleet fleet) {
		fleet.setFleetType(RAIDER_FLEET);
		buildRemaining(fleet, RAIDER);
	}

	private void buildFlaship(Fleet fleet) {
		if (fleet.canBuyMoreShips()) {
			ShipType shipType = ShipType.findBiggest(fleet.getRemainigCP(), fleet.getAp().getLevel(SHIP_SIZE));
			fleet.addGroup(new Group(shipType, 1));
		}
	}

	private boolean shouldBuildCarrierFleet(Fleet fleet) {
		return fleet.getFleetCP() >= 27 && fleet.getAp().getLevel(FIGHTERS) > 0
				&& (game.getSeenLevel(POINT_DEFENSE) == 0 || game.roller.roll() < 5);
	}

	private boolean shouldBuildRaiderFleet(Fleet fleet) {
		// TODO more test for this. Especially the resetting of isPurchasedThisTurn
		if(fleet.getFleetType().equals(DEFENSE_FLEET))
			return false;
		AlienPlayer ap = fleet.getAp();
		return fleet.getGroups().size() == 0 && 
				fleet.getFleetCP() >= RAIDER.getCost() && 
				ap.isJustPurchasedCloaking() && 
				ap.getLevel(CLOAKING) > game.getSeenLevel(SCANNER);
	}

	private void buildRemaining(Fleet fleet, ShipType shipType) {
		int shipsToBuild = fleet.getRemainigCP() / shipType.getCost();
		if (shipsToBuild > 0) {
			fleet.addGroup(new Group(shipType, shipsToBuild));
		}
	}

}
