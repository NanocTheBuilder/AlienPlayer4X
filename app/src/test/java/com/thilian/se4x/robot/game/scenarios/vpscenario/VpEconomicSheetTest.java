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

import org.junit.Assert;
import org.junit.Test;

import com.thilian.se4x.robot.game.AlienEconomicSheet;
import com.thilian.se4x.robot.game.EconomicRollTest;
import com.thilian.se4x.robot.game.MockRoller;
import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpDifficulties.VpSoloDifficulty;

public class VpEconomicSheetTest extends EconomicRollTest{

    private int[][][] resultTable = {
            {{     }, {     }, {      }, {      }}, 
            {{     }, {    1}, { 2, 10}, {      }}, 
            {{     }, { 1, 3}, { 4, 10}, {      }}, 
            {{     }, { 1, 3}, { 4,  8}, { 9, 10}}, 
            {{     }, { 1, 4}, { 5,  8}, { 9, 10}}, 
            {{     }, { 1, 6}, { 7,  9}, {    10}}, 
            {{     }, { 1, 4}, { 5,  9}, {    10}}, 
            {{     }, { 1, 5}, { 6,  9}, {    10}}, 
            {{     }, { 1, 5}, { 6,  9}, {    10}}, 
            {{     }, { 1, 5}, { 6,  9}, {    10}}, 
            {{     }, { 1, 7}, { 8,  9}, {    10}}, 
            {{     }, { 1, 7}, { 8,  9}, {    10}}, 
            {{     }, { 1, 7}, { 8,  9}, {    10}}, 
            {{     }, { 1, 6}, { 7, 10}, {      }}, 
            {{     }, { 1, 6}, { 7, 10}, {      }}, 
            {{     }, { 1, 7}, { 8, 10}, {      }}, 
            {{     }, { 1, 7}, { 8, 10}, {      }}, 
            {{     }, { 1, 8}, { 9, 10}, {      }}, 
            {{     }, { 1, 8}, { 9, 10}, {      }}, 
            {{     }, { 1, 9}, {    10}, {      }}, 
            {{     }, { 1, 9}, {    10}, {      }}, 
        };

    @Test
    public void testMaxDefense(){
        VpEconomicSheet sheet = new VpEconomicSheet(VpSoloDifficulty.EASY);
        sheet.setDefCP(49);
        makeRoll(sheet, 3, 10);
        Assert.assertEquals(50, sheet.getDefCP());
    }

    @Test
    public void testIsMaxDefense(){
        VpEconomicSheet sheet = new VpEconomicSheet(VpSoloDifficulty.EASY);
        Assert.assertEquals(false, sheet.isMaxDefCP());
        sheet.setDefCP(50);
        Assert.assertEquals(true, sheet.isMaxDefCP());
    }

    @Test
    public void rerollDefIfIsMaxDefense(){
        VpEconomicSheet sheet = new VpEconomicSheet(VpSoloDifficulty.EASY);
        sheet.setDefCP(50);
        
        MockRoller roller = new MockRoller();
        roller.mockRoll(9, 9);
        sheet.makeRoll(5, roller);
        Assert.assertEquals(50, sheet.getDefCP());
        Assert.assertEquals(5, sheet.getTechCP());
    }
    
    @Override
    protected Difficulty[] getDifficulties() {
        return VpSoloDifficulty.values();
    }
    
    @Override
    public void testEconResult() {
    }

    @Override
    protected int[] getEconRolls() {
        return new int[]{ 0, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5};
    }
    
    @Override
    protected int[] getFleetLaunchValues() {
        return new int[] { 0, -99, 10, 10, 5, 10, 4, 10, 4, 5, 6, 4, 6, 3, 10, 3, 10, 3, 10, 3, 10};
    }
    
    @Override
    protected AlienEconomicSheet getEconomicSheet() {
        return getEconomicSheet(VpSoloDifficulty.EASY);
    }
    
    @Override
    protected AlienEconomicSheet getEconomicSheet(Difficulty diff) {
        VpEconomicSheet sheet = new VpEconomicSheet((VpDifficulty) diff);
        sheet.setBank(0);
        return sheet;
    }
    
    @Override
    protected int[] getResult(int turn, int index) {
        return resultTable[turn][index];
    }
}
