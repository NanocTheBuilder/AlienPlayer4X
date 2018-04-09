package com.thilian.se4x.robot.game.scenarios.basegame;

import com.thilian.se4x.robot.game.Fixture;
import com.thilian.se4x.robot.game.Scenario;

public class BasegameFixture extends Fixture{
    @Override
    protected Scenario getScenario() {
        return new BaseGameScenario();
    }
}
