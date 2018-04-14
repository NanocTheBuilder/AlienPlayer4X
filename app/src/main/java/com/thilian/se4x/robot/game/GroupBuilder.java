package com.thilian.se4x.robot.game;

import com.thilian.se4x.robot.game.enums.ShipType;

public class GroupBuilder {

    protected void buildGroup(Fleet fleet, ShipType shipType) {
        buildGroup(fleet, shipType, 999);
    }

    protected void buildGroup(Fleet fleet, ShipType shipType, int maxToBuy) {
        int shipToBuy = fleet.getRemainingCP() / shipType.getCost();
        shipToBuy = shipToBuy > maxToBuy ? maxToBuy : shipToBuy;
        fleet.addGroup(new Group(shipType, shipToBuy));
    }

}
