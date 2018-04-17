package com.thilian.se4x.robot.game;

import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

import static com.thilian.se4x.robot.game.enums.FleetBuildOption.HOME_DEFENSE;
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

public class FleetBuilder extends GroupBuilder {

    protected Game game;

    public FleetBuilder(Game game) {
        this.game = game;
    }

    public void buildFleet(Fleet fleet, FleetBuildOption... options) {
        if (fleet.getFleetType().equals(RAIDER_FLEET)) {
            buildRaiderFleet(fleet);
        } else {
            buyFullCarriers(fleet);
            if (shouldBuildRaiderFleet(fleet, options)) {
                buildRaiderFleet(fleet);
            } else {
                buildFlagship(fleet);
                buildPossibleDD(fleet);
                buildRemainderFleet(fleet);
            }
        }

    }

    protected void buildRemainderFleet(Fleet fleet) {
        if (fleet.canBuyMoreShips()) {
            AlienPlayer ap = fleet.getAp();
            int fleetCompositionRoll = game.roller.roll();
            boolean canUsePD = ap.getLevel(Technology.POINT_DEFENSE) > 0 && game.isSeenThing(Seeable.FIGHTERS);
            if (canUsePD){
                fleetCompositionRoll -= 2;
                if(fleet.findGroup(CARRIER) == null){
                    buildGroup(fleet, SCOUT, 2);
                }
            }

            if (fleetCompositionRoll <= 3) {
                buildBallanced(fleet, 1);
            } else if (fleetCompositionRoll <= 6) {
                    if (fleet.canBuyMoreShips())
                        buildBallanced(fleet,
                                Math.max(ap.getLevel(Technology.ATTACK), ap.getLevel(Technology.DEFENSE)));
            } else {
                while (fleet.canBuyMoreShips()) {
                    buildGroup(fleet, ShipType.findBiggest(fleet.getRemainingCP(), ap.getLevel(SHIP_SIZE)));
                }
            }
        }
    }

    protected void buyFullCarriers(Fleet fleet) {
        if (shouldBuildCarrierFleet(fleet)) {
            buildCarrierFleet(fleet);
        }
    }

    protected void buildCarrierFleet(Fleet fleet) {
        int fleetCP = fleet.getFleetCP();
        int cost = CARRIER.getCost() + FIGHTER.getCost() * 3;
        int shipsToBuild = fleetCP / cost;
        fleet.addGroup(new Group(CARRIER, shipsToBuild));
        fleet.addGroup(new Group(FIGHTER, shipsToBuild * 3));
    }

    //TODO: FIND A READABLE ALGORITHM?
    private void buildBallanced(Fleet fleet, int minHullSize) {
        int apShipSize = fleet.getAp().getLevel(SHIP_SIZE);
        for (int i = minHullSize; i >= 0; i--) {
            ShipType cheapestType = ShipType.findCheapest(i);
            if (apShipSize >= cheapestType.getRequiredShipSize()) {
                for (ShipType biggerType : ShipType.getBiggerTypesInReverse(cheapestType)) {
                    if (apShipSize >= biggerType.getRequiredShipSize() && fleet.getRemainingCP() >= biggerType.getCost()) {
                        int remainder = fleet.getRemainingCP() % cheapestType.getCost();
                        int difference = biggerType.getCost() - cheapestType.getCost();
                        int shipType2ToBuy = remainder / difference;
                        buildGroup(fleet, biggerType, shipType2ToBuy);
                    }
                }
                buildGroup(fleet, cheapestType);
            }
        }
    }

    protected void buildPossibleDD(Fleet fleet) {
        if (fleet.getRemainingCP() >= 9) {
            AlienPlayer ap = fleet.getAp();
            if (DESTROYER.canBeBuilt(fleet.getRemainingCP(), ap.getLevel(SHIP_SIZE))
                    && game.getSeenLevel(CLOAKING) <= ap.getLevel(SCANNER) && fleet.findGroup(DESTROYER) == null)
                fleet.addGroup(new Group(DESTROYER, 1));
        }
    }

    protected void buildRaiderFleet(Fleet fleet) {
        fleet.setFleetType(RAIDER_FLEET);
        buildGroup(fleet, RAIDER);
    }

    protected void buildFlagship(Fleet fleet) {
        if (fleet.canBuyMoreShips()) {
            ShipType shipType = ShipType.findBiggest(fleet.getRemainingCP(), fleet.getAp().getLevel(SHIP_SIZE));
            fleet.addGroup(new Group(shipType, 1));
        }
    }

    protected boolean shouldBuildCarrierFleet(Fleet fleet) {
        return fleet.getFleetCP() >= 27 && fleet.getAp().getLevel(FIGHTERS) > 0
                && (game.getSeenLevel(POINT_DEFENSE) == 0 || game.roller.roll() < 5);
    }

    protected boolean shouldBuildRaiderFleet(Fleet fleet, FleetBuildOption... options) {
        // TODO more test for this. Especially the resetting of
        // isPurchasedThisTurn
        if (FleetBuildOption.isOption(HOME_DEFENSE, options))
            return false;
        AlienPlayer ap = fleet.getAp();
        return fleet.getGroups().size() == 0 && fleet.getFleetCP() >= RAIDER.getCost() && ap.isJustPurchasedCloaking()
                && ap.getLevel(CLOAKING) > game.getSeenLevel(SCANNER);
    }

}
