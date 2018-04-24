package com.thilian.se4x.robot.game;

import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.ShipType;

import static com.thilian.se4x.robot.game.enums.ShipType.BASE;
import static com.thilian.se4x.robot.game.enums.ShipType.MINE;

public class DefenseBuilder extends GroupBuilder{

    protected Game game;

    public DefenseBuilder(Game game) {
        this.game = game;
    }

    protected int getDefCp(AlienPlayer ap){
        return ap.getEconomicSheet().getDefCP();
    }

    public Fleet buildHomeDefense(AlienPlayer ap) {
        if(getDefCp(ap) >= ShipType.MINE.getCost()) {
            Fleet fleet = new Fleet(ap, FleetType.DEFENSE_FLEET, getDefCp(ap));
            buyHomeDefenseUnits(fleet);
            return fleet;
        }
        else
            return null;
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
        return fleet.getRemainingCP() >= MINE.getCost();
    }
}
