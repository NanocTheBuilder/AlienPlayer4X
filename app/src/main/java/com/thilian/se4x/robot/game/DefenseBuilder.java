package com.thilian.se4x.robot.game;

import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.ShipType;

public class DefenseBuilder {

	private Game game;

	public DefenseBuilder(Game game) {
		this.game = game;
	}

	public Fleet buildDefense(AlienPlayer ap) {
		Fleet fleet = new Fleet(ap, FleetType.DEFENSE_FLEET, ap.getEconomicSheet().defCP);
		int roll = game.roller.roll();
		if (roll < 4) {
			addGroup(fleet, ShipType.MINE);
		} else if (roll < 8) {
			while (fleet.getRemainigCP() >= ShipType.MINE.getCost()) {
				if (fleet.getRemainigCP() >= ShipType.BASE.getCost())
					fleet.addGroup(new Group(ShipType.BASE, 1));
				if (fleet.getRemainigCP() >= ShipType.MINE.getCost())
					fleet.addGroup(new Group(ShipType.MINE, 1));
			}
		} else {
			addGroup(fleet, ShipType.BASE);
			addGroup(fleet, ShipType.MINE);
		}
		return fleet;
	}

	private void addGroup(Fleet fleet, ShipType shipType) {
		int shipToBuild = fleet.getRemainigCP() / shipType.getCost();
		fleet.addGroup(new Group(shipType, shipToBuild));
	}

}
