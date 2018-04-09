package com.thilian.se4x.robot.game.scenarios.vpscenario;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Scenario;
import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.PlayerColor;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4;

public class VpScenario extends Scenario4 {
    @Override
    public void init(Game game) {

    }

    @Override
    public AlienPlayer newPlayer(Game game, Difficulty difficulty, PlayerColor color) {
        return null;
    }

    @Override
    public Difficulty[] getDifficulties() {
        return VpDifficulty.values();
    }
}
