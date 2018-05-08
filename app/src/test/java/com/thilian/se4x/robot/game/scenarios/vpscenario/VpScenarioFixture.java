package com.thilian.se4x.robot.game.scenarios.vpscenario;

import com.thilian.se4x.robot.game.Fixture;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpDifficulties.VpSoloDifficulty;

public class VpScenarioFixture extends Fixture{
    @Override
    protected Game createGame() {
        return Game.newGame(new VpSoloScenario(), VpSoloDifficulty.NORMAL);
    }
}
