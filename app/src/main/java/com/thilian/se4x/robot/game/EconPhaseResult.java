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

package com.thilian.se4x.robot.game;

public class EconPhaseResult extends EconRollResult {
    private Fleet fleet;
    private AlienPlayer alienPlayer;
    private boolean moveTechRolled;

    public EconPhaseResult(AlienPlayer alienPlayer) {
        this.alienPlayer = alienPlayer;
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    public Fleet getFleet() {
        return fleet;
    }

    public AlienPlayer getAlienPlayer() {
        return alienPlayer;
    }

    public boolean getMoveTechRolled() {
        return moveTechRolled;
    }

    public boolean isMoveTechRolled() {
        return moveTechRolled;
    }

    public void setMoveTechRolled(boolean moveTechRolled) {
        this.moveTechRolled = moveTechRolled;
    }
}