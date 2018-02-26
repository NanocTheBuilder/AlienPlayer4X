package com.thilian.se4x.robot.game.scenario4;

import static com.thilian.se4x.robot.game.enums.FleetType.RAIDER_FLEET;

import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

public class FleetBuilder extends com.thilian.se4x.robot.game.FleetBuilder {

    public FleetBuilder(Game game) {
        super(game);
    };

    public void buildFleet(Fleet fleet) {
        if (fleet.getFleetType().equals(RAIDER_FLEET) || shouldBuildRaiderFleet(fleet)) {
            buildRaiderFleet(fleet);
        } else {
            buildOneFullyLoadedTransport(fleet);
            buyBoardingShips(fleet);
            buyScoutsIfSeenMines(fleet);
            buyFullCarriers(fleet);
            buildFlagship(fleet);
            buildPossibleDD(fleet);
            buildRemainderFleet(fleet);
        }
    }

    private void buyBoardingShips(Fleet fleet) {
        if (fleet.getAp().getLevel(Technology.BOARDING) != 0) {
            addGroup(fleet, ShipType.BOARDING_SHIP, 2);
        }
    }

    private void buyScoutsIfSeenMines(Fleet fleet) {
        if (game.isSeenThing(Seeable.MINES)) {
            addGroup(fleet, ShipType.SCOUT, 2);
        }
    }

    private void buildOneFullyLoadedTransport(Fleet fleet) {
        fleet.addFreeGroup(new Group(ShipType.TRANSPORT, 1));
        switch (fleet.getAp().getLevel(Technology.GROUND_COMBAT)) {
        default:
            fleet.addFreeGroup(new Group(ShipType.INFANTRY, 6));
            break;
        case 2:
            fleet.addFreeGroup(new Group(ShipType.MARINE, 5));
            fleet.addFreeGroup(new Group(ShipType.HEAVY_INFANTRY, 1));
            break;
        case 3:
            fleet.addFreeGroup(new Group(ShipType.MARINE, 4));
            fleet.addFreeGroup(new Group(ShipType.HEAVY_INFANTRY, 1));
            fleet.addFreeGroup(new Group(ShipType.GRAV_ARMOR, 1));
            break;
        }
    }

}
