package com.thilian.se4x.robot.game.scenarios.vpscenario;

import com.thilian.se4x.robot.game.Fixture;

public class VpScenarioFixture extends Fixture{
    @Override
    protected void createGame() {
        game.createGame(new VpSoloScenario(), VpDifficulty.NORMAL);
    }
}
