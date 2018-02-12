package com.thilian.se4x.robot.game.scenario4;

import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.TechnologyBuyer;
import com.thilian.se4x.robot.game.TechnologyPrices;
import com.thilian.se4x.robot.game.TechnologyBuyerTestBase;

/**
 * Created by thili on 2018. 01. 05..
 */

public class Scenario4TechnologyBuyerTestBase extends TechnologyBuyerTestBase {

    @Override
    protected TechnologyBuyer getTechBuyer(Game game) {
        return new Scenario4TechnologyBuyer(game);
    }

    @Override
    protected TechnologyPrices getTechPrices() {
        return new Scenario4TechnologyPrices();
    }
}
