package com.thilian.se4x.robot.game.enums;

import static com.thilian.se4x.robot.game.enums.FleetType.FleetNameSequence.BASIC;
import static com.thilian.se4x.robot.game.enums.FleetType.FleetNameSequence.DEFENSE;
import static com.thilian.se4x.robot.game.enums.FleetType.FleetNameSequence.RAIDER;

public enum FleetType {
    REGULAR_FLEET(BASIC), RAIDER_FLEET(RAIDER), DEFENSE_FLEET(DEFENSE), EXPANSION_FLEET(BASIC), EXTERMINATION_FLEET(BASIC);

    static enum FleetNameSequence{
        BASIC, DEFENSE, RAIDER;
    }

    private FleetNameSequence sequence;

    FleetType(FleetNameSequence sequence) {
        this.sequence = sequence;
    }

    public boolean isSameNameSequence(FleetType other){
        return sequence.equals(other.sequence);
    }
}
