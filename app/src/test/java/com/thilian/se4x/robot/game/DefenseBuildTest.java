package com.thilian.se4x.robot.game;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.ShipType;

public class DefenseBuildTest extends Fixture{
    @Test
    public void spendNoDefenseCPBuildNothings() {
        assertBuiltGroups(0, 1);
    }

    @Test
    public void spendAllOnMines() {
        assertBuiltGroups(5, 1, new Group(ShipType.MINE, 1));
        assertBuiltGroups(10, 1, new Group(ShipType.MINE, 2));
    }

    @Test
    public void spendAllOnBases() {
        assertBuiltGroups(12, 8, new Group(ShipType.BASE, 1));
        assertBuiltGroups(24, 8, new Group(ShipType.BASE, 2));
        assertBuiltGroups(36, 8, new Group(ShipType.BASE, 3));
        assertBuiltGroups(51, 8, new Group(ShipType.BASE, 4));

        assertBuiltGroups(17, 8, new Group(ShipType.BASE, 1), new Group(ShipType.MINE, 1));
        assertBuiltGroups(29, 8, new Group(ShipType.BASE, 2), new Group(ShipType.MINE, 1));
        assertBuiltGroups(34, 8, new Group(ShipType.BASE, 2), new Group(ShipType.MINE, 2));

        assertBuiltGroups(5, 1, new Group(ShipType.MINE, 1));
    }

    @Test
    public void spendBalanced() {
        assertBuiltGroups(12, 5, new Group(ShipType.BASE, 1));
        assertBuiltGroups(17, 5, new Group(ShipType.BASE, 1), new Group(ShipType.MINE, 1));
        assertBuiltGroups(24, 5, new Group(ShipType.BASE, 1), new Group(ShipType.MINE, 2));

        assertBuiltGroups(5, 1, new Group(ShipType.MINE, 1));
    }

    private void assertBuiltGroups(int defCP, int roll, Group... expectedGroups) {
        sheet.setDefCP(defCP);
        roller.mockRoll(roll);
        Fleet fleet = defBuilder.buildHomeDefense(ap);
        int expectedCost = 0;
        for (Group g : expectedGroups) {
            expectedCost += g.getShipType().getCost() * g.getSize();
        }
        assertEquals(FleetType.DEFENSE_FLEET, fleet.getFleetType());
        assertEquals(Arrays.asList(expectedGroups), fleet.getGroups());
        assertEquals(expectedCost, fleet.getBuildCost());
    }
}
