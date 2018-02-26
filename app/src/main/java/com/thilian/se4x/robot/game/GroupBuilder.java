package com.thilian.se4x.robot.game;

import com.thilian.se4x.robot.game.enums.ShipType;

public class GroupBuilder {

	protected void addGroup(Fleet fleet, ShipType shipType) {
		addGroup(fleet, shipType, 999);
	}

	protected void addGroup(Fleet fleet, ShipType shipType, int maxToBuy) {
		int shipToBuy = fleet.getRemainigCP() / shipType.getCost();
		shipToBuy = shipToBuy > maxToBuy ? maxToBuy : shipToBuy;
		fleet.addGroup(new Group(shipType, shipToBuy));
	}

}
