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