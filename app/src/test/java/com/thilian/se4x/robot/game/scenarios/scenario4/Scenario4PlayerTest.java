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
import com.thilian.se4x.robot.game.FleetBuildResult;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

public class Scenario4PlayerTest extends Scenario4Fixture {

    @Test
    public void buildHomeDefenseWithMinesAndGround2(){
        setCPs(70, 60, 30);
        setLevel(SHIP_SIZE, 4);
        setLevel(ATTACK, 2);
        setLevel(Technology.GROUND_COMBAT, 1);
        roller.mockRoll(5); //Ship size
        roller.mockRoll(95,69); //Cloak
        roller.mockRoll(4); //balanced fleet
        roller.mockRoll(4); //number of HI
        roller.mockRoll(3); //bases, then mines
        FleetBuildResult result = ap.buildHomeDefense();
        List<Fleet> fleets = result.getNewFleets();
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

    @Test
    public void buildColonyDefenseWithBaseAndGround1(){
        setCPs(70, 60, 30);
        setLevel(Technology.GROUND_COMBAT, 1);
        roller.mockRoll(9); //max spending
        roller.mockRoll(7);
        roller.mockRoll(5); //buy 1 base
        FleetBuildResult result = ((Scenario4Player)ap).buildColonyDefense();
        List<Fleet> fleets = result.getNewFleets();

        assertEquals(FleetType.DEFENSE_FLEET, fleets.get(0).getFleetType());
        assertGroups(fleets.get(0),
                new Group(ShipType.BASE, 1), new Group(ShipType.INFANTRY, 2));
        assertCPs(70, 60, 14);
        assertEquals(true, fleets.get(0).hadFirstCombat());
        
    }


    @Test
    public void buildColonyDefenseWith2MinesAndGround2(){
        setCPs(70, 60, 30);
        setLevel(Technology.GROUND_COMBAT, 2);
        roller.mockRoll(9); //max spending
        roller.mockRoll(7);
        roller.mockRoll(6); //buy 2 mines
        FleetBuildResult result = ((Scenario4Player)ap).buildColonyDefense();
        List<Fleet> fleets = result.getNewFleets();
        assertEquals(FleetType.DEFENSE_FLEET, fleets.get(0).getFleetType());
        assertGroups(fleets.get(0),
                new Group(ShipType.MINE, 2), new Group(ShipType.HEAVY_INFANTRY, 2));
        assertCPs(70, 60, 14);
        assertEquals(true, fleets.get(0).hadFirstCombat());
    }
}
