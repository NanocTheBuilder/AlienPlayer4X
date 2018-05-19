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

import static com.thilian.se4x.robot.game.enums.FleetType.RAIDER_FLEET;

import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

import java.util.ArrayList;
import java.util.List;

public class FleetBuilder extends com.thilian.se4x.robot.game.FleetBuilder {

    public FleetBuilder(Game game) {
        super(game);
    };

    public void buildFleet(Fleet fleet, FleetBuildOption... options) {
        if (fleet.getFleetType().equals(RAIDER_FLEET) || shouldBuildRaiderFleet(fleet, options)) {
            buildRaiderFleet(fleet);
        } else {
            buildOneFullyLoadedTransport(fleet, options);
            buyBoardingShips(fleet);
            buyScoutsIfSeenMines(fleet);
            buyFullCarriers(fleet, options);
            buildFlagship(fleet);
            buildPossibleDD(fleet);
            buildRemainderFleet(fleet);
        }
    }

    private void buyBoardingShips(Fleet fleet) {
        if (fleet.getAp().getLevel(Technology.BOARDING) != 0) {
            buildGroup(fleet, ShipType.BOARDING_SHIP, 2);
        }
    }

    private void buyScoutsIfSeenMines(Fleet fleet) {
        if (game.isSeenThing(Seeable.MINES)) {
            buildGroup(fleet, ShipType.SCOUT, 2);
        }
    }

    protected void buildOneFullyLoadedTransport(Fleet fleet, FleetBuildOption... options) {
        fleet.addFreeGroup(new Group(ShipType.TRANSPORT, 1));
        for(Group group : buildGroundUnits(fleet)){
            fleet.addFreeGroup(group);
        }
    }

    protected List<Group> buildGroundUnits(Fleet fleet){
        List<Group> groups = new ArrayList<>();
        switch (fleet.getAp().getLevel(Technology.GROUND_COMBAT)) {
            default:
                groups.add(new Group(ShipType.INFANTRY, 6));
                break;
            case 2:
                groups.add(new Group(ShipType.MARINE, 5));
                groups.add(new Group(ShipType.HEAVY_INFANTRY, 1));
                break;
            case 3:
                groups.add(new Group(ShipType.MARINE, 4));
                groups.add(new Group(ShipType.HEAVY_INFANTRY, 1));
                groups.add(new Group(ShipType.GRAV_ARMOR, 1));
                break;
        }
        return groups;
    }

}
