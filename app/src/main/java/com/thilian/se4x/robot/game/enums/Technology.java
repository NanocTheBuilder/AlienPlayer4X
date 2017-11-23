package com.thilian.se4x.robot.game.enums;

public enum Technology {
	MOVE(1, 0, 0, 20, 25, 25, 25, 20, 20), 
	SHIP_SIZE(1, 0, 0, 10, 15, 20, 20, 20),
	MINES(0, 0, 20),
	
	ATTACK(0, 0, 20, 30, 25),
	DEFENSE( 0, 0, 20, 30, 25),
	TACTICS(0, 0, 15, 15, 15),
	CLOAKING(0, 0, 30, 30), 
	SCANNER(0, 0, 20, 20),
	FIGHTERS(0, 0, 25, 25, 25), 
	POINT_DEFENSE(0, 0, 20, 20, 20), 
	MINE_SWEEP(0, 0, 10, 15),
	;
	
	private int startingLevel;
	private int[] levelCosts;

	private Technology( int startingLevel, int... levelCosts){
		this.startingLevel = startingLevel;
		this.levelCosts = levelCosts;
	}
	
	public int getStartingLevel() {
		return startingLevel;
	}
	
	public int getCost(int level) {
		return levelCosts[level];
	}
	
	public int getMaxLevel() {
		return levelCosts.length - 1;
	}
}
