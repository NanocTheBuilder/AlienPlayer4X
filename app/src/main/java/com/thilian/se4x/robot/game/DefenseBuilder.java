/*
 * Copyright (C) 2018 Balázs Péter
 *
 * This file is part of Alien Player 4X.
 *
 * Alien Player 4X is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alien Player 4X is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Alien Player 4X.  If not, see <http://www.gnu.org/licenses/>.
 */

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
