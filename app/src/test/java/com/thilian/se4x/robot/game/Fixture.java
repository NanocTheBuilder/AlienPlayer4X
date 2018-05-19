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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;

import com.thilian.se4x.robot.game.enums.Technology;

public abstract class Fixture {
    protected Game game;
    protected AlienPlayer ap;
    protected DefenseBuilder defBuilder;
    protected MockRoller roller;
    protected AlienEconomicSheet sheet;
    protected FleetBuilder fleetBuilder;
    protected TechnologyBuyer techBuyer;
    protected FleetLauncher fleetLauncher;

    @Before
    public void setUpFixture() {
        game = createGame();
        setupRoller(game);
        defBuilder = game.scenario.defenseBuilder;
        fleetBuilder = game.scenario.fleetBuilder;
        fleetLauncher = game.scenario.fleetLauncher;
        techBuyer = game.scenario.techBuyer;
        ap = game.aliens.get(0);
        sheet = ap.getEconomicSheet();
    }

    protected abstract Game createGame();

    @After
    public void assertAllRollsUsed() {
        assertRoller();
    }
    
    private void setupRoller(Game game) {
        roller = new MockRoller();
        game.roller = roller;
    }
    
    protected void setLevel(Technology technology, int level){
        ap.setLevel(technology, level);
    }

    protected void assertLevel(Technology technology, int expected) {
        assertEquals(expected, ap.getLevel(technology));
    }

    protected void assertGroups(Fleet fleet, Group... expectedGroups) {
        assertEquals(Arrays.asList(expectedGroups), fleet.getGroups());
    }

    protected void assertRoller(){
        assertEquals(0, roller.rolls.size());
    }

    protected void setCPs(int fleetCP, int techCP, int defCP) {
       sheet.setFleetCP(fleetCP);
       sheet.setTechCP(techCP);
       sheet.setDefCP(defCP);
   }

    protected void assertCPs(int fleetCP, int techCP, int defCP) {
       assertEquals(fleetCP, sheet.getFleetCP());
       assertEquals(techCP, sheet.getTechCP());
       assertEquals(defCP, sheet.getDefCP());
   }

}
