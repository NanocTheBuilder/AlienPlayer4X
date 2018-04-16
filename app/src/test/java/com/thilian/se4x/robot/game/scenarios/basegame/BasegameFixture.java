package com.thilian.se4x.robot.game.scenarios.basegame;

import com.thilian.se4x.robot.game.Fixture;

public class BasegameFixture extends Fixture{
    @Override
    protected void createGame() {
        game.createGame(new BaseGameScenario(), BaseGameDifficulty.NORMAL);
    }
}
