package com.thilian.se4x.robot.game.scenarios.basegame;

import com.thilian.se4x.robot.game.Fixture;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.PlayerColor;

public class BasegameFixture extends Fixture{
    @Override
    protected Game createGame() {
        return Game.newGame(new BaseGameScenario(), BaseGameDifficulty.NORMAL, PlayerColor.GREEN, PlayerColor.YELLOW, PlayerColor.RED);
    }
}
