package com.thilian.se4x.robot.game.scenario4;

import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

public class DefenseBuilder extends com.thilian.se4x.robot.game.DefenseBuilder {

	public DefenseBuilder(Game game) {
		super(game);
	}
	
	@Override
	protected void buyHomeDefenseUnits(Fleet fleet) {
		if(fleet.getRemainigCP() > 25){
			buyGravArmor(fleet);
			buyHeavyInfantry(fleet);
		}
		super.buyHomeDefenseUnits(fleet);
	}

	private void buyGravArmor(Fleet fleet) {
		if(fleet.getAp().getLevel(Technology.GROUND_COMBAT) == 3){
			fleet.addGroup(new Group(ShipType.GRAV_ARMOR, 2));
		}
	}

	private void buyHeavyInfantry(Fleet fleet) {
		if(fleet.getAp().getLevel(Technology.GROUND_COMBAT) >= 2){
			int howManyHI = game.roller.roll();
			fleet.addGroup(new Group(ShipType.HEAVY_INFANTRY, howManyHI));
		}
	}
}
