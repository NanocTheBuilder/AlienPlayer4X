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

public class EconRollResult {
    private int fleetCP;
    private int techCP;
    private int defCP;
    private int extraEcon;

    public int getFleetCP() {
        return fleetCP;
    }

    public void setFleetCP(int fleetCP) {
        this.fleetCP = fleetCP;
    }

    public int getTechCP() {
        return techCP;
    }

    public void setTechCP(int techCP) {
        this.techCP = techCP;
    }

    public int getDefCP() {
        return defCP;
    }

    public void setDefCP(int defCP) {
        this.defCP = defCP;
    }

    public void setExtraEcon(int extraEcon) {
        this.extraEcon = extraEcon;
    }

    public int getExtraEcon() {
        return extraEcon;
    }

    public void add(EconRollResult rollResult) {
        fleetCP += rollResult.fleetCP;
        techCP += rollResult.techCP;
        defCP += rollResult.defCP;
        extraEcon += rollResult.extraEcon;
    }
}
