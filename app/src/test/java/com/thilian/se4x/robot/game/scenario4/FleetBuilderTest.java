package com.thilian.se4x.robot.game.scenario4;

import static com.thilian.se4x.robot.game.enums.FleetType.REGULAR_FLEET;
import static com.thilian.se4x.robot.game.enums.ShipType.CARRIER;
import static com.thilian.se4x.robot.game.enums.ShipType.FIGHTER;
import static com.thilian.se4x.robot.game.enums.Technology.FIGHTERS;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.MockRoller;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

public class FleetBuilderTest {

	//COPY_PASTE_START
	private Game game;
	private AlienPlayer ap;
	private FleetBuilder builder;
	private MockRoller roller;

	@Before
	public void setUp() {
		roller = new MockRoller();
		game = new Game();
		game.roller = roller;
		game.technologyPrices = new Scenario4TechnologyPrices();
		game.resetSeenLevels();
		builder = new FleetBuilder(game);
		ap = new AlienPlayer(null, game, null);
		ap.setLevel(Technology.GROUND_COMBAT, 1);
	}

	@After
	public void assertAllRollsUsed() {
		assertEquals(0, roller.rolls.size());
	}

	//COPY_PASTE_END
	
	@Test
	public void raiderFleetBuysOnlyRaiders(){
		Fleet fleet = new Fleet(ap , FleetType.RAIDER_FLEET, 36);
		assertBuiltRaiderGroups(fleet, new Group(ShipType.RAIDER, 3));
	}
	
	@Test
	public void buildRaiderFleet(){
		ap.setLevel(Technology.CLOAKING, 2);
		ap.setJustPurchasedCloaking(true);
		game.setSeenLevel(Technology.SCANNER, 1);
		Fleet fleet = new Fleet(ap , FleetType.REGULAR_FLEET, 36);
		assertBuiltRaiderGroups(fleet, new Group(ShipType.RAIDER, 3));
	}

	@Test
	public void buildFullyLoadedTransport(){
		ap.setLevel(Technology.GROUND_COMBAT, 1);
		Fleet fleet = new Fleet(ap , FleetType.REGULAR_FLEET, 0);
		assertBuiltFreeGroups(fleet, new Group(ShipType.TRANSPORT, 1), new Group(ShipType.INFANTRY, 6));
	}

	@Test
	public void buildGC2FullyLoadedTransport(){
		ap.setLevel(Technology.GROUND_COMBAT, 2);
		Fleet fleet = new Fleet(ap , FleetType.REGULAR_FLEET, 0);
		assertBuiltFreeGroups(fleet, 
				new Group(ShipType.TRANSPORT, 1), 
				new Group(ShipType.MARINE, 5), 
				new Group(ShipType.HEAVY_INFANTRY, 1));
	}

	@Test
	public void buildGC3FullyLoadedTransport(){
		ap.setLevel(Technology.GROUND_COMBAT, 3);
		Fleet fleet = new Fleet(ap , FleetType.REGULAR_FLEET, 0);
		assertBuiltFreeGroups(fleet, 
				new Group(ShipType.TRANSPORT, 1), 
				new Group(ShipType.MARINE, 4), 
				new Group(ShipType.HEAVY_INFANTRY, 1),
				new Group(ShipType.GRAV_ARMOR, 1));
	}

	@Test
	public void boarding1Builds1BDs(){
		Fleet fleet = new Fleet(ap , FleetType.REGULAR_FLEET, 12);
		ap.setLevel(Technology.BOARDING, 1);
		assertBuiltGroups(fleet, new Group(ShipType.BOARDING_SHIP, 1));
	}

	@Test
	public void boarding1Builds2BDs(){
		Fleet fleet = new Fleet(ap , FleetType.REGULAR_FLEET, 24);
		ap.setLevel(Technology.BOARDING, 1);
		assertBuiltGroups(fleet, new Group(ShipType.BOARDING_SHIP, 2));
	}

	@Test
	public void boarding1BuildsOnly2BDs(){
		Fleet fleet = new Fleet(ap , FleetType.REGULAR_FLEET, 36);
		ap.setLevel(Technology.BOARDING, 1);
		assertBuiltGroups(fleet, new Group(ShipType.BOARDING_SHIP, 2));
	}

	@Test
	public void boarding2BuildsOnly2BDs(){
		Fleet fleet = new Fleet(ap , FleetType.REGULAR_FLEET, 36);
		ap.setLevel(Technology.BOARDING, 2);
		assertBuiltGroups(fleet, new Group(ShipType.BOARDING_SHIP, 2));
	}

	@Test
	public void dontBuildsBDsIfNotEnoughCP(){
		Fleet fleet = new Fleet(ap , FleetType.REGULAR_FLEET, 11);
		ap.setLevel(Technology.BOARDING, 2);
		assertBuiltGroups(fleet);
	}

	@Test
	public void buy1SCIfSeenMines(){
		Fleet fleet = new Fleet(ap, FleetType.REGULAR_FLEET, 6);
		game.addSeenThing(Seeable.MINES);
		assertBuiltGroups(fleet, new Group(ShipType.SCOUT, 1));
	}
	
	@Test
	public void buy2SCIfSeenMines(){
		Fleet fleet = new Fleet(ap, FleetType.REGULAR_FLEET, 12);
		game.addSeenThing(Seeable.MINES);
		assertBuiltGroups(fleet, new Group(ShipType.SCOUT, 2));
	}
	
	@Test
	public void buildCarrierFleet() {
		ap.setLevel(FIGHTERS, 1);
		Fleet fleet = new Fleet(ap, REGULAR_FLEET, 27);
		assertBuiltGroups(fleet, new Group(CARRIER, 1), new Group(FIGHTER, 3));

		fleet = new Fleet(ap, REGULAR_FLEET, 54);
		assertBuiltGroups(fleet, new Group(CARRIER, 2), new Group(FIGHTER, 6));
	}
	
	private void assertBuiltGroups(Fleet fleet, Group... expectedGroups) {
		List<Group> fleetGroups = new ArrayList<>(); 
		fleetGroups.add(new Group(ShipType.TRANSPORT, 1));		
		fleetGroups.add(new Group(ShipType.INFANTRY, 6));
		
		builder.buildFleet(fleet);
		int expectedCost = 0;
		for (Group g : expectedGroups) {
			expectedCost += g.getShipType().getCost() * g.getSize();
		}
		fleetGroups.addAll(Arrays.asList(expectedGroups));
		assertEquals(fleetGroups, fleet.getGroups());
		assertEquals(expectedCost, fleet.getBuildCost());
		assertEquals(FleetType.REGULAR_FLEET, fleet.getFleetType());
	}

	private void assertBuiltRaiderGroups(Fleet fleet, Group... expectedGroups) {
		builder.buildFleet(fleet);
		int expectedCost = 0;
		for (Group g : expectedGroups) {
			expectedCost += g.getShipType().getCost() * g.getSize();
		}
		assertEquals(Arrays.asList(expectedGroups), fleet.getGroups());
		assertEquals(expectedCost, fleet.getBuildCost());
		assertEquals(FleetType.RAIDER_FLEET, fleet.getFleetType());
	}
	
	private void assertBuiltFreeGroups(Fleet fleet, Group... expectedGroups) {
		builder.buildFleet(fleet);
		int expectedCost = 0;
		assertEquals(Arrays.asList(expectedGroups), fleet.getGroups());
		assertEquals(expectedCost, fleet.getBuildCost());
		assertEquals(FleetType.REGULAR_FLEET, fleet.getFleetType());
	}
	
}
