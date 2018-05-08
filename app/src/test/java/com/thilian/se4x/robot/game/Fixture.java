package com.thilian.se4x.robot.game;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;

import com.thilian.se4x.robot.game.enums.Technology;

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
        game = createGame();
        setupRoller(game);
        defBuilder = game.scenario.defenseBuilder;
        fleetBuilder = game.scenario.fleetBuilder;
        fleetLauncher = game.scenario.fleetLauncher;
        techBuyer = game.scenario.techBuyer;
        ap = game.aliens.get(0);
        sheet = ap.getEconomicSheet();
    }

    protected abstract Game createGame();

    @After
    public void assertAllRollsUsed() {
        assertRoller();
    }
    
    private void setupRoller(Game game) {
        roller = new MockRoller();
        game.roller = roller;
    }
    
    protected void setLevel(Technology technology, int level){
        ap.setLevel(technology, level);
    }

    protected void assertLevel(Technology technology, int expected) {
        assertEquals(expected, ap.getLevel(technology));
    }

    protected void assertGroups(Fleet fleet, Group... expectedGroups) {
        assertEquals(Arrays.asList(expectedGroups), fleet.getGroups());
    }

    protected void assertRoller(){
        assertEquals(0, roller.rolls.size());
    }

    protected void setCPs(int fleetCP, int techCP, int defCP) {
       sheet.setFleetCP(fleetCP);
       sheet.setTechCP(techCP);
       sheet.setDefCP(defCP);
   }

    protected void assertCPs(int fleetCP, int techCP, int defCP) {
       assertEquals(fleetCP, sheet.getFleetCP());
       assertEquals(techCP, sheet.getTechCP());
       assertEquals(defCP, sheet.getDefCP());
   }

}
