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

package com.thilian.se4x.robot.game.buyers.fleet.optional;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.buyers.OptionalGroupBuyer;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;

import static com.thilian.se4x.robot.game.enums.ShipType.DESTROYER;
import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static com.thilian.se4x.robot.game.enums.Technology.SCANNER;
import static com.thilian.se4x.robot.game.enums.Technology.SHIP_SIZE;

public class DestroyerBuyer extends OptionalGroupBuyer {
    public DestroyerBuyer(Game game) {
        super(game);
    }

    @Override
    protected boolean shouldBuyGroups(Fleet fleet, FleetBuildOption... options) {
        AlienPlayer ap = fleet.getAp();
        return (DESTROYER.canBeBuilt(fleet.getRemainingCP(), ap.getLevel(SHIP_SIZE))
                && game.getSeenLevel(CLOAKING) <= ap.getLevel(SCANNER) && fleet.findGroup(DESTROYER) == null);
    }

    @Override
    protected void doBuyGroups(Fleet fleet) {
        fleet.addGroup(new Group(DESTROYER, 1));
    }
}
