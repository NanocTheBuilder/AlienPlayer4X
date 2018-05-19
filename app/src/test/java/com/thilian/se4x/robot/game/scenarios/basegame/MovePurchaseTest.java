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

import org.junit.Test;

import com.thilian.se4x.robot.game.enums.Technology;

public class MovePurchaseTest extends BasegameFixture{


    @Test
    public void buyNextMoveAtFleetLaunch() {
        assertEquals(1, ap.getLevel(Technology.MOVE));
        assertMovePuchase(1, 20, 2, 0);
        assertMovePuchase(2, 25, 3, 0);
        assertMovePuchase(3, 25, 4, 0);
        assertMovePuchase(4, 25, 5, 0);
        assertMovePuchase(5, 20, 6, 0);
        assertMovePuchase(6, 20, 7, 0);
        assertMovePuchase(7, 100, 7, 100);
    }

    @Test
    public void dontBuyMoveIfNoCP() {
        assertMovePuchase(2, 10, 2, 10);
    }

    @Test
    public void dontBuyMoveIfRollFails() {
        ap.setLevel(Technology.MOVE, 3);
        sheet.setTechCP(10);
        roller.mockRoll(5);
        ap.buyNextMoveLevel();
        assertEquals(3, ap.getLevel(Technology.MOVE));
        assertEquals(10, sheet.getTechCP());
    }

    private void assertMovePuchase(int level, int techCP, int newLevel, int newTechCP) {
        ap.setLevel(Technology.MOVE, level);
        sheet.setTechCP(techCP);
        roller.mockRoll(1);
        boolean result = ap.buyNextMoveLevel();
        assertEquals(level != newLevel, result);
        assertEquals(newLevel, ap.getLevel(Technology.MOVE));
        assertEquals(newTechCP, sheet.getTechCP());
    }
}
