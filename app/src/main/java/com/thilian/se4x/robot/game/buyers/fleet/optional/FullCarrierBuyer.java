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
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.buyers.OptionalGroupBuyer;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.Seeable;

import static com.thilian.se4x.robot.game.enums.ShipType.CARRIER;
import static com.thilian.se4x.robot.game.enums.ShipType.FIGHTER;
import static com.thilian.se4x.robot.game.enums.Technology.FIGHTERS;
import static com.thilian.se4x.robot.game.enums.Technology.POINT_DEFENSE;

public class FullCarrierBuyer extends OptionalGroupBuyer {
    private static int FULL_CV_COST = CARRIER.getCost() + FIGHTER.getCost() * 3;

    public FullCarrierBuyer(Game game) {
        super(game);
    }

    @Override
    protected boolean shouldBuyGroups(Fleet fleet, FleetBuildOption... options) {
        if(fleet.getFleetCP() < FULL_CV_COST
                || fleet.getAp().getLevel(FIGHTERS) == 0
                || FleetBuildOption.isOption(FleetBuildOption.COMBAT_WITH_NPAS, options)
                )
            return false;
        return game.getSeenLevel(POINT_DEFENSE) == 0 && !game.isSeenThing(Seeable.MINES)
                || game.roller.roll() < 5;
    }

    @Override
    protected void doBuyGroups(Fleet fleet) {
        int fleetCP = fleet.getFleetCP();
        int shipsToBuild = fleetCP / FULL_CV_COST;
        fleet.addGroup(new Group(CARRIER, shipsToBuild));
        fleet.addGroup(new Group(FIGHTER, shipsToBuild * 3));
    }
}
