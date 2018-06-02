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

import com.thilian.se4x.robot.game.enums.Difficulty;

public class AlienEconomicSheet {

    protected static final int RESULT_FLEET = 0;
    protected static final int RESULT_TECH = 1;
    protected static final int RESULT_DEF = 2;

    //@formatter:off
    private static int[][] resultTable = new int[][] {
        { 99, 99, 99},
        { 99,  3, 99},
        {  2,  4, 99},
        {  2,  5,  9},
        {  2,  6,  9},
        {  2,  6, 10},
        {  2,  7, 10},
        {  1,  6, 10},
        {  1,  6, 10},
        {  1,  6, 10},
        {  1,  7, 10},
        {  1,  7, 10},
        {  1,  7, 10},
        {  1,  7, 99},
        {  1,  7, 99},
        {  1,  8, 99},
        {  1,  8, 99},
        {  1,  9, 99},
        {  1,  9, 99},
        {  1, 10, 99},
        {  1, 10, 99},
    };

    private static int[] econRolls = new int[] { -99, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5 };

    private static int[] fleetLaunch = new int[] { -99, -99, 10, 10, 5, 3, 4, 4, 4, 5, 5, 3, 3, 3, 10, 3, 10, 3, 10, 3, 10 };

    //@formatter:on

    protected Difficulty difficulty;
    protected int fleetCP = 0;
    protected int techCP = 0;
    protected int defCP = 0;
    int[] extraEcon = new int[21];

    public AlienEconomicSheet(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public EconRollResult makeRoll(int turn, DiceRoller roller) {
        EconRollResult result = new EconRollResult();
        int roll = roller.roll();
        if (roll >= requiredRoll(turn, RESULT_DEF)) {
            int defCP = 2 * difficulty.getCPPerEcon();
            this.defCP += defCP;
            result.setDefCP(defCP);
        }
        else if (roll >= requiredRoll(turn, RESULT_TECH)) {
            int techCP = difficulty.getCPPerEcon();
            this.techCP += techCP;
            result.setTechCP(techCP);
        }
        else if (roll >= requiredRoll(turn, RESULT_FLEET)) {
            int fleetCP = difficulty.getCPPerEcon();
            this.fleetCP += fleetCP;
            result.setFleetCP(fleetCP);
        }
        else {
            for (int i = turn + 3; i < 21; i++)
                extraEcon[i] += 1;
            result.setExtraEcon(1);
        }
        return result;
    }

    protected int requiredRoll(int turn, int result) {
        return AlienEconomicSheet.resultTable[getResultTableRow(turn)][result];
    }

    protected int getResultTableRow(int turn) {
        return turn < 20 ?  turn :  20 - (turn % 2);
    }

    public int getExtraEcon(int turn) {
        return extraEcon[getResultTableRow(turn)];
    }

    public int getEconRolls(int turn) {
        return AlienEconomicSheet.econRolls[getResultTableRow(turn)];
    }

    public int getFleetLaunch(int turn) {
        return AlienEconomicSheet.fleetLaunch[getResultTableRow(turn)];
    }

    public void spendFleetCP(int ammount) {
        fleetCP -= ammount;
    }

    public void addFleetCP(int ammount) {
        fleetCP += ammount;
    }

    public void spendTechCP(int ammount) {
        techCP -= ammount;
    }

    public void spendDefCP(int ammount) {
        defCP -= ammount;
    }

    public int getTechCP() {
        return techCP;
    }

    public int getDefCP() {
        return defCP;
    }

    public int getFleetCP() {
        return fleetCP;
    }

    //only for testing
    public void setDefCP(int defCP) {
        this.defCP = defCP;
    }

    //only for testing
    public void setFleetCP(int fleetCP) {
        this.fleetCP = fleetCP;
    }

    //only for testing
    public void setTechCP(int techCP) {
        this.techCP = techCP;
    }
}
