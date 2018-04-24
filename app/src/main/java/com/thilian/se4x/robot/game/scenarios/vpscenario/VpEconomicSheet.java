package com.thilian.se4x.robot.game.scenarios.vpscenario;

import com.thilian.se4x.robot.game.AlienEconomicSheet;
import com.thilian.se4x.robot.game.DiceRoller;
import com.thilian.se4x.robot.game.EconRollResult;

public class VpEconomicSheet extends AlienEconomicSheet {


    private int bank;

    public VpEconomicSheet(VpDifficulty difficulty) {
        super(difficulty);
        bank = difficulty.getStartingBank();
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
    public EconRollResult makeRoll(int turn, DiceRoller roller) {
        EconRollResult result = new EconRollResult();
        int limit = 10;
        if(defCP == ((VpDifficulty)difficulty).getMaxDefenseCp()
                && requiredRoll(turn, RESULT_DEF) != 99)
        {
            limit = requiredRoll(turn, RESULT_DEF) - 1;
        }

        int roll = roller.roll(limit);
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
        else {
            int fleetCP = difficulty.getCPPerEcon();
            this.fleetCP += fleetCP;
            result.setFleetCP(fleetCP);
        }

        int maxDefenseCp = ((VpDifficulty)difficulty).getMaxDefenseCp();
        if(defCP > maxDefenseCp){
            defCP = maxDefenseCp;
        }
        return result;
    }

    @Override
    protected int requiredRoll(int turn, int result) {
        return VpEconomicSheet.resultTable[turn][result];
    }

    @Override
    public int getExtraEcon(int turn) {
        return 0;
    }

    @Override
    public int getEconRolls(int turn) {
        return econRolls[turn];
    }

    @Override
    public int getFleetLaunch(int turn) {
        return VpEconomicSheet.fleetLaunch[turn];
    }

    public boolean isMaxDefCP() {
        return defCP == ((VpDifficulty)difficulty).getMaxDefenseCp();
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public void spendBank(int amount){
        bank -= amount;
    }

    @Override
    public void spendDefCP(int amount) {
        defCP -= amount > bank ? amount - bank : 0;
        bank -= amount > bank ? bank : amount;
    }
}
