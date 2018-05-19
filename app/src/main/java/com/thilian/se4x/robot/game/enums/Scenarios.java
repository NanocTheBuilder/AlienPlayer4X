/*
 * Copyright (C) 2018 Balázs Péter
 *
 * This file is part of Alien Player 4X.
 *
 * Alien Player 4X is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alien Player 4X is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Alien Player 4X.  If not, see <http://www.gnu.org/licenses/>.
 */

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

    Scenarios(Class<? extends Scenario> clazz){
        this.clazz = clazz;
    }
}
