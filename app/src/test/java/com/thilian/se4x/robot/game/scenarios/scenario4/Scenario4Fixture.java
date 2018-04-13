package com.thilian.se4x.robot.game.scenarios.scenario4;

import com.thilian.se4x.robot.game.Fixture;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameDifficulty;

public class Scenario4Fixture extends Fixture{
    @Override
    protected void createGame() {
        game.createGame(BaseGameDifficulty.NORMAL, new Scenario4());
    }
}