package com.thilian.se4x.robot.game.scenarios.scenario4;

import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.FleetType;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static com.thilian.se4x.robot.game.enums.ShipType.BASE;
import static com.thilian.se4x.robot.game.enums.ShipType.HEAVY_INFANTRY;
import static com.thilian.se4x.robot.game.enums.ShipType.INFANTRY;
import static com.thilian.se4x.robot.game.enums.ShipType.MINE;
import static com.thilian.se4x.robot.game.enums.Technology.GROUND_COMBAT;
import static org.junit.Assert.assertEquals;

public class ColonyDefenseBuildTest extends Scenario4Fixture{

    @Before
    public void setUp() {
        sheet.setDefCP(100);
    }

    @Test
    public void noCPbuildsNull(){
        sheet.setDefCP(1);
        assertEquals(0, ((Scenario4Player)ap).buildColonyDefense().getNewFleets().size());
    }
    
    @Test
    public void dontSpendOverDiceRoll() {
        roller.mockRoll(1);
        roller.mockRoll(1); //Max CP
        roller.mockRoll(1);
        assertBuiltGroups(new Group(INFANTRY, 1));    
}

    @Test
    public void dontSpendMoreThanDefCP() {
        sheet.setDefCP(2);
        roller.mockRoll(10);
        roller.mockRoll(10);//Max CP
        roller.mockRoll(1);
        assertBuiltGroups(new Group(INFANTRY, 1));    
    }

    @Test
    public void buy1Base() {
        roller.mockRoll(5);
        roller.mockRoll(7);//max cp
        roller.mockRoll(5); //buy 1 base
        assertBuiltGroups(new Group(BASE, 1));
    }

    @Test
    public void buy0Base() {
        roller.mockRoll(1);
        roller.mockRoll(0); //max cp
        roller.mockRoll(5); //buy 1 base
        assertBuiltGroups();
    }

    @Test
    public void buy2Mines() {
        roller.mockRoll(3);
        roller.mockRoll(7); //max cp
        roller.mockRoll(6); //buy 2 mines
        assertBuiltGroups(new Group(MINE, 2));
    }

    @Test
    public void buy1Mine() {
        roller.mockRoll(3);
        roller.mockRoll(2); //max cp
        roller.mockRoll(6); //buy 2 mines
        assertBuiltGroups(new Group(MINE, 1));
    }

    @Test
    public void buy1MineIfCantAffordBase() {
        roller.mockRoll(2);
        roller.mockRoll(3); //max cp
        roller.mockRoll(5); //buy 1 base
        assertBuiltGroups(new Group(MINE, 1));
    }

    @Test
    public void buy2MinesAnd4Infantry() {
        roller.mockRoll(10);
        roller.mockRoll(9); //max cp
        roller.mockRoll(6); //buy 2 mines
        assertBuiltGroups(new Group(MINE, 2), new Group(INFANTRY, 4));
    }

    @Test
    public void buy2MinesAnd3HeavyInfantry() {
        ap.setLevel(GROUND_COMBAT, 2);
        roller.mockRoll(10);
        roller.mockRoll(9); //max cp
        roller.mockRoll(6); //buy 2 mines
        assertBuiltGroups(new Group(MINE, 2), new Group(HEAVY_INFANTRY, 3));
    }

    @Test
    public void buy2MinesAnd1InfIfCantAffordHI() {
        ap.setLevel(GROUND_COMBAT, 2);
        roller.mockRoll(10);
        roller.mockRoll(2); //max cp
        roller.mockRoll(6); //buy 2 mines
        assertBuiltGroups(new Group(MINE, 2),new Group(INFANTRY, 1));
    }

    private void assertBuiltGroups(Group... expectedGroups) {
        Fleet fleet = ((DefenseBuilder)defBuilder).buildColonyDefense(ap);
        int expectedCost = 0;
        for (Group g : expectedGroups) {
            expectedCost += g.getShipType().getCost() * g.getSize();
        }
        assertEquals(FleetType.DEFENSE_FLEET, fleet.getFleetType());
        assertEquals(Arrays.asList(expectedGroups), fleet.getGroups());
        assertEquals(expectedCost, fleet.getBuildCost());
    }

}
