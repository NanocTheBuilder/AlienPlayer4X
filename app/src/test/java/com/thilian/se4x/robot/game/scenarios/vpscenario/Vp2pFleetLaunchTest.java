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

import com.thilian.se4x.robot.game.Fixture;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.PlayerColor;

import org.junit.Test;

import static com.thilian.se4x.robot.game.enums.FleetType.EXTERMINATION_FLEET_GALACTIC_CAPITAL;
import static com.thilian.se4x.robot.game.enums.FleetType.EXTERMINATION_FLEET_HOME_WORLD;
import static org.junit.Assert.assertEquals;

public class Vp2pFleetLaunchTest extends Fixture {
    @Override
    protected Game createGame() {
        return Game.newGame(new Vp2pScenario(), VpDifficulties.Vp2pDifficulty.NORMAL, PlayerColor.GREEN, PlayerColor.YELLOW);
    }


    @Test
    public void launchExterminationFleet(){
        VpEconomicSheet vpSheet = (VpEconomicSheet)sheet;

        vpSheet.setFleetCP(10);
        roller.mockRoll(1); //launch
        roller.mockRoll(8);
        assertFleetLaunched(7, EXTERMINATION_FLEET_GALACTIC_CAPITAL, "1", 10);

        vpSheet.setFleetCP(10);
        roller.mockRoll(1); //launch
        roller.mockRoll(6);
        assertFleetLaunched(8, EXTERMINATION_FLEET_HOME_WORLD, "2", 10);

        vpSheet.setFleetCP(10);
        roller.mockRoll(1); //launch
        roller.mockRoll(6);
        assertFleetLaunched(9, EXTERMINATION_FLEET_GALACTIC_CAPITAL, "3", 10);


        vpSheet.setFleetCP(10);
        roller.mockRoll(1); //launch
        roller.mockRoll(6);
        assertFleetLaunched(10, EXTERMINATION_FLEET_HOME_WORLD, "4", 10);

        vpSheet.setFleetCP(10);
        roller.mockRoll(1); //launch
        roller.mockRoll(4);
        assertFleetLaunched(11, EXTERMINATION_FLEET_GALACTIC_CAPITAL, "5", 10);
    }

    private void assertFleetLaunched(int turn, FleetType fleetType, String name, int fleetCP) {
        Fleet fleet = fleetLauncher.rollFleetLaunch(ap, turn);
        assertEquals(fleetType, fleet.getFleetType());
        assertEquals(name, fleet.getName());
        assertEquals(fleetCP, fleet.getFleetCP());
    }
}
