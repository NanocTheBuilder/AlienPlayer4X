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

package com.thilian.se4x.robot.game.buyers.technology;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.buyers.TechBuyer;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.Technology;

import static com.thilian.se4x.robot.game.enums.Technology.FIGHTERS;
import static com.thilian.se4x.robot.game.enums.Technology.POINT_DEFENSE;

public class FightersBuyer extends TechBuyer {

    public FightersBuyer(Game game) {
        super(game, Technology.FIGHTERS);
    }

    @Override
    protected boolean isNeeded(Fleet fleet, FleetBuildOption... options) {
        AlienPlayer ap = fleet.getAp();
        return game.getSeenLevel(POINT_DEFENSE) == 0
                && ap.getLevel(FIGHTERS) != 0
                && game.roller.roll() <= 6;
    }

}
