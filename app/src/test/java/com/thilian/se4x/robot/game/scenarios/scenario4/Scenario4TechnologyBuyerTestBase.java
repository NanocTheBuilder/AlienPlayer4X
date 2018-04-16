package com.thilian.se4x.robot.game.scenarios.scenario4;

import com.thilian.se4x.robot.game.TechnologyBuyerTestBase;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameDifficulty;

/**
 * Created by thili on 2018. 01. 05..
 */

public class Scenario4TechnologyBuyerTestBase extends TechnologyBuyerTestBase {
    @Override
    protected void createGame() {
        game.createGame(new Scenario4(), BaseGameDifficulty.NORMAL);
    }
}
