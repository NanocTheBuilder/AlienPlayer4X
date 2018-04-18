package com.thilian.se4x.robot.game.enums;

import com.thilian.se4x.robot.game.Scenario;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameScenario;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpScenario;

public enum Scenarios {
    BASE_GAME(BaseGameScenario.class), SCENARIO_4(Scenario4.class), VP_SCENARIO(VpScenario.class);

    public Class<? extends Scenario> getClazz() {
        return clazz;
    }

    private Class<? extends Scenario> clazz;

    private Scenarios(Class clazz){
        this.clazz = clazz;
    }
}
