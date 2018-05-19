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

package com.thilian.se4x.robot.game.scenarios.vpscenario;

import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.scenarios.scenario4.FleetBuilder;

import static com.thilian.se4x.robot.game.enums.FleetBuildOption.COMBAT_IS_ABOVE_PLANET;
import static com.thilian.se4x.robot.game.enums.FleetType.EXPANSION_FLEET;

public class VpFleetBuilder extends FleetBuilder {
    public VpFleetBuilder(Game game) {
        super(game);
    }

    @Override
    protected void buildOneFullyLoadedTransport(Fleet fleet, FleetBuildOption... options) {
        if(EXPANSION_FLEET.equals(fleet.getFleetType())){
            super.buildOneFullyLoadedTransport(fleet);
        }
        else if(fleet.getRemainingCP() >= 40){
            int roll = game.roller.roll();
            if(FleetBuildOption.isOption(COMBAT_IS_ABOVE_PLANET, options))
                roll -=2;
            if(roll <= 5){
                fleet.addGroup(new Group(ShipType.TRANSPORT, 1));
                for (Group group : buildGroundUnits(fleet)) {
                    fleet.addGroup(group);
                }
            }
        }
    }
}
