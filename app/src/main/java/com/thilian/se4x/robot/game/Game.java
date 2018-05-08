package com.thilian.se4x.robot.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.PlayerColor;
import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.Technology;

public class Game {
    public DiceRoller roller;
    public Scenario scenario;
    public List<AlienPlayer> aliens;
    private Map<Technology, Integer> seenLevels;
    private Set<Seeable> seenThings;
    public int currentTurn;
    private Difficulty difficulty;

    public static Game newGame(Scenario scenario, Difficulty difficulty) {
        Game game = new Game();
        game.difficulty = difficulty;
        game.scenario = scenario;
        scenario.init(game);
        game.aliens = new ArrayList<>();
        for (int i = 0; i < difficulty.getNumberOfAlienPlayers(); i++) {
            game.aliens.add(scenario.newPlayer(game, difficulty, PlayerColor.values()[i]));
        }

        game.resetSeenLevels();

        game.currentTurn = 1;
        return game;
    }

    public void resetSeenLevels() {
        seenThings = new HashSet<>();
        seenLevels = new HashMap<>();
        for (Technology technology : scenario.getAvailableTechs()) {
            int startingLevel = scenario.getStartingLevel(technology);
            seenLevels.put(technology, startingLevel);
        }
    }

    public List<EconPhaseResult> doEconomicPhase() {
        List<EconPhaseResult> results = new ArrayList<>();
        for (AlienPlayer ap : aliens) {
            if(!ap.isEliminated()){
                results.add(ap.makeEconRoll(currentTurn));
            }
        }
        currentTurn++;
        return results;
    }

    public Integer getSeenLevel(Technology technology) {
        return seenLevels.get(technology);
    }

    public void setSeenLevel(Technology technology, Integer level) {
        seenLevels.put(technology, level);
    }

    public void addSeenThing(Seeable seeable) {
        seenThings.add(seeable);
    }

    public void removeSeenThing(Seeable seeable){
        seenThings.remove(seeable);
    }

    public boolean isSeenThing(Seeable seeable) {
        return seenThings.contains(seeable);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}