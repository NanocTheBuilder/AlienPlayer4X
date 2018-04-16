package com.thilian.se4x.robot.game.enums;

public enum FleetBuildOption {
    COMBAT_IS_ABOVE_PLANET, HOME_DEFENSE;

    public static boolean isOption(FleetBuildOption option, FleetBuildOption[] options){
        for(FleetBuildOption opt : options){
            if(option.equals(option))
                    return true;
        };
        return false;
    }
}
