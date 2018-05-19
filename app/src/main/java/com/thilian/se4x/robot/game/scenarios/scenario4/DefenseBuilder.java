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

package com.thilian.se4x.robot.game.scenarios.scenario4;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.Technology;

import static com.thilian.se4x.robot.game.enums.ShipType.BASE;
import static com.thilian.se4x.robot.game.enums.ShipType.GRAV_ARMOR;
import static com.thilian.se4x.robot.game.enums.ShipType.HEAVY_INFANTRY;
import static com.thilian.se4x.robot.game.enums.ShipType.INFANTRY;
import static com.thilian.se4x.robot.game.enums.ShipType.MINE;

public class DefenseBuilder extends com.thilian.se4x.robot.game.DefenseBuilder {

    public DefenseBuilder(Game game) {
        super(game);
    }

    @Override
    protected void buyHomeDefenseUnits(Fleet fleet) {
        if (fleet.getRemainingCP() > 25) {
            buyGravArmor(fleet);
            buyHeavyInfantry(fleet);
        }
        super.buyHomeDefenseUnits(fleet);
    }

    private void buyGravArmor(Fleet fleet) {
        if (fleet.getAp().getLevel(Technology.GROUND_COMBAT) == 3) {
            buildGroup(fleet, GRAV_ARMOR, 2);
        }
    }

    private void buyHeavyInfantry(Fleet fleet) {
        if (fleet.getAp().getLevel(Technology.GROUND_COMBAT) >= 2) {
            int howManyHI = game.roller.roll();
            buildGroup(fleet, HEAVY_INFANTRY, howManyHI);
        }
    }

    public Fleet buildColonyDefense(AlienPlayer ap) {
        int defCP = getDefCp(ap);
        if(defCP >= INFANTRY.getCost()) {
            int maxCP = game.roller.roll() + game.roller.roll();
            maxCP = maxCP < defCP ? maxCP : defCP;
            Fleet fleet = new Fleet(ap, FleetType.DEFENSE_FLEET, maxCP);
            addBasesOrMines(fleet);
            addGroundTroops(ap, fleet);
            return fleet;
        }
        else
            return null;
    }

    private void addGroundTroops(AlienPlayer ap, Fleet fleet) {
        if(ap.getLevel(Technology.GROUND_COMBAT) > 1 
                && fleet.getRemainingCP() >= HEAVY_INFANTRY.getCost()){
            buildGroup(fleet, HEAVY_INFANTRY);
        }
        else{
            buildGroup(fleet, INFANTRY);
        }
    }

    private void addBasesOrMines(Fleet fleet) {
        if (game.roller.roll() < 6 && fleet.getRemainingCP() >= BASE.getCost())
            buildGroup(fleet, BASE, 1);
        else
            buildGroup(fleet, MINE, 2);
    }
}
