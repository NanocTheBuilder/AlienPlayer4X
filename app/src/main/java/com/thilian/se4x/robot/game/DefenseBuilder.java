package com.thilian.se4x.robot.game;

import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.ShipType;

public class DefenseBuilder extends GroupBuilder{

    protected Game game;

    public DefenseBuilder(Game game) {
        this.game = game;
    }

    public Fleet buildHomeDefense(AlienPlayer ap) {
        Fleet fleet = new Fleet(ap, FleetType.DEFENSE_FLEET, ap.getEconomicSheet().getDefCP());
        buyHomeDefenseUnits(fleet);
        return fleet;
    }

    protected void buyHomeDefenseUnits(Fleet fleet) {
        int roll = game.roller.roll();
        if (roll < 4) {
            addGroup(fleet, ShipType.MINE);
        } else if (roll < 8) {
            while (canBuyMine(fleet)) {
                if (canBuyBase(fleet))
                    fleet.addGroup(new Group(ShipType.BASE, 1));
                if (canBuyMine(fleet))
                    fleet.addGroup(new Group(ShipType.MINE, 1));
            }
        } else {
            addGroup(fleet, ShipType.BASE);
            addGroup(fleet, ShipType.MINE);
        }
    }

    private boolean canBuyBase(Fleet fleet) {
        return fleet.getRemainigCP() >= ShipType.BASE.getCost();
    }

    private boolean canBuyMine(Fleet fleet) {
        return fleet.getRemainigCP() >= ShipType.MINE.getCost();
    }

    public Fleet buildColonyDefense(AlienPlayer ap) {
        throw new UnsupportedOperationException();
    }
}
