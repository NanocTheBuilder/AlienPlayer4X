package com.thilian.se4x.robot.game.basegame;

import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.TechnologyBuyer;
import com.thilian.se4x.robot.game.TechnologyPrices;
import com.thilian.se4x.robot.game.TechnologyBuyerTestBase;

/**
 * Created by thili on 2018. 01. 05..
 */

public class BasegameTechnologyBuyerTestBase extends TechnologyBuyerTestBase {

    @Override
    protected TechnologyBuyer getTechBuyer(Game game) {
        return new BaseGameTechnologyBuyer(game);
    }

    @Override
    protected TechnologyPrices getTechProces() {
        return new BaseGameTechnologyPrices();
    }
}
