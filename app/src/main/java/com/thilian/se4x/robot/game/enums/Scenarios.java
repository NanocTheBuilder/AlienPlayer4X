package com.thilian.se4x.robot.game.enums;

import com.thilian.se4x.robot.game.Scenario;
import com.thilian.se4x.robot.game.basegame.BaseGameScenario;
import com.thilian.se4x.robot.game.scenario4.Scenario4;

public enum Scenarios {
    BASE_GAME(BaseGameScenario.class), SCENARIO_4(Scenario4.class);


    public Class<Scenario> getClazz() {
        return clazz;
    }

    private Class<Scenario> clazz;

    private Scenarios(Class clazz){
        this.clazz = clazz;
    }
}
