package com.thilian.se4x.robot.game.scenarios.vpscenario;

import org.junit.Assert;
import org.junit.Test;

import com.thilian.se4x.robot.game.AlienEconomicSheet;
import com.thilian.se4x.robot.game.EconomicRollTest;
import com.thilian.se4x.robot.game.MockRoller;
import com.thilian.se4x.robot.game.enums.Difficulty;

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
        VpEconomicSheet sheet = new VpEconomicSheet(VpDifficulty.EASY);
        sheet.setDefCP(49);
        makeRoll(sheet, 3, 10);
        Assert.assertEquals(50, sheet.getDefCP());
    }

    @Test
    public void testIsMaxDefense(){
        VpEconomicSheet sheet = new VpEconomicSheet(VpDifficulty.EASY);
        Assert.assertEquals(false, sheet.isMaxDefCP());
        sheet.setDefCP(50);
        Assert.assertEquals(true, sheet.isMaxDefCP());
    }

    @Test
    public void rerollDefIfIsMaxDefense(){
        VpEconomicSheet sheet = new VpEconomicSheet(VpDifficulty.EASY);
        sheet.setDefCP(50);
        
        MockRoller roller = new MockRoller();
        roller.mockRoll(9, 9);
        sheet.makeRoll(5, roller);
        Assert.assertEquals(50, sheet.getDefCP());
        Assert.assertEquals(5, sheet.getTechCP());
    }
    
    @Override
    protected Difficulty[] getDifficulties() {
        return VpDifficulty.values();
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
        return getEconomicSheet(VpDifficulty.EASY);
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
