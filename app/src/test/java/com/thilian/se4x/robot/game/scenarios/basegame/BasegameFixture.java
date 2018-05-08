package com.thilian.se4x.robot.game.scenarios.basegame;

import com.thilian.se4x.robot.game.Fixture;
import com.thilian.se4x.robot.game.Game;

public class BasegameFixture extends Fixture{
    @Override
    protected Game createGame() {
        return Game.newGame(new BaseGameScenario(), BaseGameDifficulty.NORMAL);
    }
}
