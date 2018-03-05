package com.thilian.se4x.robot.game.basegame;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.thilian.se4x.robot.game.AlienEconomicSheet;
import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.MockRoller;
import com.thilian.se4x.robot.game.basegame.BaseGameScenario;
import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

public class Integration {

    private AlienEconomicSheet sheet;

    @Test
    public void economyRollStartsNewFleet() {
        sheet = new AlienEconomicSheet(Difficulty.NORMAL);
        MockRoller roller = new MockRoller();
        Game game = new Game();
        game.roller = roller;
        game.scenario = new BaseGameScenario(game);
        game.resetSeenLevels();
        AlienPlayer ap = new AlienPlayer(sheet, game, null);

        roller.mockRoll(1);
        assertEquals(null, ap.makeEconRoll(1));
        assertEquals(1, sheet.getExtraEcon(4));
        assertCPs(0, 0, 0);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(5);
        assertEquals(null, ap.makeEconRoll(2));
        assertCPs(0, 5, 0);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(4, 3, 2, 7);
        Fleet fleet = ap.makeEconRoll(3);
        assertEquals(10, fleet.getFleetCP());
        assertEquals(FleetType.REGULAR_FLEET, fleet.getFleetType());
        assertEquals(fleet, ap.getFleets().get(0));
        assertEquals(1, ap.getLevel(Technology.MOVE));
        assertCPs(0, 5, 0);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(4, 3, 3, 6);
        fleet = ap.makeEconRoll(4);
        assertEquals(null, fleet);
        assertCPs(15, 5, 0);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(7, 10, 10, 10);
        fleet = ap.makeEconRoll(5);
        assertEquals(null, fleet);
        assertCPs(15, 10, 20);
        assertEquals(0, roller.rolls.size());

        fleet = ap.getFleets().get(0);
        roller.mockRoll(9); // ShipSize
        ap.buildFleet(fleet);
        assertEquals(2, ap.getLevel(Technology.SHIP_SIZE));
        assertGroups(fleet, new Group(ShipType.DESTROYER, 1));
        assertCPs(16, 0, 20);
        assertEquals(0, roller.rolls.size());

        ap.destroyFleet(fleet);
        assertEquals(0, ap.getFleets().size());

        roller.mockRoll(6, 6, 9, 7, 5);
        fleet = ap.makeEconRoll(6);
        assertCPs(26, 10, 20);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(6, 9, 8, 3, 4, 8);
        fleet = ap.makeEconRoll(7);
        assertEquals(31, fleet.getFleetCP());
        assertEquals(FleetType.REGULAR_FLEET, fleet.getFleetType());
        assertEquals(fleet, ap.getFleets().get(0));
        assertEquals(1, ap.getLevel(Technology.MOVE));
        assertCPs(0, 25, 20);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(1, 4, 7, 9, 7);
        fleet = ap.makeEconRoll(8);
        assertEquals(null, fleet);
        assertCPs(10, 35, 20);
        assertEquals(0, roller.rolls.size());

        fleet = ap.getFleets().get(0);
        roller.mockRoll(9); // ShipSize
        roller.mockRoll(6); // Cloaking
        ap.buildFleet(fleet);
        assertEquals(2, ap.getLevel(Technology.SHIP_SIZE));
        assertEquals(1, ap.getLevel(Technology.CLOAKING));
        assertGroups(fleet, new Group(ShipType.RAIDER, 2));
        assertEquals(FleetType.RAIDER_FLEET, fleet.getFleetType());
        assertCPs(17, 5, 20);
        assertEquals(0, roller.rolls.size());

        roller.mockRoll(2, 9, 7, 7, 8);
        fleet = ap.makeEconRoll(9);
        assertEquals(null, fleet);
        assertCPs(22, 20, 20);
        assertEquals(0, roller.rolls.size());

        game.setSeenLevel(Technology.CLOAKING, 1);
        roller.mockRoll(1); // Scanners
        roller.mockRoll(9); // ShipSize (Ignored)
        roller.mockRoll(1); // Max number of ships
        roller.mockRoll(10); // Max bases
        ap.buildHomeDefense();
        assertEquals(2, ap.getLevel(Technology.SHIP_SIZE));
        assertEquals(1, ap.getLevel(Technology.SCANNER));
        assertGroups(ap.getFleets().get(1), new Group(ShipType.DESTROYER, 1), new Group(ShipType.SCOUT, 2));
        assertEquals(FleetType.REGULAR_FLEET, ap.getFleets().get(1).getFleetType());
        assertGroups(ap.getFleets().get(2), new Group(ShipType.BASE, 1), new Group(ShipType.MINE, 1));
        assertEquals(FleetType.DEFENSE_FLEET, ap.getFleets().get(2).getFleetType());
        assertCPs(1, 0, 3);
        assertEquals(0, roller.rolls.size());

        // TODO build raider fleet (isJustPurchasedCloaking)
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
