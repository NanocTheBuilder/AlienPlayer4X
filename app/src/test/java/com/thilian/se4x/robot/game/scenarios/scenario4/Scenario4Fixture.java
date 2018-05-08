package com.thilian.se4x.robot.game.scenarios.scenario4;

import com.thilian.se4x.robot.game.Fixture;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameDifficulty;

public class Scenario4Fixture extends Fixture{
    @Override
    protected Game createGame() {
        return Game.newGame(new Scenario4(), BaseGameDifficulty.NORMAL);
    }
}
