package com.thilian.se4x.robot.game.scenarios.basegame;

import static com.thilian.se4x.robot.game.enums.FleetType.RAIDER_FLEET;
import static com.thilian.se4x.robot.game.enums.FleetType.REGULAR_FLEET;
import static com.thilian.se4x.robot.game.enums.ShipType.BATTLECRUISER;
import static com.thilian.se4x.robot.game.enums.ShipType.BATTLESHIP;
import static com.thilian.se4x.robot.game.enums.ShipType.CARRIER;
import static com.thilian.se4x.robot.game.enums.ShipType.CRUISER;
import static com.thilian.se4x.robot.game.enums.ShipType.DESTROYER;
import static com.thilian.se4x.robot.game.enums.ShipType.DREADNAUGHT;
import static com.thilian.se4x.robot.game.enums.ShipType.FIGHTER;
import static com.thilian.se4x.robot.game.enums.ShipType.RAIDER;
import static com.thilian.se4x.robot.game.enums.ShipType.SCOUT;
import static com.thilian.se4x.robot.game.enums.Technology.ATTACK;
import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static com.thilian.se4x.robot.game.enums.Technology.DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.FIGHTERS;
import static com.thilian.se4x.robot.game.enums.Technology.POINT_DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.SCANNER;
import static com.thilian.se4x.robot.game.enums.Technology.SHIP_SIZE;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

public class FleetBuildTest extends BasegameFixture{

    @Test
    public void buildRaiderFleet() {
        Fleet fleet = new Fleet(ap, FleetType.RAIDER_FLEET, 24);
        assertBuiltGroups(fleet, new Group(ShipType.RAIDER, 2));
    }

    @Test
    public void buildScoutFleet() {
        assertBuiltFleet(1, 12, new Group(SCOUT, 2));
        assertBuiltFleet(4, 12, new Group(SCOUT, 2));
        assertBuiltFleet(7, 12, new Group(SCOUT, 2));
    }

    @Test
    public void buildCarrierFleet() {
        ap.setLevel(FIGHTERS, 1);
        Fleet fleet = new Fleet(ap, REGULAR_FLEET, 27);
        assertBuiltGroups(fleet, new Group(CARRIER, 1), new Group(FIGHTER, 3));

        fleet = new Fleet(ap, REGULAR_FLEET, 54);
        assertBuiltGroups(fleet, new Group(CARRIER, 2), new Group(FIGHTER, 6));

    }

    @Test
    public void dontBuildCarrierFleetUnder27() {
        ap.setLevel(FIGHTERS, 1);
        assertBuiltFleet(1, 26, new Group(SCOUT, 4));
    }

    @Test
    public void dontBuildCarrierFleetIfSeenPDAndFailedRoll() {
        ap.setLevel(FIGHTERS, 1);
        game.setSeenLevel(POINT_DEFENSE, 1);
        roller.mockRoll(5);
        assertBuiltFleet(1, 27, new Group(SCOUT, 4));
    }

    @Test
    public void buildCarrierFleetIfSeenPDAndPassedRoll() {
        ap.setLevel(FIGHTERS, 1);
        game.setSeenLevel(POINT_DEFENSE, 1);
        roller.mockRoll(4);
        Fleet fleet = new Fleet(ap, REGULAR_FLEET, 27);
        assertBuiltGroups(fleet, new Group(CARRIER, 1), new Group(FIGHTER, 3));
    }

    @Test
    public void buildRaiderFleetIfJustPurchasedCloak() {
        ap.setLevel(CLOAKING, 1);
        ap.setJustPurchasedCloaking(true);
        Fleet fleet = new Fleet(ap, REGULAR_FLEET, 12);
        assertBuiltGroups(fleet, new Group(RAIDER, 1));
        assertEquals(RAIDER_FLEET, fleet.getFleetType());

        game.setSeenLevel(SCANNER, 1);
        assertBuiltFleet(1, 12, new Group(SCOUT, 2));
    }

    @Test
    public void dontBuildRaiderFleetUnder12() {
        ap.setLevel(CLOAKING, 1);
        ap.setJustPurchasedCloaking(true);
        ap.setLevel(SHIP_SIZE, 2);
        Fleet fleet = new Fleet(ap, REGULAR_FLEET, 11);
        assertBuiltGroups(fleet, new Group(DESTROYER, 1));
    }


    @Test
    public void dontBuildRaiderFleetForHomeDefense() {
        ap.setLevel(CLOAKING, 1);
        ap.setJustPurchasedCloaking(true);
        ap.setLevel(SHIP_SIZE, 2);
        Fleet fleet = new Fleet(ap, REGULAR_FLEET, 12);
        assertBuiltGroups(fleet, 
                new FleetBuildOption[]{FleetBuildOption.HOME_DEFENSE}, 
                new Group(DESTROYER, 1));

    }

    @Test
    public void checkFighterFleetBeforeRaiderFleet() {
        ap.setLevel(FIGHTERS, 1);
        ap.setLevel(CLOAKING, 1);
        ap.setJustPurchasedCloaking(true);
        ap.setLevel(SHIP_SIZE, 2);
        Fleet fleet = new Fleet(ap, REGULAR_FLEET, 39);
        assertBuiltGroups(fleet, new Group(CARRIER, 1), new Group(FIGHTER, 3), new Group(DESTROYER, 1));
    }

    @Test
    public void buyFlagshipFirst() {
        assertBuiltFlagship(1, 6, SCOUT);
        assertBuiltFlagship(2, 9, DESTROYER);
        assertBuiltFlagship(3, 12, CRUISER);
        assertBuiltFlagship(4, 15, BATTLECRUISER);
        assertBuiltFlagship(5, 20, BATTLESHIP);
        assertBuiltFlagship(6, 24, DREADNAUGHT);
    }

    @Test
    public void buyOneDD() {
        ap.setLevel(SHIP_SIZE, 2);
        assertBuiltFleet(1, 27, new Group(DESTROYER, 1), new Group(SCOUT, 3));

        ap.setLevel(SHIP_SIZE, 3);
        assertBuiltFleet(1, 30, new Group(CRUISER, 1), new Group(DESTROYER, 2));

        game.setSeenLevel(CLOAKING, 1);
        assertBuiltFleet(1, 30, new Group(CRUISER, 1), new Group(SCOUT, 3));

        ap.setLevel(SCANNER, 1);
        assertBuiltFleet(1, 30, new Group(CRUISER, 1), new Group(DESTROYER, 2));
    }

    @Test
    public void buildLargestFleet() {
        game.setSeenLevel(CLOAKING, 1); // NO Possible DD

        ap.setLevel(SHIP_SIZE, 2);
        assertBuiltFleet(1, 18, new Group(DESTROYER, 2));

        assertBuiltFleet(1, 27, new Group(DESTROYER, 1), new Group(SCOUT, 3));

        ap.setLevel(SHIP_SIZE, 3);
        assertBuiltFleet(1, 30, new Group(CRUISER, 1), new Group(SCOUT, 3));
        assertBuiltFleet(1, 33, new Group(CRUISER, 1), new Group(DESTROYER, 1), new Group(SCOUT, 2));
        assertBuiltFleet(1, 87, new Group(CRUISER, 1), new Group(DESTROYER, 1), new Group(SCOUT, 11));
    }

    @Test
    public void buildLargestShips() {
        game.setSeenLevel(CLOAKING, 1); // NO Possible DD

        ap.setLevel(SHIP_SIZE, 2);
        assertBuiltFleet(7, 21, new Group(DESTROYER, 2));
        assertBuiltFleet(7, 27, new Group(DESTROYER, 3));

        ap.setLevel(SHIP_SIZE, 3);
        assertBuiltFleet(7, 30, new Group(CRUISER, 2), new Group(SCOUT, 1));

        ap.setLevel(SHIP_SIZE, 6);
        assertBuiltFleet(7, 30, new Group(DREADNAUGHT, 1), new Group(SCOUT, 1));
    }

    @Test
    public void buildBalancedFleet() {
        game.setSeenLevel(CLOAKING, 1); // NO Possible DD

        ap.setLevel(SHIP_SIZE, 5);
        assertBuiltFleet(4, 44, new Group(BATTLESHIP, 1), new Group(SCOUT, 4));

        ap.setLevel(ATTACK, 2);
        assertBuiltFleet(4, 44, new Group(BATTLESHIP, 1), new Group(CRUISER, 2));

        ap.setLevel(ATTACK, 0);
        ap.setLevel(DEFENSE, 2);
        assertBuiltFleet(4, 44, new Group(BATTLESHIP, 1), new Group(CRUISER, 2));
        assertBuiltFleet(4, 47, new Group(BATTLESHIP, 1), new Group(BATTLECRUISER, 1), new Group(CRUISER, 1));
        assertBuiltFleet(4, 50, new Group(BATTLESHIP, 1), new Group(BATTLECRUISER, 2));
        assertBuiltFleet(4, 52, new Group(BATTLESHIP, 2), new Group(CRUISER, 1));
        assertBuiltFleet(4, 56, new Group(BATTLESHIP, 1), new Group(CRUISER, 3));
        //+20 for BATTLESHIP
        assertBuiltFleet(4, 26, new Group(BATTLESHIP, 1), new Group(SCOUT, 1));
        assertBuiltFleet(4, 29, new Group(BATTLESHIP, 1), new Group(DESTROYER, 1));
        assertBuiltFleet(4, 32, new Group(BATTLESHIP, 1), new Group(CRUISER, 1));
        assertBuiltFleet(4, 35, new Group(BATTLESHIP, 1), new Group(BATTLECRUISER, 1));
        assertBuiltFleet(4, 41, new Group(BATTLESHIP, 2));

        ap.setLevel(SHIP_SIZE, 2); //+9 for DD
        assertBuiltFleet(4, 15, new Group(DESTROYER, 1), new Group(SCOUT, 1));
        assertBuiltFleet(4, 18, new Group(DESTROYER, 2));
        assertBuiltFleet(4, 21, new Group(DESTROYER, 1), new Group(SCOUT, 2));
        assertBuiltFleet(4, 24, new Group(DESTROYER, 2), new Group(SCOUT, 1));
        assertBuiltFleet(4, 44, new Group(DESTROYER, 2), new Group(SCOUT, 4));

        ap.setLevel(SHIP_SIZE, 3);
        assertBuiltFleet(4, 26, new Group(CRUISER, 2));
    }

    @Test
    public void subtractTwoIfHasPDAndSeenFighters() {
        game.setSeenLevel(CLOAKING, 1); // No Possible DD
        ap.setLevel(Technology.POINT_DEFENSE, 1);
        game.addSeenThing(Seeable.FIGHTERS);

        ap.setLevel(SHIP_SIZE, 3);
        assertBuiltFleet(5, 27, new Group(CRUISER, 1), new Group(SCOUT, 2));
    }

    @Test
    public void subtractTwoIfHasPDAndSeenFightersAndBuy2SC() {
        game.setSeenLevel(CLOAKING, 1); // No Possible DD
        ap.setLevel(Technology.POINT_DEFENSE, 1);
        game.addSeenThing(Seeable.FIGHTERS);

        ap.setLevel(SHIP_SIZE, 5);
        ap.setLevel(ATTACK, 2);
        assertBuiltFleet(6, 44, new Group(BATTLESHIP, 1), new Group(SCOUT, 2), new Group(CRUISER, 1));

        ap.setLevel(SHIP_SIZE, 3);
        assertBuiltFleet(6, 26, new Group(CRUISER, 1), new Group(SCOUT, 2));

        ap.setLevel(SHIP_SIZE, 2);
        assertBuiltFleet(9, 21, new Group(DESTROYER, 1), new Group(SCOUT, 2));
    }

    @Test
    public void subtractTwoIfHasPDAndSeenFightersAndDontBuy2SCIfHasFullCarrier() {
        game.setSeenLevel(CLOAKING, 1); // No Possible DD
        ap.setLevel(FIGHTERS, 1);
        ap.setLevel(Technology.POINT_DEFENSE, 1);
        game.addSeenThing(Seeable.FIGHTERS);

        ap.setLevel(SHIP_SIZE, 3);
        ap.setLevel(ATTACK, 2);
        assertBuiltFleet(6, 27 + 26, new Group(CARRIER, 1), new Group(FIGHTER, 3), new Group(CRUISER, 2));

        ap.setLevel(SHIP_SIZE, 2);
        assertBuiltFleet(9, 27 + 21, new Group(CARRIER, 1), new Group(FIGHTER, 3), new Group(DESTROYER, 2));
    }

    @Test
    public void noCPnoFleet() {
        Fleet fleet = new Fleet(ap, REGULAR_FLEET, 5);
        assertBuiltGroups(fleet);
        assertEquals(0, fleet.getBuildCost());
    }

    private void assertBuiltFlagship(int shipSize, int fleetCP, ShipType shipType) {
        ap.setLevel(SHIP_SIZE, shipSize);
        Fleet fleet = new Fleet(ap, REGULAR_FLEET, fleetCP);
        assertBuiltGroups(fleet, new Group(shipType, 1));
    }

    private void assertBuiltFleet(int fleetTypeRoll, int fleetCP, Group... expectedGroups) {
        roller.mockRoll(fleetTypeRoll);
        Fleet fleet = new Fleet(ap, REGULAR_FLEET, fleetCP);
        assertBuiltGroups(fleet, expectedGroups);
    }

    private void assertBuiltGroups(Fleet fleet, Group... expectedGroups) {
        assertBuiltGroups(fleet, new FleetBuildOption[0], expectedGroups);
    }


    private void assertBuiltGroups(Fleet fleet, FleetBuildOption[] options, Group... expectedGroups) {
        fleetBuilder.buildFleet(fleet, options);
        int expectedCost = 0;
        for (Group g : expectedGroups) {
            expectedCost += g.getShipType().getCost() * g.getSize();
        }
        assertEquals(Arrays.asList(expectedGroups), fleet.getGroups());
        assertEquals(expectedCost, fleet.getBuildCost());
    }
}
