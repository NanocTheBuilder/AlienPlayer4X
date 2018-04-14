package com.thilian.se4x.robot.game.enums;

public enum FleetBuildOptions {
    COMBAT_IS_ABOVE_PLANET;

    public static boolean isOption(FleetBuildOptions option, FleetBuildOptions[] options){
        return options.length != 0 && options[0].equals(option);
    }
}
