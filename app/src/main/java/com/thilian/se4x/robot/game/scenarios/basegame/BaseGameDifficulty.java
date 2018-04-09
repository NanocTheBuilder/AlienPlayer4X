package com.thilian.se4x.robot.game.scenarios.basegame;

public enum BaseGameDifficulty implements com.thilian.se4x.robot.game.enums.Difficulty{
	EASY(5, 2), NORMAL(5, 3), HARD(10, 2), HARDER(10, 3), REALLY_TOUGH(15, 2), GOOD_LUCK(15, 3);

	private int cpPerEcon;
	private int numberOfAlienPlayers;
	
	private BaseGameDifficulty(int cpPerEcon, int numberOfAlienPlayers) {
		this.cpPerEcon = cpPerEcon;
		this.numberOfAlienPlayers = numberOfAlienPlayers;
	}

	public int getCPPerEcon() {
		return cpPerEcon;
	}

	public int getNumberOfAlienPlayers() {
		return numberOfAlienPlayers;
	}
}
