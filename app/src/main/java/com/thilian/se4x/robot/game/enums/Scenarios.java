package com.thilian.se4x.robot.game.enums;

import com.thilian.se4x.robot.game.Scenario;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameScenario;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4;
import com.thilian.se4x.robot.game.scenarios.vpscenario.Vp2pScenario;
import com.thilian.se4x.robot.game.scenarios.vpscenario.Vp3pScenario;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpSoloScenario;

public enum Scenarios {
    BASE_GAME(BaseGameScenario.class), 
    SCENARIO_4(Scenario4.class), 
    VP_SOLO_SCENARIO(VpSoloScenario.class),
    VP_2P_SCENARIO(Vp2pScenario.class),
    VP_3P_SCENARIO(Vp3pScenario.class),
    
    ;

    public Class<? extends Scenario> getClazz() {
        return clazz;
    }

    private Class<? extends Scenario> clazz;

    private Scenarios(Class<? extends Scenario> clazz){
        this.clazz = clazz;
    }
}
