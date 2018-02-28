package com.thilian.se4x.robot.game.scenario4;

import com.thilian.se4x.robot.game.Fixture;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Scenario;

public class Scenario4Fixture extends Fixture{
    @Override
    protected Scenario createScenario(Game game) {
        return new Scenario4(game);
    }
}
