package com.thilian.se4x.robot.game.enums;

public enum FleetBuildOption {
    COMBAT_IS_ABOVE_PLANET, HOME_DEFENSE, COMBAT_WITH_NPAS;

    public static boolean isOption(FleetBuildOption option, FleetBuildOption[] options){
        for(FleetBuildOption opt : options){
            if(opt.equals(option))
                    return true;
        };
        return false;
    }
}
