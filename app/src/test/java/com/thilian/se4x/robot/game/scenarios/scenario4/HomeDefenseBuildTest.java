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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

public class HomeDefenseBuildTest extends Scenario4Fixture{

    @Test
    public void ifNoGroundCombatTechSpendBalanced() {
        assertBuiltGroups(12, 5, new Group(ShipType.BASE, 1));
        assertBuiltGroups(17, 5, new Group(ShipType.BASE, 1), new Group(ShipType.MINE, 1));
        assertBuiltGroups(24, 5, new Group(ShipType.BASE, 1), new Group(ShipType.MINE, 2));
        assertBuiltGroups(34, 5, new Group(ShipType.BASE, 2), new Group(ShipType.MINE, 2));

        assertBuiltGroups(5, 1, new Group(ShipType.MINE, 1));
    }

    @Test
    public void buyHeavyInfantry() {
        ap.setLevel(Technology.GROUND_COMBAT, 2);
        assertBuiltGroups(17, 5, new Group(ShipType.BASE, 1), new Group(ShipType.MINE, 1));

        roller.mockRoll(1);
        assertBuiltGroups(27, 5, new Group(ShipType.HEAVY_INFANTRY, 1), new Group(ShipType.BASE, 1),
                new Group(ShipType.MINE, 2));

        roller.mockRoll(2);
        assertBuiltGroups(30, 5, new Group(ShipType.HEAVY_INFANTRY, 2), new Group(ShipType.BASE, 1),
                new Group(ShipType.MINE, 2));

        roller.mockRoll(10);
        assertBuiltGroups(35, 5, new Group(ShipType.HEAVY_INFANTRY, 10), new Group(ShipType.MINE, 1));
    }

    @Test
    public void buy2GravIfAble() {
        ap.setLevel(Technology.GROUND_COMBAT, 3);
        assertBuiltGroups(17, 5, new Group(ShipType.BASE, 1), new Group(ShipType.MINE, 1));

        roller.mockRoll(2);
        assertBuiltGroups(26, 5, new Group(ShipType.GRAV_ARMOR, 2), new Group(ShipType.HEAVY_INFANTRY, 2), new Group(ShipType.BASE, 1));
        
        roller.mockRoll(2);
        assertBuiltGroups(38, 5, new Group(ShipType.GRAV_ARMOR, 2), new Group(ShipType.HEAVY_INFANTRY, 2),
                new Group(ShipType.BASE, 1), new Group(ShipType.MINE, 2));
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
