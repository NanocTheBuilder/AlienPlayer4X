/*
 * Copyright (C) 2018 Balázs Péter
 *
 * This file is part of Alien Player 4X.
 *
 * Alien Player 4X is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alien Player 4X is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Alien Player 4X.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.thilian.se4x.robot.game.scenarios.scenario4;

import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.thilian.se4x.robot.game.enums.FleetType.RAIDER_FLEET;
import static com.thilian.se4x.robot.game.enums.FleetType.REGULAR_FLEET;
import static com.thilian.se4x.robot.game.enums.ShipType.BATTLECRUISER;
import static com.thilian.se4x.robot.game.enums.ShipType.BATTLESHIP;
import static com.thilian.se4x.robot.game.enums.ShipType.BOARDING_SHIP;
import static com.thilian.se4x.robot.game.enums.ShipType.CARRIER;
import static com.thilian.se4x.robot.game.enums.ShipType.CRUISER;
import static com.thilian.se4x.robot.game.enums.ShipType.DESTROYER;
import static com.thilian.se4x.robot.game.enums.ShipType.DREADNAUGHT;
import static com.thilian.se4x.robot.game.enums.ShipType.FIGHTER;
import static com.thilian.se4x.robot.game.enums.ShipType.GRAV_ARMOR;
import static com.thilian.se4x.robot.game.enums.ShipType.HEAVY_INFANTRY;
import static com.thilian.se4x.robot.game.enums.ShipType.INFANTRY;
import static com.thilian.se4x.robot.game.enums.ShipType.MARINE;
import static com.thilian.se4x.robot.game.enums.ShipType.RAIDER;
import static com.thilian.se4x.robot.game.enums.ShipType.SCOUT;
import static com.thilian.se4x.robot.game.enums.ShipType.TITAN;
import static com.thilian.se4x.robot.game.enums.ShipType.TRANSPORT;
import static com.thilian.se4x.robot.game.enums.Technology.ATTACK;
import static com.thilian.se4x.robot.game.enums.Technology.BOARDING;
import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static com.thilian.se4x.robot.game.enums.Technology.DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.FIGHTERS;
import static com.thilian.se4x.robot.game.enums.Technology.GROUND_COMBAT;
import static com.thilian.se4x.robot.game.enums.Technology.SCANNER;
import static com.thilian.se4x.robot.game.enums.Technology.SHIP_SIZE;
import static org.junit.Assert.assertEquals;

public class FleetBuilderTest extends Scenario4Fixture{

    @Test
    public void raiderFleetBuysOnlyRaiders() {
        Fleet fleet = new Fleet(ap, RAIDER_FLEET, 36);
        assertBuiltRaiderGroups(fleet, new Group(RAIDER, 3));
    }

    @Test
    public void buildRaiderFleet() {
        ap.setLevel(CLOAKING, 2);
        ap.setJustPurchasedCloaking(true);
        game.setSeenLevel(SCANNER, 1);
        Fleet fleet = new Fleet(ap, REGULAR_FLEET, 36);
        assertBuiltRaiderGroups(fleet, new Group(RAIDER, 3));
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
    public void buildFullyLoadedTransport() {
        ap.setLevel(GROUND_COMBAT, 1);
        Fleet fleet = new Fleet(ap, REGULAR_FLEET, 0);
        assertBuiltFreeGroups(fleet, new Group(TRANSPORT, 1), new Group(INFANTRY, 6));
    }

    @Test
    public void buildGC2FullyLoadedTransport() {
        ap.setLevel(GROUND_COMBAT, 2);
        Fleet fleet = new Fleet(ap, REGULAR_FLEET, 0);
        assertBuiltFreeGroups(fleet, new Group(TRANSPORT, 1), new Group(MARINE, 5), new Group(HEAVY_INFANTRY, 1));
    }

    @Test
    public void buildGC3FullyLoadedTransport() {
        ap.setLevel(GROUND_COMBAT, 3);
        Fleet fleet = new Fleet(ap, REGULAR_FLEET, 0);
        assertBuiltFreeGroups(fleet, new Group(TRANSPORT, 1), new Group(MARINE, 4), new Group(HEAVY_INFANTRY, 1),
                new Group(GRAV_ARMOR, 1));
    }

    @Test
    public void boarding1Builds1BDs() {
        ap.setLevel(BOARDING, 1);
        assertBuiltFleet(12, new Group(BOARDING_SHIP, 1));
    }

    @Test
    public void boarding1Builds2BDs() {
        ap.setLevel(BOARDING, 1);
        assertBuiltFleet(24, new Group(BOARDING_SHIP, 2));
    }

    @Test
    public void boarding1BuildsOnly2BDs() {
        ap.setLevel(BOARDING, 1);
        ap.setLevel(SHIP_SIZE, 2); // spend 9 CP on optional DD
        assertBuiltFleet(36, new Group(BOARDING_SHIP, 2), new Group(DESTROYER, 1));
    }

    @Test
    public void boarding2BuildsOnly2BDs() {
        ap.setLevel(BOARDING, 2);
        ap.setLevel(SHIP_SIZE, 3); // spend 12 CP on optional CRUISER
        assertBuiltFleet(36, new Group(BOARDING_SHIP, 2), new Group(CRUISER, 1));
    }

    @Test
    public void dontBuildsBDsIfNotEnoughCP() {
        ap.setLevel(BOARDING, 2);
        assertBuiltFleet(5);
    }

    @Test
    public void buy1SCIfSeenMines() {
        game.addSeenThing(Seeable.MINES);
        assertBuiltFleet(6, new Group(SCOUT, 1));
    }

    @Test
    public void buy2SCIfSeenMines() {
        game.addSeenThing(Seeable.MINES);
        assertBuiltFleet(12, new Group(SCOUT, 2));
    }

    @Test
    public void buildCarrierFleet() {
        ap.setLevel(FIGHTERS, 1);
        assertBuiltFleet(27, new Group(CARRIER, 1), new Group(FIGHTER, 3));
        assertBuiltFleet(54, new Group(CARRIER, 2), new Group(FIGHTER, 6));
    }

    @Test
    public void buyFlagshipFirst() {
        assertBuiltFlagship(1, 6, SCOUT);
        assertBuiltFlagship(2, 9, DESTROYER);
        assertBuiltFlagship(3, 12, CRUISER);
        assertBuiltFlagship(4, 15, BATTLECRUISER);
        assertBuiltFlagship(5, 20, BATTLESHIP);
        assertBuiltFlagship(6, 24, DREADNAUGHT);
        assertBuiltFlagship(7, 32, TITAN);
    }

    // COPY PASTE START
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

        ap.setLevel(SHIP_SIZE, 7);
        assertBuiltFleet(7, 56, new Group(TITAN, 1), new Group(DREADNAUGHT, 1));
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

        ap.setLevel(SHIP_SIZE, 2);
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
        assertBuiltFleet(5, 27, new Group(CRUISER, 1), new Group(DESTROYER, 1), new Group(SCOUT, 1));
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

    // COPY PASTE END

    private void assertBuiltFlagship(int shipSize, int fleetCP, ShipType shipType) {
        ap.setLevel(SHIP_SIZE, shipSize);
        assertBuiltFleet(fleetCP, new Group(shipType, 1));
    }

    private void assertBuiltFleet(int fleetTypeRoll, int fleetCP, Group... expectedGroups) {
        roller.mockRoll(fleetTypeRoll);
        assertBuiltFleet(fleetCP, expectedGroups);
    }

    private void assertBuiltFleet(int fleetCP, Group... expectedGroups) {
        Fleet fleet = new Fleet(ap, REGULAR_FLEET, fleetCP);
        assertBuiltGroups(fleet, expectedGroups);
    }

    private void assertBuiltGroups(Fleet fleet, Group... expectedGroups) {
        assertBuiltGroups(fleet, new FleetBuildOption[0], expectedGroups);
    }

    private void assertBuiltGroups(Fleet fleet, FleetBuildOption[] options, Group... expectedGroups) {
        List<Group> fleetGroups = new ArrayList<>();
        fleetGroups.add(new Group(TRANSPORT, 1));
        fleetGroups.add(new Group(INFANTRY, 6));

        fleetBuilder.buildFleet(fleet, options);
        int expectedCost = 0;
        for (Group g : expectedGroups) {
            expectedCost += g.getShipType().getCost() * g.getSize();
        }
        fleetGroups.addAll(Arrays.asList(expectedGroups));
        assertEquals(fleetGroups, fleet.getGroups());
        assertEquals(expectedCost, fleet.getBuildCost());
        assertEquals(REGULAR_FLEET, fleet.getFleetType());
    }

    private void assertBuiltRaiderGroups(Fleet fleet, Group... expectedGroups) {
        fleetBuilder.buildFleet(fleet);
        int expectedCost = 0;
        for (Group g : expectedGroups) {
            expectedCost += g.getShipType().getCost() * g.getSize();
        }
        assertEquals(Arrays.asList(expectedGroups), fleet.getGroups());
        assertEquals(expectedCost, fleet.getBuildCost());
        assertEquals(RAIDER_FLEET, fleet.getFleetType());
    }

    private void assertBuiltFreeGroups(Fleet fleet, Group... expectedGroups) {
        fleetBuilder.buildFleet(fleet);
        int expectedCost = 0;
        assertEquals(Arrays.asList(expectedGroups), fleet.getGroups());
        assertEquals(expectedCost, fleet.getBuildCost());
        assertEquals(REGULAR_FLEET, fleet.getFleetType());
    }

}
