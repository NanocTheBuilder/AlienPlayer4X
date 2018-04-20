package com.thilian.se4x.robot.game.scenarios.vpscenario;

import com.thilian.se4x.robot.game.enums.Difficulty;

public interface VpDifficulty extends Difficulty {
    public int getStartingBank();
    public int getMaxDefenseCp();
}
