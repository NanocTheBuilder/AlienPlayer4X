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

    public void applyRoll(int turn, int result) {
        if (result >= requiredRoll(turn, RESULT_DEF))
            defCP += 2 * difficulty.getCPPerEcon();
        else if (result >= requiredRoll(turn, RESULT_TECH))
            techCP += difficulty.getCPPerEcon();
        else if (result >= requiredRoll(turn, RESULT_FLEET))
            fleetCP += difficulty.getCPPerEcon();
        else {
            for (int i = turn + 3; i < 21; i++)
                extraEcon[i] += 1;
        }
    }

    protected int requiredRoll(int turn, int result) {
        return AlienEconomicSheet.resultTable[turn][result];
    }

    public int getExtraEcon(int turn) {
        return extraEcon[turn];
    }

    public int getEconRolls(int turn) {
        return AlienEconomicSheet.econRolls[turn];
    }

    public int getFleetLaunch(int turn) {
        return AlienEconomicSheet.fleetLaunch[turn];
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

    public void setDefCP(int defCP) {
        this.defCP = defCP;
    }

    public void setFleetCP(int fleetCP) {
        this.fleetCP = fleetCP;
    }

    public void setTechCP(int techCP) {
        this.techCP = techCP;
    }
}
