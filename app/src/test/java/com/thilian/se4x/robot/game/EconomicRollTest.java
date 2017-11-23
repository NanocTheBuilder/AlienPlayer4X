package com.thilian.se4x.robot.game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.thilian.se4x.robot.game.enums.Difficulty;

public class EconomicRollTest {
	
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
	};

	@Test
	public void testCPResults() {
		for(int turn = 1; turn < 21; turn ++){
			for(Difficulty diff : Difficulty.values()){
				assertFleetResults(turn, diff);
				assertTechResults(turn, diff);
				assertDefResults(turn, diff);
			}
		}
	}
	
	@Test
	public void testEconResult() {
		AlienEconomicSheet sheet = new AlienEconomicSheet(Difficulty.EASY);
		assertEquals(0, sheet.getExtraEcon(4));
		sheet.applyRoll(1, 1);
		sheet.applyRoll(2, 1);
		assertEquals(1, sheet.getExtraEcon(4));
		assertEquals(2, sheet.getExtraEcon(5));
		assertEquals(2, sheet.getExtraEcon(6));
		sheet.applyRoll(7, 1);
		assertEquals(2, sheet.getExtraEcon(10));
	}
	
	@Test
	public void testEconRollsColumn() {
		int[] expected = new int[] {0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5};
		AlienEconomicSheet sheet = new AlienEconomicSheet(null);
		for(int turn = 1; turn < 21; turn++)
			assertEquals(expected[turn], sheet.getEconRolls(turn));
	}
	
	@Test
	public void testFleetLaunch() {
		int[] expected = new int[] {0, -99, 10, 10, 5, 3, 4, 4, 4, 5, 5, 3, 3, 3, 10, 3, 10, 3, 10, 3, 10};
		AlienEconomicSheet sheet = new AlienEconomicSheet(null);
		for(int turn = 1; turn < 21; turn++)
			assertEquals(expected[turn], sheet.getFleetLaunch(turn));
	}
	
	private void assertFleetResults(int turn, Difficulty diff) {
		for(Integer i : getFleetRange(turn)){
			assertIsFleet(turn, diff, i);
		}
	}

	private void assertTechResults(int turn, Difficulty diff) {
		for(Integer i : getTechRange(turn)){
			assertIsTech(turn, diff, i);
		}
	}

	private void assertDefResults(int turn, Difficulty diff) {
		for(Integer i : getDefRange(turn)){
			assertIsDef(turn, diff, i);
		}
	}

	private List<Integer> getFleetRange(int turn) {
		return getRange(turn, 1);
	}

	private void assertIsFleet(int turn, Difficulty diff, int result) {
		AlienEconomicSheet sheet = new AlienEconomicSheet(diff);
		sheet.applyRoll(turn, result);
		assertEquals(String.format("turn %d roll %d is not Fleet" ,turn, result), diff.getCPPerEcon(), sheet.fleetCP);
	}

	private List<Integer> getTechRange(int turn) {
		return getRange(turn, 2);
	}
	
	private void assertIsTech(int turn, Difficulty diff, int result) {
		AlienEconomicSheet sheet = new AlienEconomicSheet(diff);
		sheet.applyRoll(turn, result);
		assertEquals(diff.getCPPerEcon(), sheet.techCP);
	}

	private List<Integer> getDefRange(int turn) {
		return getRange(turn, 3);
	}
	
	private void assertIsDef(int turn, Difficulty diff, int result) {
		AlienEconomicSheet sheet = new AlienEconomicSheet(diff);
		sheet.applyRoll(turn, result);
		assertEquals(2 * diff.getCPPerEcon(), sheet.defCP);
	}
	
	private List<Integer> getRange(int turn, int index) {
		int[] range = resultTable[turn][index];
		if(range.length != 0) {
			int lower = range[0];
			int higher = range.length == 1 ? lower + 1 : range[1] + 1;
			List<Integer> result = new ArrayList<>(higher - lower + 1);
			for(int i = lower; i < higher;  i++)
				result.add(i);
			return result;
		}
		else {
			return new ArrayList<>();
		}
	}

}