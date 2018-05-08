package com.thilian.se4x.robot.game.scenarios.basegame;

public enum BaseGameDifficulty implements com.thilian.se4x.robot.game.enums.Difficulty{
    EASY("EASY", 5, 2), NORMAL("NORMAL", 5, 3), HARD("HARD", 10, 2),
    HARDER("HARDER", 10, 3), REALLY_TOUGH("REALLY_TOUGH", 15, 2), GOOD_LUCK("GOOD_LUCK", 15, 3);

    private String name;
    private int cpPerEcon;
    private int numberOfAlienPlayers;
	
    private BaseGameDifficulty(String name, int cpPerEcon, int numberOfAlienPlayers) {
        this.name = name;
        this.cpPerEcon = cpPerEcon;
        this.numberOfAlienPlayers = numberOfAlienPlayers;
    }
    @Override
    public int getCPPerEcon() {
        return cpPerEcon;
    }

    @Override
    public int getNumberOfAlienPlayers() {
        return numberOfAlienPlayers;
    }

    @Override
    public String getName() {
        return name;
    }
}
