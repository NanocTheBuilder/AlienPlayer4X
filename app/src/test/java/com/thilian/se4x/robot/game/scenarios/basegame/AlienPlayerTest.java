package com.thilian.se4x.robot.game.scenarios.basegame;

import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

import org.junit.Test;

import java.util.Arrays;

import static com.thilian.se4x.robot.game.enums.ShipType.CRUISER;
import static com.thilian.se4x.robot.game.enums.ShipType.DESTROYER;
import static com.thilian.se4x.robot.game.enums.ShipType.SCOUT;
import static com.thilian.se4x.robot.game.enums.Technology.ATTACK;
import static com.thilian.se4x.robot.game.enums.Technology.MINE_SWEEPER;
import static com.thilian.se4x.robot.game.enums.Technology.SHIP_SIZE;
import static org.junit.Assert.assertEquals;

public class AlienPlayerTest extends BasegameFixture {

    @Test
    public void launchRegularFleetThenBuildLargest(){
        sheet.setFleetCP(50);
        sheet.setTechCP(30);
        ap.setLevel(SHIP_SIZE, 2);
        roller.mockRoll(3);
        roller.mockRoll(6);
        roller.mockRoll(8);
        roller.mockRoll(10);
        roller.mockRoll(3); //fleet launch
        roller.mockRoll(7); //move tech
        Fleet fleet = ap.makeEconRoll(10);
        assertCPs(0, 35, 10);
        assertEquals(60, ap.getFleets().get(0).getFleetCP());
        assertRoller();
        roller.mockRoll(6); //Ship size
        roller.mockRoll(8,1); //Attack
        roller.mockRoll(7); //fleet composition
        ap.firstCombat(fleet);
        assertEquals(3, ap.getLevel(SHIP_SIZE));
        assertEquals(1, ap.getLevel(ATTACK));
        assertGroups(fleet, new Group(CRUISER, 4), new Group(DESTROYER, 1));
        assertCPs(3, 0, 10);
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
}
