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

import com.thilian.se4x.robot.game.AlienEconomicSheet;
import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.FleetBuildResult;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.PlayerColor;

public class Scenario4Player extends AlienPlayer {
    public Scenario4Player(AlienEconomicSheet sheet, Game game, PlayerColor color) {
        super(sheet, game, color);
    }

    public FleetBuildResult buildColonyDefense() {
        FleetBuildResult result = new FleetBuildResult(this);
        Fleet fleet = ((Scenario4) game.scenario).buildColonyDefense(this);
        if(fleet != null){
            economicSheet.spendDefCP(fleet.getBuildCost());
            fleet.setFirstCombat(true);
            result.addNewFleet(fleet);
        }
        return result;
    }

}
