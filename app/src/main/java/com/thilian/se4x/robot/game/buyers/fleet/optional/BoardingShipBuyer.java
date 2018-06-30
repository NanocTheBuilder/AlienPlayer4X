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

import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.buyers.OptionalGroupBuyer;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

public class BoardingShipBuyer extends OptionalGroupBuyer {
    public BoardingShipBuyer(Game game) {
        super(game);
    }

    @Override
    protected boolean shouldBuyGroups(Fleet fleet, FleetBuildOption... options) {
        return fleet.getAp().getLevel(Technology.BOARDING) != 0;
    }

    @Override
    protected void doBuyGroups(Fleet fleet) {
        buyGroup(fleet, ShipType.BOARDING_SHIP, 2);
    }
}
