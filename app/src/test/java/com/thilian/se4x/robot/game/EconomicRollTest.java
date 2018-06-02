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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameDifficulty;

public class EconomicRollTest {

    //@formatter:off
    private int[][][] resultTable = {
        {{     }, {     }, {      }, {      }},	
        {{ 1, 2}, {     }, { 3, 10}, {      }},	
        {{    1}, { 2, 3}, { 4, 10}, {      }},	
        {{    1}, { 2, 4}, { 5,  8}, { 9, 10}},	
        {{    1}, { 2, 5}, { 6,  8}, { 9, 10}},	
        {{    1}, { 2, 5}, { 6,  9}, {    10}},	
        {{    1}, { 2, 6}, { 7,  9}, {    10}},	
        {{     }, { 1, 5}, { 6,  9}, {    10}},	
        {{     }, { 1, 5}, { 6,  9}, {    10}},	
        {{     }, { 1, 5}, { 6,  9}, {    10}},	
        {{     }, { 1, 6}, { 7,  9}, {    10}},	
        {{     }, { 1, 6}, { 7,  9}, {    10}},	
        {{     }, { 1, 6}, { 7,  9}, {    10}},	
        {{     }, { 1, 6}, { 7, 10}, {      }},	
        {{     }, { 1, 6}, { 7, 10}, {      }},	
        {{     }, { 1, 7}, { 8, 10}, {      }},	
        {{     }, { 1, 7}, { 8, 10}, {      }},	
        {{     }, { 1, 8}, { 9, 10}, {      }},	
        {{     }, { 1, 8}, { 9, 10}, {      }},	
        {{     }, { 1, 9}, {    10}, {      }},	
        {{     }, { 1, 9}, {    10}, {      }},
        {{     }, { 1, 9}, {    10}, {      }},
        {{     }, { 1, 9}, {    10}, {      }},
    };
    //@formatter:on

    @Test
    public void testCPResults() {
        for (int turn = 1; turn < 23; turn++) {
            for (Difficulty diff : getDifficulties()) {
                assertFleetResults(turn, diff);
                assertTechResults(turn, diff);
                assertDefResults(turn, diff);
            }
        }
    }

    protected Difficulty[] getDifficulties() {
        return BaseGameDifficulty.values();
    }

    @Test
    public void testEconResult() {
        AlienEconomicSheet sheet = getEconomicSheet(BaseGameDifficulty.EASY);
        assertEquals(0, sheet.getExtraEcon(4));
        makeRoll(sheet, 1, 1);
        makeRoll(sheet, 2, 1);
        assertEquals(1, sheet.getExtraEcon(4));
        assertEquals(2, sheet.getExtraEcon(5));
        assertEquals(2, sheet.getExtraEcon(6));
        makeRoll(sheet, 7, 1);
        assertEquals(2, sheet.getExtraEcon(10));
        assertEquals(2, sheet.getExtraEcon(98));
        assertEquals(2, sheet.getExtraEcon(99));
    }

    protected void makeRoll(AlienEconomicSheet sheet, int turn, int result){
        MockRoller roller = new MockRoller();
        roller.mockRoll(result);
        sheet.makeRoll(turn, roller);
    }
    @Test
    public void testEconRollsColumn() {
        int[] expected = getEconRolls();
        AlienEconomicSheet sheet = getEconomicSheet();
        for (int turn = 1; turn < 23; turn++)
            assertEquals(expected[turn], sheet.getEconRolls(turn));
    }

    protected int[] getEconRolls() {
        return new int[] { 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5 };
    }

    @Test
    public void testFleetLaunch() {
        int[] expected = getFleetLaunchValues();
        AlienEconomicSheet sheet = getEconomicSheet();
        for (int turn = 1; turn < 23; turn++)
            assertEquals(expected[turn], sheet.getFleetLaunch(turn));
    }

    protected int[] getFleetLaunchValues() {
        return new int[] { 0, -99, 10, 10, 5, 3, 4, 4, 4, 5, 5, 3, 3, 3, 10, 3, 10, 3, 10, 3, 10, 3, 10 };
    }

    private void assertFleetResults(int turn, Difficulty diff) {
        for (Integer i : getFleetRange(turn)) {
            assertIsFleet(turn, diff, i);
        }
    }

    private void assertTechResults(int turn, Difficulty diff) {
        for (Integer i : getTechRange(turn)) {
            assertIsTech(turn, diff, i);
        }
    }

    private void assertDefResults(int turn, Difficulty diff) {
        for (Integer i : getDefRange(turn)) {
            assertIsDef(turn, diff, i);
        }
    }

    private List<Integer> getFleetRange(int turn) {
        return getRange(turn, 1);
    }

    private void assertIsFleet(int turn, Difficulty diff, int result) {
        AlienEconomicSheet sheet = getEconomicSheet(diff);
        makeRoll(sheet, turn, result);
        assertEquals(String.format("turn %d roll %d is not Fleet", turn, result), diff.getCPPerEcon(),
                sheet.getFleetCP());
    }

    private List<Integer> getTechRange(int turn) {
        return getRange(turn, 2);
    }

    private void assertIsTech(int turn, Difficulty diff, int result) {
        AlienEconomicSheet sheet = getEconomicSheet(diff);
        makeRoll(sheet, turn, result);
        assertEquals(diff.getCPPerEcon(), sheet.getTechCP());
    }

    private List<Integer> getDefRange(int turn) {
        return getRange(turn, 3);
    }

    private void assertIsDef(int turn, Difficulty diff, int result) {
        AlienEconomicSheet sheet = getEconomicSheet(diff);
        makeRoll(sheet, turn, result);
        assertEquals(2 * diff.getCPPerEcon(), sheet.getDefCP());
    }

    protected AlienEconomicSheet getEconomicSheet() {
        return new AlienEconomicSheet(BaseGameDifficulty.EASY);
    }
    
    protected AlienEconomicSheet getEconomicSheet(Difficulty diff) {
        return new AlienEconomicSheet(diff);
    }

    private List<Integer> getRange(int turn, int index) {
        int[] range = getResult(turn, index);
        if (range.length != 0) {
            int lower = range[0];
            int higher = range.length == 1 ? lower + 1 : range[1] + 1;
            List<Integer> result = new ArrayList<>(higher - lower + 1);
            for (int i = lower; i < higher; i++)
                result.add(i);
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    protected int[] getResult(int turn, int index) {
        return resultTable[turn][index];
    }

}