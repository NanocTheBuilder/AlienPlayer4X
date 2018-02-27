package com.thilian.se4x.robot.game;

import org.junit.Before;

import com.thilian.se4x.robot.game.basegame.BaseGameScenario;
import com.thilian.se4x.robot.game.enums.Difficulty;

public class Fixture {
    protected Game game;
    protected AlienPlayer ap;
    protected DefenseBuilder defBuilder;
    protected MockRoller roller;
    protected AlienEconomicSheet sheet;
    protected FleetBuilder fleetBuilder;
    protected TechnologyBuyer techBuyer;

    @Before
    public void setUpFixture() {
        game = new Game();
        setupRoller(game);
        game.createGame(Difficulty.NORMAL, createScenario(game));
        defBuilder = game.scenario.defenseBuilder;
        fleetBuilder = game.scenario.fleetBuilder;
        techBuyer = game.scenario.techBuyer;
        ap = game.aliens.get(0);
        sheet = ap.getEconomicSheet();
    }

    protected Scenario createScenario(Game game) {
        return new BaseGameScenario(game);
    }
    
    private void setupRoller(Game game) {
        roller = new MockRoller();
        game.roller = roller;
    }

}
