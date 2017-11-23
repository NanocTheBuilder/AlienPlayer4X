package com.thilian.se4x.robot.game;

import java.util.Random;

public class RealDiceRoller implements DiceRoller {

    private final Random random;

    public RealDiceRoller(){
        random = new Random();
    }

    @Override
    public int roll() {
        return roll(10);
    }

    @Override
    public int roll(int bound) {
        return random.nextInt(bound) + 1;
    }
}
