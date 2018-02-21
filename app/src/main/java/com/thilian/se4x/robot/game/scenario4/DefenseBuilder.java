package com.thilian.se4x.robot.game.scenario4;

import javax.print.attribute.standard.SheetCollate;

import static com.thilian.se4x.robot.game.enums.ShipType.*;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.FleetType;
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
			fleet.addGroup(new Group(GRAV_ARMOR, 2));
		}
	}

	private void buyHeavyInfantry(Fleet fleet) {
		if(fleet.getAp().getLevel(Technology.GROUND_COMBAT) >= 2){
			int howManyHI = game.roller.roll();
			fleet.addGroup(new Group(HEAVY_INFANTRY, howManyHI));
		}
	}

	public Fleet buildColonyDefense(AlienPlayer ap) {
		int defCP = ap.getEconomicSheet().getDefCP();
		int maxCP = game.roller.roll() + game.roller.roll();
		maxCP = maxCP < defCP ? maxCP : defCP;
		Fleet fleet = new Fleet(ap, FleetType.DEFENSE_FLEET, maxCP);
		if(game.roller.roll() < 6)
			addGroup(fleet, BASE, 1);
		else
			addGroup(fleet, MINE, 2);
		return fleet;
	}
	
	//TODO copy pasted from FleetBuilder
	private void addGroup(Fleet fleet, ShipType shipType, int maxToBuy) {
		int shipToBuy = fleet.getFleetCP() / shipType.getCost();
		shipToBuy = shipToBuy > maxToBuy ? maxToBuy : shipToBuy;
		fleet.addGroup(new Group(shipType, shipToBuy));
	}	
}
