package com.thilian.se4x.robot.game.scenarios.vpscenario;

import com.thilian.se4x.robot.game.AlienEconomicSheet;

public class VpEconomicSheet extends AlienEconomicSheet {


    public VpEconomicSheet(VpDifficulty difficulty) {
        super(difficulty);
    }

    //@formatter:off
    private static int[][] resultTable = new int[][] {
        { 99, 99, 99},
        {  1,  2, 99},
        {  1,  4, 99},
        {  1,  4,  9},
        {  1,  5,  9},
        {  1,  7, 10},
        {  1,  5, 10},
        {  1,  6, 10},
        {  1,  6, 10},
        {  1,  6, 10},
        {  1,  8, 10},
        {  1,  8, 10},
        {  1,  8, 10},
        {  1,  7, 99},
        {  1,  7, 99},
        {  1,  8, 99},
        {  1,  8, 99},
        {  1,  9, 99},
        {  1,  9, 99},
        {  1, 10, 99},
        {  1, 10, 99},
    };

    private static int[] econRolls = new int[] { -99, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5};

    private static int[] fleetLaunch = new int[] { -99, -99, 10, 10, 5, 10, 4, 10, 4, 5, 6, 4, 6, 3, 10, 3, 10, 3, 10, 3, 10 };

    //@formatter:on


    @Override
    protected int requiredRoll(int turn, int result) {
        return VpEconomicSheet.resultTable[turn][result];
    }

    @Override
    public int getExtraEcon(int turn) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getEconRolls(int turn) {
        return 0;
    }

    @Override
    public int getFleetLaunch(int turn) {
        return VpEconomicSheet.fleetLaunch[turn];
    }


}
