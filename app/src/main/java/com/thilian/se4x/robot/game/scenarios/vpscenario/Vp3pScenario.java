package com.thilian.se4x.robot.game.scenarios.vpscenario;

import java.util.Arrays;
import java.util.List;

import com.thilian.se4x.robot.game.enums.Difficulty;

public class Vp3pScenario extends VpSoloScenario {
    @Override
    public List<? extends Difficulty> getDifficulties() {
        return Arrays.asList(VpDifficulties.Vp3pDifficulty.values());
    }
}