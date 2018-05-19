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

import com.thilian.se4x.robot.game.DiceRoller;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Assert;

public class MockRoller implements DiceRoller {

    private static final int LIMIT = 0;
    private static final int RESULT = 1;
    public Queue<int[]> rolls = new LinkedList<>();

    public void mockRoll(int roll) {
        mockRoll(10, roll);
    }

    public void mockRoll(int limit, int roll) {
        rolls.add(new int[]{limit, roll});
}

    @Override
    public int roll() {
        return roll(10);
    }

    @Override
    public int roll(int limit) {
        int[] call = rolls.remove();
        Assert.assertEquals(call[LIMIT], limit);
        return call[RESULT];
    }

}