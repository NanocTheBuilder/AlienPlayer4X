package com.thilian.se4x.robot.game.basegame;

import com.thilian.se4x.robot.game.Fixture;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Scenario;

public class BasegameFixture extends Fixture{
    @Override
    protected Scenario createScenario(Game game) {
        return new BaseGameScenario(game);
    }
}
