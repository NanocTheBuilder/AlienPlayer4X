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

import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static com.thilian.se4x.robot.game.enums.Technology.SCANNER;

public class ScannerBuyer extends TechBuyer {
    public ScannerBuyer(Game game){
        super(game, Technology.SCANNER);
    }

    @Override
    public void buyTechIfNeeded(Fleet fleet, FleetBuildOption... options) {
        AlienPlayer ap = fleet.getAp();
        if (game.getSeenLevel(CLOAKING) > ap.getLevel(SCANNER)) {
            if (game.roller.roll() <= 4) {
                int levelsNeeded = game.getSeenLevel(CLOAKING) - ap.getLevel(SCANNER);
                for (int i = 0; i < levelsNeeded; i++)
                    buyNextLevel(ap);
            }
        }
    }
}
