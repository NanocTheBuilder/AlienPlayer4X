package com.thilian.se4x.robot.game;

import com.thilian.se4x.robot.game.DiceRoller;

import java.util.LinkedList;
import java.util.Queue;

public class MockRoller implements DiceRoller {

	Queue<Integer> rolls = new LinkedList<>();

	public void mockRoll(int... roll) {
		for(int i : roll)
			rolls.add(i);
	}
	
	@Override
	public int roll() {
		return rolls.remove();
	}
	
	@Override
	public int roll(int limit) {
		return roll();
	}

}