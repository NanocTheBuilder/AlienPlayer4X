package com.thilian.se4x.robot.game.scenarios.basegame;

import com.thilian.se4x.robot.game.Scenario;
import com.thilian.se4x.robot.game.TechnologyBuyerTestBase;

/**
 * Created by thili on 2018. 01. 05..
 */

public class BasegameTechnologyBuyerTestBase extends TechnologyBuyerTestBase {
    @Override
    protected Scenario getScenario() {
        return new BaseGameScenario();
    }
}
