package com.thilian.se4x.robot.game.scenarios.scenario4;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.thilian.se4x.robot.game.AlienEconomicSheet;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.MockRoller;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameDifficulty;

public class Integration {

    private AlienEconomicSheet sheet;

    @Test
    public void economyRollStartsNewFleet() {
        MockRoller roller = new MockRoller();
        Game game = new Game();
        game.createGame(BaseGameDifficulty.NORMAL, new Scenario4());
        game.roller = roller;
        
        Scenario4Player ap = (Scenario4Player) game.aliens.get(0);
        sheet = ap.getEconomicSheet();

        roller.mockRoll(1);
        assertEquals(null, ap.makeEconRoll(1));
        assertEquals(1, sheet.getExtraEcon(4));
        assertCPs(0, 0, 0);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(5);
        assertEquals(null, ap.makeEconRoll(2));
        assertCPs(0, 5, 0);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(4);
        roller.mockRoll(3);
        roller.mockRoll(2);
        roller.mockRoll(7);
        Fleet fleet = ap.makeEconRoll(3);
        assertEquals(10, fleet.getFleetCP());
        assertEquals(FleetType.REGULAR_FLEET, fleet.getFleetType());
        assertEquals(fleet, ap.getFleets().get(0));
        assertEquals(1, ap.getLevel(Technology.MOVE));
        assertCPs(0, 5, 0);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(4);
        roller.mockRoll(3);
        roller.mockRoll(3);
        roller.mockRoll(6);
        fleet = ap.makeEconRoll(4);
        assertEquals(null, fleet);
        assertCPs(15, 5, 0);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(7);
        roller.mockRoll(10);
        roller.mockRoll(10);
        roller.mockRoll(10);
        fleet = ap.makeEconRoll(5);
        assertEquals(null, fleet);
        assertCPs(15, 10, 20);
        assertEquals(0, roller.rolls.size());

        fleet = ap.getFleets().get(0);
        roller.mockRoll(9); // ShipSize
        ap.buildFleet(fleet);
        assertEquals(2, ap.getLevel(Technology.SHIP_SIZE));
        assertGroups(fleet, new Group(ShipType.TRANSPORT, 1), new Group(ShipType.INFANTRY, 6), new Group(ShipType.DESTROYER, 1));
        assertCPs(16, 0, 20);
        assertEquals(0, roller.rolls.size());

        ap.destroyFleet(fleet);
        assertEquals(0, ap.getFleets().size());

        roller.mockRoll(6);
        roller.mockRoll(6);
        roller.mockRoll(9);
        roller.mockRoll(7);
        roller.mockRoll(5);
        fleet = ap.makeEconRoll(6);
        assertCPs(26, 10, 20);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(6);
        roller.mockRoll(9);
        roller.mockRoll(8);
        roller.mockRoll(3);
        roller.mockRoll(4);
        roller.mockRoll(8);
        fleet = ap.makeEconRoll(7);
        assertEquals(31, fleet.getFleetCP());
        assertEquals(FleetType.REGULAR_FLEET, fleet.getFleetType());
        assertEquals(fleet, ap.getFleets().get(0));
        assertEquals(1, ap.getLevel(Technology.MOVE));
        assertCPs(0, 25, 20);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(1);
        roller.mockRoll(4);
        roller.mockRoll(7);
        roller.mockRoll(9);
        roller.mockRoll(7);
        fleet = ap.makeEconRoll(8);
        assertEquals(null, fleet);
        assertCPs(10, 35, 20);
        assertEquals(0, roller.rolls.size());

        fleet = ap.getFleets().get(0);
        roller.mockRoll(9); // ShipSize
        roller.mockRoll(100, 70); // Cloaking
        ap.buildFleet(fleet);
        assertEquals(2, ap.getLevel(Technology.SHIP_SIZE));
        assertEquals(1, ap.getLevel(Technology.CLOAKING));
        assertGroups(fleet, new Group(ShipType.RAIDER, 2));
        assertEquals(FleetType.RAIDER_FLEET, fleet.getFleetType());
        assertCPs(17, 5, 20);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(2);
        roller.mockRoll(9);
        roller.mockRoll(7);
        roller.mockRoll(7);
        roller.mockRoll(8);
        fleet = ap.makeEconRoll(9);
        assertEquals(null, fleet);
        assertCPs(22, 20, 20);
        assertEquals(0, roller.rolls.size());

        game.setSeenLevel(Technology.CLOAKING, 1);
        roller.mockRoll(1); // Scanners
        roller.mockRoll(9); // ShipSize (Ignored)
        roller.mockRoll(4, 3); // Military academy
        roller.mockRoll(1); // Max number of ships
        roller.mockRoll(10); // Max bases
        ap.buildHomeDefense();
        assertEquals(2, ap.getLevel(Technology.SHIP_SIZE));
        assertEquals(0, ap.getLevel(Technology.SCANNER));
        assertEquals(2, ap.getLevel(Technology.GROUND_COMBAT));
        assertEquals(1, ap.getLevel(Technology.MILITARY_ACADEMY));
        assertGroups(ap.getFleets().get(1), new Group(ShipType.TRANSPORT, 1), new Group(ShipType.MARINE, 5), new Group(ShipType.HEAVY_INFANTRY, 1), new Group(ShipType.DESTROYER, 1), new Group(ShipType.SCOUT, 2));
        assertEquals(FleetType.REGULAR_FLEET, ap.getFleets().get(1).getFleetType());
        assertGroups(ap.getFleets().get(2), new Group(ShipType.BASE, 1), new Group(ShipType.MINE, 1));
        assertEquals(FleetType.DEFENSE_FLEET, ap.getFleets().get(2).getFleetType());
        assertCPs(1, 0, 3);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(2);
        roller.mockRoll(9);
        roller.mockRoll(10);
        roller.mockRoll(7);
        roller.mockRoll(10);
        roller.mockRoll(8);
        fleet = ap.makeEconRoll(10);
        assertEquals(null, fleet);
        assertCPs(6, 10, 23);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(7);
        roller.mockRoll(5); // max spending
        roller.mockRoll(3); //buy 1 base
        ap.buildColonyDefense();
        assertGroups(ap.getFleets().get(3), new Group(ShipType.BASE, 1));
        assertEquals(FleetType.DEFENSE_FLEET, ap.getFleets().get(3).getFleetType());
        assertCPs(6, 10, 11); //spend 12 on 1 base
        assertEquals(0, roller.rolls.size());
    }

    public void assertCPs(int fleetCP, int techCP, int defCP) {
        assertEquals(fleetCP, sheet.getFleetCP());
        assertEquals(techCP, sheet.getTechCP());
        assertEquals(defCP, sheet.getDefCP());
    }

    public void assertGroups(Fleet fleet, Group... expectedGroups) {
        assertEquals(Arrays.asList(expectedGroups), fleet.getGroups());
    }
}
