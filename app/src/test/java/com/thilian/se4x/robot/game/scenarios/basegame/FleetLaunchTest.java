package com.thilian.se4x.robot.game.scenarios.basegame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.thilian.se4x.robot.game.Fleet;
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
        int fleetCP = sheet.getFleetCP();
        fleetLauncher.rollFleetLaunch(ap, turn);
        assertEquals(fleetCP, sheet.getFleetCP());
        assertTrue(ap.getFleets().isEmpty());
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
        assertEquals(true, fleet.isBuilt());
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
        assertEquals(false, fleet.isBuilt());
    }

    private void assertFleetLaunches() {
        int fleetCP = sheet.getFleetCP();
        roller.mockRoll(roll);
        fleetLauncher.rollFleetLaunch(ap, turn);
        assertEquals(0, sheet.getFleetCP());
        assertEquals(fleetCP, ap.getFleets().get(0).getFleetCP());
        assertEquals(false, ap.getFleets().get(0).isBuilt());
    }

    private void assertFleetDoesNotLaunch() {
        int fleetCP = sheet.getFleetCP();
        roller.mockRoll(roll);
        fleetLauncher.rollFleetLaunch(ap, turn);
        assertEquals(fleetCP, sheet.getFleetCP());
        assertTrue(ap.getFleets().isEmpty());
    }

}
