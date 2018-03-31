package com.thilian.se4x.robot.game;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;

import com.thilian.se4x.robot.game.enums.Difficulty;

public abstract class Fixture {
    protected Game game;
    protected AlienPlayer ap;
    protected DefenseBuilder defBuilder;
    protected MockRoller roller;
    protected AlienEconomicSheet sheet;
    protected FleetBuilder fleetBuilder;
    protected TechnologyBuyer techBuyer;
    protected FleetLauncher fleetLauncher;

    @Before
    public void setUpFixture() {
        game = new Game();
        setupRoller(game);
        game.createGame(Difficulty.NORMAL, getScenario());
        defBuilder = game.scenario.defenseBuilder;
        fleetBuilder = game.scenario.fleetBuilder;
        fleetLauncher = game.scenario.fleetLauncher;
        techBuyer = game.scenario.techBuyer;
        ap = game.aliens.get(0);
        sheet = ap.getEconomicSheet();
    }

    @After
    public void assertAllRollsUsed() {
        assertEquals(0, roller.rolls.size());
    }

    protected abstract Scenario getScenario();
    
    private void setupRoller(Game game) {
        roller = new MockRoller();
        game.roller = roller;
    }

}
