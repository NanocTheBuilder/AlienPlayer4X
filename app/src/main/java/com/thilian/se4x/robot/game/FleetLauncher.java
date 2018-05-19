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

import static com.thilian.se4x.robot.game.enums.FleetBuildOption.HOME_DEFENSE;
import static com.thilian.se4x.robot.game.enums.FleetType.RAIDER_FLEET;
import static com.thilian.se4x.robot.game.enums.FleetType.REGULAR_FLEET;

import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

public class FleetLauncher {
    
    protected Game game;


    public FleetLauncher(Game game) {
        this.game = game;
    }

    public Fleet rollFleetLaunch(AlienPlayer ap, int turn) {
        AlienEconomicSheet economicSheet = ap.getEconomicSheet();
        int roll = getFleetLaunchRoll(ap);
        if (roll <= economicSheet.getFleetLaunch(turn)) {
            return launchFleet(ap, turn);
        }
        return null;
    }

    public Fleet launchFleet(AlienPlayer ap, int turn, FleetBuildOption... options) {
        AlienEconomicSheet economicSheet = ap.getEconomicSheet();
        int currentFleetCP = economicSheet.getFleetCP();
        if (currentFleetCP >= ShipType.SCOUT.getCost()) {
            Fleet fleet = new Fleet(ap, REGULAR_FLEET, currentFleetCP);
            if (shouldLaunchRaiderFleet(ap, options)) {
                fleet.setFleetType(RAIDER_FLEET);
                game.scenario.buildFleet(fleet);
            }
            int cpSpent = fleet.getFleetType().equals(RAIDER_FLEET) ? fleet.getBuildCost() : fleet.getFleetCP();
            economicSheet.spendFleetCP(cpSpent);
            return fleet;
        }
        return null;
    }

    private int getFleetLaunchRoll(AlienPlayer ap) {
        int currentFleetCP = ap.getEconomicSheet().getFleetCP();
        int roll = game.roller.roll();
        if ((currentFleetCP >= 27
                && ap.getLevel(Technology.FIGHTERS) > game.getSeenLevel(Technology.POINT_DEFENSE))
                || shouldLaunchRaiderFleet(ap))
            roll -= 2;
        return roll;
    }


    private boolean shouldLaunchRaiderFleet(AlienPlayer ap, FleetBuildOption... options) {
        if(FleetBuildOption.isOption(HOME_DEFENSE, options))
            return false;
        int currentFleetCP = ap.getEconomicSheet().getFleetCP();
        return currentFleetCP >= ShipType.RAIDER.getCost()
                && ap.getLevel(Technology.CLOAKING) > game.getSeenLevel(Technology.SCANNER);
    }

}
