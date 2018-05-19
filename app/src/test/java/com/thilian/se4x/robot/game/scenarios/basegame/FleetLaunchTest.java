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

package com.thilian.se4x.robot.game.scenarios.basegame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.Technology;

public class FleetLaunchTest extends BasegameFixture {
    private int roll;
    private int turn;

    @Before
    public void setUp() {
        sheet.setFleetCP(500);
        roll = 7;
        turn = 4;
    }

    @Test
    public void noLaunchInTurn1() {
        turn = 1;
        roll = 1;
        assertFleetDoesNotLaunch();
    }

    @Test
    public void noLaunchUnder6() {
        sheet.setFleetCP(5);
        turn = 2;
        roll = 1;
        assertFleetDoesNotLaunch();
    }

    @Test
    public void allwaysLaunchInTurn2() {
        turn = 2;
        roll = 10;
        assertFleetLaunches();
    }

    @Test
    public void dontLaunchIfRollFails() {
        assertFleetDoesNotLaunch();
    }

    @Test
    public void subtract2ForFighters() {
        ap.setLevel(Technology.FIGHTERS, 1);
        assertFleetLaunches();
    }

    @Test
    public void onlySubtract2ForFightersIfPDNotSeen() {
        ap.setLevel(Technology.FIGHTERS, 1);
        game.setSeenLevel(Technology.POINT_DEFENSE, 1);
        assertFleetDoesNotLaunch();
    }

    @Test
    public void onlySubtract2ForFightersIfHasEnoughCP() {
        sheet.setFleetCP(26);
        ap.setLevel(Technology.FIGHTERS, 1);
        assertFleetDoesNotLaunch();
    }
    
    @Test
    public void testRaiderFleetLaunch() {
        ap.setLevel(Technology.CLOAKING, 1);
        sheet.setFleetCP(13);
        roller.mockRoll(roll);
        fleetLauncher.rollFleetLaunch(ap, turn);
        Fleet fleet = ap.getFleets().get(0);
        assertEquals(1, sheet.getFleetCP());
        assertEquals(FleetType.RAIDER_FLEET, fleet.getFleetType());
        assertEquals(13, fleet.getFleetCP());
        assertEquals(12, fleet.getBuildCost());
    }

    @Test
    public void onlySubtract2ForCloakingIfScannerNotSeen() {
        ap.setLevel(Technology.FIGHTERS, 1);
        game.setSeenLevel(Technology.POINT_DEFENSE, 1);
        assertFleetDoesNotLaunch();
    }

    @Test
    public void testNoRaiderUnder12() {
        ap.setLevel(Technology.CLOAKING, 1);
        sheet.setFleetCP(11);
        roller.mockRoll(3);
        fleetLauncher.rollFleetLaunch(ap, turn);
        Fleet fleet = ap.getFleets().get(0);
        assertEquals(0, sheet.getFleetCP());
        assertEquals(FleetType.REGULAR_FLEET, fleet.getFleetType());
        assertEquals(11, fleet.getFleetCP());
        assertEquals(0, fleet.getBuildCost());
    }
    
    @Test
    public void testNoRaiderFleetForHomeDefense() {
        ap.setLevel(Technology.CLOAKING, 1);
        sheet.setFleetCP(12);
        fleetLauncher.launchFleet(ap, turn, FleetBuildOption.HOME_DEFENSE);
        Fleet fleet = ap.getFleets().get(0);
        assertEquals(0, sheet.getFleetCP());
        assertEquals(FleetType.REGULAR_FLEET, fleet.getFleetType());
        assertEquals(12, fleet.getFleetCP());
        assertEquals(0, fleet.getBuildCost());
    }
    

    private void assertFleetLaunches() {
        int fleetCP = sheet.getFleetCP();
        roller.mockRoll(roll);
        fleetLauncher.rollFleetLaunch(ap, turn);
        assertEquals(0, sheet.getFleetCP());
        assertEquals(fleetCP, ap.getFleets().get(0).getFleetCP());
    }

    private void assertFleetDoesNotLaunch() {
        int fleetCP = sheet.getFleetCP();
        roller.mockRoll(roll);
        fleetLauncher.rollFleetLaunch(ap, turn);
        assertEquals(fleetCP, sheet.getFleetCP());
        assertTrue(ap.getFleets().isEmpty());
    }

}
