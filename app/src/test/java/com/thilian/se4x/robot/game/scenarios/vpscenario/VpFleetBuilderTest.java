package com.thilian.se4x.robot.game.scenarios.vpscenario;

import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.FleetBuildOptions;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.thilian.se4x.robot.game.enums.FleetBuildOptions.COMBAT_IS_ABOVE_PLANET;
import static com.thilian.se4x.robot.game.enums.FleetType.EXPANSION_FLEET;
import static com.thilian.se4x.robot.game.enums.FleetType.EXTERMINATION_FLEET;
import static com.thilian.se4x.robot.game.enums.FleetType.REGULAR_FLEET;
import static com.thilian.se4x.robot.game.enums.ShipType.INFANTRY;
import static com.thilian.se4x.robot.game.enums.ShipType.SCOUT;
import static com.thilian.se4x.robot.game.enums.ShipType.TRANSPORT;
import static com.thilian.se4x.robot.game.enums.Technology.GROUND_COMBAT;
import static org.junit.Assert.assertEquals;

public class VpFleetBuilderTest extends VpScenarioFixture {
    @Test
    public void expansionFleetAlwaysBuysFullyLoadedTransport(){
        assertEquals(1, ap.getLevel(GROUND_COMBAT));
        Fleet fleet = new Fleet(ap, EXPANSION_FLEET, 0);
        fleetBuilder.buildFleet(fleet);
        assertBuiltFreeGroups(fleet, new Group(TRANSPORT, 1), new Group(INFANTRY, 6));
    }

    @Test
    public void exterminationFleetBuysFullyLoadedTransportIfRolled(){
        assertEquals(1, ap.getLevel(GROUND_COMBAT));
        roller.mockRoll(5);
        roller.mockRoll(7); //Largest ships
        Fleet fleet = new Fleet(ap, EXTERMINATION_FLEET, 40);
        fleetBuilder.buildFleet(fleet);
        assertBuiltGroups(fleet, new Group(TRANSPORT, 1), new Group(INFANTRY, 6), new Group(SCOUT, 3));
    }

    @Test
    public void dontBuyUnder40(){
        assertEquals(1, ap.getLevel(GROUND_COMBAT));
        roller.mockRoll(7); //Largest ships
        Fleet fleet = new Fleet(ap, EXTERMINATION_FLEET, 39);
        fleetBuilder.buildFleet(fleet);
        assertBuiltGroups(fleet, new Group(SCOUT, 6));
    }

    @Test
    public void subtract2IfAbovePlanet(){
        assertEquals(1, ap.getLevel(GROUND_COMBAT));
        roller.mockRoll(7);
        roller.mockRoll(7); //Largest ships
        Fleet fleet = new Fleet(ap, EXTERMINATION_FLEET, 40);
        fleetBuilder.buildFleet(fleet, COMBAT_IS_ABOVE_PLANET);
        assertBuiltGroups(fleet, new Group(TRANSPORT, 1), new Group(INFANTRY, 6), new Group(SCOUT, 3));
    }

    private void assertBuiltGroups(Fleet fleet, Group... expectedGroups) {
        int expectedCost = 0;
        for (Group g : expectedGroups) {
            expectedCost += g.getShipType().getCost() * g.getSize();
        }

        List<Group> fleetGroups = Arrays.asList(expectedGroups);
        assertEquals(fleetGroups, fleet.getGroups());
        assertEquals(expectedCost, fleet.getBuildCost());
    }

    private void assertBuiltFreeGroups(Fleet fleet, Group... expectedGroups) {
        int expectedCost = 0;
        assertEquals(Arrays.asList(expectedGroups), fleet.getGroups());
        assertEquals(expectedCost, fleet.getBuildCost());
    }

}
