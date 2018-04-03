package com.thilian.se4x.robot.game;

import static com.thilian.se4x.robot.game.enums.ShipType.BASE;
import static com.thilian.se4x.robot.game.enums.ShipType.MINE;

import com.thilian.se4x.robot.game.enums.FleetType;

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
            buildGroup(fleet, MINE);
        } else if (roll < 8) {
            while (canBuyMine(fleet)) {
                buildGroup(fleet, BASE, 1);
                buildGroup(fleet, MINE, 1);
            }
        } else {
            buildGroup(fleet, BASE);
            buildGroup(fleet, MINE);
        }
    }

    private boolean canBuyMine(Fleet fleet) {
        return fleet.getRemainigCP() >= MINE.getCost();
    }
}
