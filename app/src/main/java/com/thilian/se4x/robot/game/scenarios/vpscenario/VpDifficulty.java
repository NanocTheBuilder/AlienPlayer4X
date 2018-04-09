package com.thilian.se4x.robot.game.scenarios.vpscenario;

import com.thilian.se4x.robot.game.enums.Difficulty;

public enum VpDifficulty implements Difficulty {
    EASY(2, 5, 0, 50), NORMAL(2, 10, 100, 50), HARD(2, 15, 100, 50);

    private final int numberOfAPs;
    private final int cpPerEcon;
    private final int startingBank;
    private final int maxDefenseCp;

    VpDifficulty(int numberOfAPs, int cpPerEcon, int startingBank, int maxDefenseCp) {

        this.numberOfAPs = numberOfAPs;
        this.cpPerEcon = cpPerEcon;
        this.startingBank = startingBank;
        this.maxDefenseCp = maxDefenseCp;
    }

    @Override
    public int getCPPerEcon() {
        return cpPerEcon;
    }

    @Override
    public int getNumberOfAlienPlayers() {
        return numberOfAPs;
    }

    public int getStartingBank() {
        return startingBank;
    }

    public int getMaxDefenseCp() {
        return maxDefenseCp;
    }
}
