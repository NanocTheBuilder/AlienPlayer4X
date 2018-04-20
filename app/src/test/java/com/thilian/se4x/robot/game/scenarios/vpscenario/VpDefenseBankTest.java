package com.thilian.se4x.robot.game.scenarios.vpscenario;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.thilian.se4x.robot.game.scenarios.vpscenario.VpDifficulties.Vp2pDifficulty;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpDifficulties.Vp3pDifficulty;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpDifficulties.VpSoloDifficulty;

public class VpDefenseBankTest {

    @Test
    public void testStartingBank(){
        Assert.assertEquals(0, new VpEconomicSheet(VpSoloDifficulty.EASY).getBank());
        Assert.assertEquals(100, new VpEconomicSheet(VpSoloDifficulty.NORMAL).getBank());
        Assert.assertEquals(100, new VpEconomicSheet(VpSoloDifficulty.HARD).getBank());

        Assert.assertEquals(150, new VpEconomicSheet(Vp2pDifficulty.EASY).getBank());
        Assert.assertEquals(150, new VpEconomicSheet(Vp2pDifficulty.NORMAL).getBank());
        Assert.assertEquals(150, new VpEconomicSheet(Vp2pDifficulty.HARD).getBank());

        Assert.assertEquals(200, new VpEconomicSheet(Vp3pDifficulty.EASY).getBank());
        Assert.assertEquals(200, new VpEconomicSheet(Vp3pDifficulty.NORMAL).getBank());
        Assert.assertEquals(200, new VpEconomicSheet(Vp3pDifficulty.HARD).getBank());
}

    @Test
    public void getDefenseCPAddsBankIfAvailable(){
        VpEconomicSheet sheet = new VpEconomicSheet(VpSoloDifficulty.NORMAL);
        assertEquals(100, sheet.getDefCP());

        sheet.setBank(25);
        assertEquals(25, sheet.getDefCP());

        sheet.setBank(0);
        sheet.setDefCP(20);
        assertEquals(20, sheet.getDefCP());
    }

    @Test
    public void spendDefCPFromBankIfAble(){
        VpEconomicSheet sheet = new VpEconomicSheet(VpSoloDifficulty.NORMAL);
        sheet.setDefCP(50);
        assertEquals(150, sheet.getDefCP());
        assertEquals(100, sheet.getBank());

        sheet.spendDefCP(50);
        assertEquals(100, sheet.getDefCP());
        assertEquals(50, sheet.getBank());

        sheet.setBank(25);
        sheet.spendDefCP(50);
        assertEquals(25, sheet.getDefCP());
        assertEquals(0, sheet.getBank());

        sheet.spendDefCP(5);
        assertEquals(20, sheet.getDefCP());
        assertEquals(0, sheet.getBank());

        sheet.spendDefCP(20);
        assertEquals(0, sheet.getDefCP());
        assertEquals(0, sheet.getBank());
    }
}
