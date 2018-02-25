package com.thilian.se4x.robot.game.scenario4;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.thilian.se4x.robot.game.AlienEconomicSheet;
import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.MockRoller;
import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

public class HomeDefenseBuildTest {
	private AlienPlayer ap;
	private DefenseBuilder builder;
	private MockRoller roller;
	private AlienEconomicSheet sheet;

	@Before
	public void setUp() {
		roller = new MockRoller();
		Game game = new Game();
		game.roller = roller;
		game.technologyPrices = new Scenario4TechnologyPrices();
		builder = new DefenseBuilder(game);
		sheet = new AlienEconomicSheet(Difficulty.NORMAL);
		ap = new AlienPlayer(sheet, game, null);
	}


	@Test
	public void ifNoGroundCombatTechSpendBalanced() {
		assertBuiltGroups(12, 5, new Group(ShipType.BASE, 1));
		assertBuiltGroups(17, 5, new Group(ShipType.BASE, 1), new Group(ShipType.MINE, 1));
		assertBuiltGroups(24, 5, new Group(ShipType.BASE, 1), new Group(ShipType.MINE, 2));
		assertBuiltGroups(34, 5, new Group(ShipType.BASE, 2), new Group(ShipType.MINE, 2));

		assertBuiltGroups(5, 1, new Group(ShipType.MINE, 1));
	}
	
	@Test
	public void buyHeavyInfantry(){
		ap.setLevel(Technology.GROUND_COMBAT, 2);
		roller.mockRoll(1);
		assertBuiltGroups(27, 5, new Group(ShipType.HEAVY_INFANTRY, 1), new Group(ShipType.BASE, 1), new Group(ShipType.MINE, 2));
		roller.mockRoll(2);
		assertBuiltGroups(30, 5, new Group(ShipType.HEAVY_INFANTRY, 2), new Group(ShipType.BASE, 1), new Group(ShipType.MINE, 2));
	}
	
	
	@Test
	public void buy2GravIfAble(){
		ap.setLevel(Technology.GROUND_COMBAT, 3);
		roller.mockRoll(2);
		assertBuiltGroups(38, 5, new Group(ShipType.GRAV_ARMOR, 2), new Group(ShipType.HEAVY_INFANTRY, 2), new Group(ShipType.BASE, 1), new Group(ShipType.MINE, 2));
}
	
	private void assertBuiltGroups(int defCP, int roll, Group... expectedGroups) {
		sheet.setDefCP(defCP);
		roller.mockRoll(roll);
		Fleet fleet = builder.buildHomeDefense(ap);
		int expectedCost = 0;
		for (Group g : expectedGroups) {
			expectedCost += g.getShipType().getCost() * g.getSize();
		}
		assertEquals(FleetType.DEFENSE_FLEET, fleet.getFleetType());
		assertEquals(Arrays.asList(expectedGroups), fleet.getGroups());
		assertEquals(expectedCost, fleet.getBuildCost());
	}

}
