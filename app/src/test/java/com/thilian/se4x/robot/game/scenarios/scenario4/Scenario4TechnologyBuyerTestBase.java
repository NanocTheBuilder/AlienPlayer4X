package com.thilian.se4x.robot.game.scenarios.scenario4;

import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.TechnologyBuyerTestBase;
import com.thilian.se4x.robot.game.enums.PlayerColor;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameDifficulty;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameScenario;

/**
 * Created by thili on 2018. 01. 05..
 */

public class Scenario4TechnologyBuyerTestBase extends TechnologyBuyerTestBase {
    @Override
    protected Game createGame() {
        return Game.newGame(new Scenario4(), BaseGameDifficulty.NORMAL, PlayerColor.GREEN, PlayerColor.YELLOW, PlayerColor.RED);
    }
}
