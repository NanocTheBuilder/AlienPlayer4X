package com.thilian.se4x.robot.game.scenarios.basegame;

import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.TechnologyBuyerTestBase;
import com.thilian.se4x.robot.game.enums.PlayerColor;

/**
 * Created by thili on 2018. 01. 05..
 */

public class BasegameTechnologyBuyerTestBase extends TechnologyBuyerTestBase {

    @Override
    protected Game createGame() {
        return Game.newGame(new BaseGameScenario(), BaseGameDifficulty.NORMAL, PlayerColor.GREEN, PlayerColor.YELLOW, PlayerColor.RED);
    }
}
