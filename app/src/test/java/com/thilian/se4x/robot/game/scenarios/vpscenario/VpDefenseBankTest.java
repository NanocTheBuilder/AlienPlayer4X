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

    public void spendDefCPFromBankIfAble(){
        VpEconomicSheet sheet = new VpEconomicSheet(VpSoloDifficulty.NORMAL);
        sheet.setDefCP(50);
        assertEquals(100, sheet.getBank());

        sheet.spendDefCP(50);
        assertEquals(50, sheet.getDefCP());
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
