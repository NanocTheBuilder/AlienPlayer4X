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
