package com.thilian.se4x.robot.game.scenarios.basegame;

import static com.thilian.se4x.robot.game.enums.ShipType.*;
import static com.thilian.se4x.robot.game.enums.Technology.*;
import static com.thilian.se4x.robot.game.enums.Seeable.*;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;

import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.Technology;

public class AlienPlayerTest extends BasegameFixture {

    @Test
    public void launchRegularFleetThenBuildLargestFleet(){
        sheet.setFleetCP(60);
        sheet.setTechCP(45);
        setLevel(SHIP_SIZE, 4);
        setLevel(ATTACK, 1);
        mock2Fleet1Tech1DefRoll();
        roller.mockRoll(3); //fleet launch
        roller.mockRoll(7); //move tech
        Fleet fleet = ap.makeEconRoll(10);
        assertCPs(0, 50, 10);
        assertEquals(70, ap.getFleets().get(0).getFleetCP());
        assertRoller();
        roller.mockRoll(5); //Ship size
        roller.mockRoll(10,1); //Attack
        roller.mockRoll(3); //fleet composition
        ap.firstCombat(fleet);
        assertLevel(SHIP_SIZE, 5);
        assertLevel(ATTACK, 2);
        assertGroups(fleet, new Group(BATTLESHIP, 1), new Group(DESTROYER, 2), new Group(SCOUT, 5));
        assertCPs(2, 0, 10);
        assertRoller();
    }

    @Test
    public void launchRegularFleetThenBuildBalancedWith2SC(){
        sheet.setFleetCP(60);
        sheet.setTechCP(45);
        setLevel(SHIP_SIZE, 4);
        setLevel(ATTACK, 1);
        setLevel(POINT_DEFENSE, 1);
        game.addSeenThing(Seeable.FIGHTERS);
        mock2Fleet1Tech1DefRoll();
        roller.mockRoll(3); //fleet launch
        roller.mockRoll(7); //move tech
        Fleet fleet = ap.makeEconRoll(10);
        assertCPs(0, 50, 10);
        assertEquals(70, ap.getFleets().get(0).getFleetCP());
        assertRoller();
        roller.mockRoll(5); //Ship size
        roller.mockRoll(10,1); //Attack
        roller.mockRoll(8); //fleet composition
        ap.firstCombat(fleet);
        assertLevel(SHIP_SIZE, 5);
        assertLevel(ATTACK, 2);
        assertGroups(fleet, new Group(BATTLESHIP, 1), new Group(DESTROYER, 1), new Group(SCOUT, 2), new Group(BATTLECRUISER, 1), new Group(CRUISER, 1));
        assertCPs(2, 0, 10);
        assertRoller();
    }

    @Test
    public void launchRegularFleetThenBuildLargestShips(){
        sheet.setFleetCP(60);
        sheet.setTechCP(45);
        setLevel(SHIP_SIZE, 4);
        setLevel(ATTACK, 1);
        mock2Fleet1Tech1DefRoll();
        roller.mockRoll(3); //fleet launch
        roller.mockRoll(7); //move tech
        Fleet fleet = ap.makeEconRoll(10);
        assertCPs(0, 50, 10);
        assertEquals(70, ap.getFleets().get(0).getFleetCP());
        assertRoller();
        roller.mockRoll(5); //Ship size
        roller.mockRoll(10,1); //Attack
        roller.mockRoll(8); //fleet composition
        ap.firstCombat(fleet);
        assertLevel(SHIP_SIZE, 5);
        assertLevel(ATTACK, 2);
        assertGroups(fleet, new Group(BATTLESHIP, 3), new Group(DESTROYER, 1));
        assertCPs(1, 0, 10);
        assertRoller();
    }

    public void assertRoller() {
        assertEquals(0, roller.rolls.size());
    }

    public void assertCPs(int fleetCP, int techCP, int defCP) {
        assertEquals(fleetCP, sheet.getFleetCP());
        assertEquals(techCP, sheet.getTechCP());
        assertEquals(defCP, sheet.getDefCP());
    }

    public void assertGroups(Fleet fleet, Group... expectedGroups) {
        assertEquals(Arrays.asList(expectedGroups), fleet.getGroups());
    }


    private void assertLevel(Technology technology, int expected) {
        assertEquals(expected, ap.getLevel(technology));
    }

    private void setLevel(Technology technology, int level) {
        ap.setLevel(technology, level);
    }

    private void mock2Fleet1Tech1DefRoll() {
        roller.mockRoll(3);
        roller.mockRoll(6);
        roller.mockRoll(8);
        roller.mockRoll(10);
    }
}
