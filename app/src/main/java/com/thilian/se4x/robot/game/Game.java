package com.thilian.se4x.robot.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.PlayerColor;
import com.thilian.se4x.robot.game.enums.Technology;

public class Game {
	public DiceRoller roller;
	public FleetBuilder fleetBuilder = new FleetBuilder(this);
	public TechnologyBuyer techBuyer = new TechnologyBuyer(this);
	public DefenseBuilder defenseBuilder = new DefenseBuilder(this);
	public List<AlienPlayer> aliens;
	public Map<Technology, Integer> seenLevels;
	public int currentTurn;
	
	public void createGame(Difficulty difficulty) {
		aliens = new ArrayList<>();
		for(int i = 0; i < difficulty.getNumberOfAlienPlayers(); i++) {
			aliens.add(new AlienPlayer(new AlienEconomicSheet(difficulty), this, PlayerColor.values()[i]));
		}

		resetSeenLevels();

		currentTurn = 1;
	}

	protected void resetSeenLevels() {
		seenLevels = new HashMap<>();
		for (Technology technology : Technology.values()) {
			int startingLevel = technology.getStartingLevel();
			seenLevels.put(technology, startingLevel);
		}
	}

	public List<Fleet> doEconomicPhase() {
		List<Fleet> newFleets = new ArrayList<>();
		for(AlienPlayer ap : aliens) {
			Fleet fleet = ap.makeEconRoll(currentTurn);
			if(fleet != null)
				newFleets.add(fleet);
		}
		currentTurn++;
		return newFleets;
	}
}