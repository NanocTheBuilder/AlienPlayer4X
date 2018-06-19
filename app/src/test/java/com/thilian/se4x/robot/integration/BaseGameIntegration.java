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

package com.thilian.se4x.robot.integration;

import com.thilian.se4x.robot.game.AlienEconomicSheet;
import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.EconPhaseResult;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.MockRoller;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.PlayerColor;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameDifficulty;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameScenario;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class BaseGameIntegration {

    private AlienPlayer ap;
    private AlienEconomicSheet sheet;
    private Fleet fleet;

    @Test
    public void economyRollStartsNewFleet() {
        MockRoller roller = new MockRoller();
        Game game = Game.newGame(new BaseGameScenario(), BaseGameDifficulty.NORMAL, PlayerColor.GREEN, PlayerColor.YELLOW, PlayerColor.RED);
        game.roller = roller;
        ap = game.aliens.get(0);
        sheet = ap.getEconomicSheet();

        roller.mockRoll(1); //extra econ
        roller.mockRoll(1); //launch
        assertNoFleetLaunch(ap.makeEconRoll(1));
        assertEquals(1, sheet.getExtraEcon(4));
        assertCPs(0, 0, 0);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(5);
        roller.mockRoll(1); //launch
        assertNoFleetLaunch(ap.makeEconRoll(2));
        assertCPs(0, 5, 0);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(4);
        roller.mockRoll(3);
        roller.mockRoll(2);
        roller.mockRoll(7);
        EconPhaseResult result = ap.makeEconRoll(3);
        assertRegularFleetLaunch(result, 10);
        assertEquals(1, ap.getLevel(Technology.MOVE));
        assertCPs(0, 5, 0);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(4);
        roller.mockRoll(3);
        roller.mockRoll(3);
        roller.mockRoll(6);
        result = ap.makeEconRoll(4);
        assertNoFleetLaunch(result);
        assertCPs(15, 5, 0);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(7);
        roller.mockRoll(10);
        roller.mockRoll(10);
        roller.mockRoll(10);
        result = ap.makeEconRoll(5);
        assertNoFleetLaunch(result);
        assertCPs(15, 10, 20);
        assertEquals(0, roller.rolls.size());

        fleet = ap.getFleets().get(0);
        roller.mockRoll(9); // ShipSize
        ap.firstCombat(fleet);
        assertEquals(2, ap.getLevel(Technology.SHIP_SIZE));
        assertGroups(fleet, new Group(ShipType.DESTROYER, 1));
        assertCPs(16, 0, 20);
        assertEquals(0, roller.rolls.size());

        ap.removeFleet(fleet);
        assertEquals(0, ap.getFleets().size());

        roller.mockRoll(6);
        roller.mockRoll(6);
        roller.mockRoll(9);
        roller.mockRoll(7);
        roller.mockRoll(5);
        result = ap.makeEconRoll(6);
        assertNoFleetLaunch(result);
        assertCPs(26, 10, 20);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(6);
        roller.mockRoll(9);
        roller.mockRoll(8);
        roller.mockRoll(3);
        roller.mockRoll(4);
        roller.mockRoll(8);
        result = ap.makeEconRoll(7);
        assertRegularFleetLaunch(result, 31);
        assertEquals(1, ap.getLevel(Technology.MOVE));
        assertCPs(0, 25, 20);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(1);
        roller.mockRoll(4);
        roller.mockRoll(7);
        roller.mockRoll(9);
        roller.mockRoll(7);
        result = ap.makeEconRoll(8);
        assertNoFleetLaunch(result);
        assertCPs(10, 35, 20);
        assertEquals(0, roller.rolls.size());

        fleet = ap.getFleets().get(0);
        roller.mockRoll(9); // ShipSize
        roller.mockRoll(6); // Cloaking
        ap.firstCombat(fleet);
        assertEquals(2, ap.getLevel(Technology.SHIP_SIZE));
        assertEquals(1, ap.getLevel(Technology.CLOAKING));
        assertGroups(fleet, new Group(ShipType.RAIDER, 2));
        assertEquals(FleetType.RAIDER_FLEET, fleet.getFleetType());
        assertCPs(17, 5, 20);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(2);
        roller.mockRoll(9);
        roller.mockRoll(7);
        roller.mockRoll(7);
        roller.mockRoll(8);

        result = ap.makeEconRoll(9);
        assertNoFleetLaunch(result);
        assertCPs(22, 20, 20);
        assertEquals(2, ap.getLevel(Technology.SHIP_SIZE));
        assertEquals(0, roller.rolls.size());

        game.setSeenLevel(Technology.CLOAKING, 1);
        roller.mockRoll(1); // Scanners
        roller.mockRoll(9); // ShipSize (Ignored)
        roller.mockRoll(1); // Max number of ships
        roller.mockRoll(10); // Max bases
        ap.buildHomeDefense();
        assertEquals(2, ap.getLevel(Technology.SHIP_SIZE));
        assertEquals(1, ap.getLevel(Technology.SCANNER));
        assertGroups(ap.getFleets().get(1), new Group(ShipType.DESTROYER, 1), new Group(ShipType.SCOUT, 2));
        assertEquals(FleetType.REGULAR_FLEET, ap.getFleets().get(1).getFleetType());
        assertGroups(ap.getFleets().get(2), new Group(ShipType.BASE, 1), new Group(ShipType.MINE, 1));
        assertEquals(FleetType.DEFENSE_FLEET, ap.getFleets().get(2).getFleetType());
        assertCPs(1, 0, 3);
        assertEquals(0, roller.rolls.size());

        // TODO build raider fleet (isJustPurchasedCloaking)
    }

    private void assertRegularFleetLaunch(EconPhaseResult result, int fleetCP) {
        assertEquals(fleetCP, result.getFleet().getFleetCP());
        assertEquals(FleetType.REGULAR_FLEET, result.getFleet().getFleetType());
        assertEquals(result.getFleet(), ap.getFleets().get(0));
    }

    private void assertNoFleetLaunch(EconPhaseResult result) {
        assertEquals(null, result.getFleet());
    }

    private void assertCPs(int fleetCP, int techCP, int defCP) {
        assertEquals(fleetCP, sheet.getFleetCP());
        assertEquals(techCP, sheet.getTechCP());
        assertEquals(defCP, sheet.getDefCP());
    }

    private void assertGroups(Fleet fleet, Group... expectedGroups) {
        assertEquals(Arrays.asList(expectedGroups), fleet.getGroups());
    }
}
