package com.thilian.se4x.robot.game.scenario4;

import static com.thilian.se4x.robot.game.enums.ShipType.BASE;
import static com.thilian.se4x.robot.game.enums.ShipType.GRAV_ARMOR;
import static com.thilian.se4x.robot.game.enums.ShipType.HEAVY_INFANTRY;
import static com.thilian.se4x.robot.game.enums.ShipType.INFANTRY;
import static com.thilian.se4x.robot.game.enums.ShipType.MINE;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.Technology;

public class DefenseBuilder extends com.thilian.se4x.robot.game.DefenseBuilder {

    public DefenseBuilder(Game game) {
        super(game);
    }

    @Override
    protected void buyHomeDefenseUnits(Fleet fleet) {
        if (fleet.getRemainigCP() > 25) {
            buyGravArmor(fleet);
            buyHeavyInfantry(fleet);
        }
        super.buyHomeDefenseUnits(fleet);
    }

    private void buyGravArmor(Fleet fleet) {
        if (fleet.getAp().getLevel(Technology.GROUND_COMBAT) == 3) {
            fleet.addGroup(new Group(GRAV_ARMOR, 2));
        }
    }

    private void buyHeavyInfantry(Fleet fleet) {
        if (fleet.getAp().getLevel(Technology.GROUND_COMBAT) >= 2) {
            int howManyHI = game.roller.roll();
            fleet.addGroup(new Group(HEAVY_INFANTRY, howManyHI));
        }
    }

    @Override
    public Fleet buildColonyDefense(AlienPlayer ap) {
        int defCP = ap.getEconomicSheet().getDefCP();
        int maxCP = game.roller.roll() + game.roller.roll();
        maxCP = maxCP < defCP ? maxCP : defCP;
        Fleet fleet = new Fleet(ap, FleetType.DEFENSE_FLEET, maxCP);
        addBasesOrMines(fleet);
        addGroundTroops(ap, fleet);
        return fleet;
    }

    private void addGroundTroops(AlienPlayer ap, Fleet fleet) {
        if(ap.getLevel(Technology.GROUND_COMBAT) > 1)
            addGroup(fleet, HEAVY_INFANTRY);
        else
            addGroup(fleet, INFANTRY);
    }

    private void addBasesOrMines(Fleet fleet) {
        if (game.roller.roll() < 6)
            addGroup(fleet, BASE, 1);
        else
            addGroup(fleet, MINE, 2);
    }
}
