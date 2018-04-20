package com.thilian.se4x.robot.game.scenarios.scenario4;

import static com.thilian.se4x.robot.game.enums.ShipType.BATTLECRUISER;
import static com.thilian.se4x.robot.game.enums.ShipType.BATTLESHIP;
import static com.thilian.se4x.robot.game.enums.ShipType.CRUISER;
import static com.thilian.se4x.robot.game.enums.ShipType.DESTROYER;
import static com.thilian.se4x.robot.game.enums.Technology.ATTACK;
import static com.thilian.se4x.robot.game.enums.Technology.SHIP_SIZE;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

public class Scenario4PlayerTest extends Scenario4Fixture {

    @Test
    public void buildHomeDefenseWithMinesAndGround2(){
        sheet.setFleetCP(70);
        sheet.setTechCP(60);
        sheet.setDefCP(30);
        setLevel(SHIP_SIZE, 4);
        setLevel(ATTACK, 2);
        setLevel(Technology.GROUND_COMBAT, 1);
        roller.mockRoll(5); //Ship size
        roller.mockRoll(95,69); //Cloak
        roller.mockRoll(4); //balanced fleet
        roller.mockRoll(4); //number of HI
        roller.mockRoll(3); //bases, then mines
        List<Fleet> fleets = ap.buildHomeDefense();
        assertEquals(FleetType.REGULAR_FLEET, fleets.get(0).getFleetType());
        assertGroups(fleets.get(0),
                new Group(ShipType.TRANSPORT, 1), new Group (ShipType.MARINE, 5), new Group (ShipType.HEAVY_INFANTRY, 1),
                new Group(BATTLESHIP, 1), new Group(DESTROYER, 1), new Group(BATTLECRUISER, 1), new Group(CRUISER, 2));
        assertEquals(FleetType.DEFENSE_FLEET, fleets.get(1).getFleetType());
        assertGroups(fleets.get(1),
                new Group(ShipType.HEAVY_INFANTRY, 4),
                new Group(ShipType.MINE, 3));
        assertRoller();
        assertCPs(2, 0, 3);
        assertLevel(SHIP_SIZE, 5);
        assertLevel(Technology.GROUND_COMBAT, 2);
        assertLevel(Technology.CLOAKING, 1);
    }
}
