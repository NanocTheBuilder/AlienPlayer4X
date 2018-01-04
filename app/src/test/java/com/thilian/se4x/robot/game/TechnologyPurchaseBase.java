package com.thilian.se4x.robot.game;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;

import com.thilian.se4x.robot.game.basegame.BaseGameTechnologyBuyer;
import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.Technology;

public class TechnologyPurchaseBase {

	protected AlienEconomicSheet sheet;
	protected Game game;
	protected MockRoller roller;
	protected AlienPlayer ap;
	protected Fleet fleet;
	protected BaseGameTechnologyBuyer techBuyer;

	public TechnologyPurchaseBase() {
		super();
	}

	@Before
	public void setUp() {
		sheet = new AlienEconomicSheet(Difficulty.NORMAL);
		roller = new MockRoller();
		game = new Game();
		techBuyer = new BaseGameTechnologyBuyer(game);
		game.techBuyer = techBuyer;
		game.roller = roller;
		game.resetSeenLevels();
		ap = new AlienPlayer(sheet, game, null);
		for(Technology t : game.technologyPrices.getAvailableTechs()){
			assertLevel(t, game.technologyPrices.getStartingLevel(t));
		}
		buildFleet();
	}

	protected void buildFleet() {
		fleet = new Fleet(ap, FleetType.REGULAR_FLEET, -1);
	}

	@After
	public void assertAllRollsUsed() {
		assertEquals(0, roller.rolls.size());
	}

	protected void assertLevel(Technology technology, int expectedLevel) {
		assertEquals(expectedLevel, ap.getLevel(technology));
	}

}