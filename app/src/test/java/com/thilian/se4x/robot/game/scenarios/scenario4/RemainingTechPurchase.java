package com.thilian.se4x.robot.game.scenarios.scenario4;

import static com.thilian.se4x.robot.game.enums.Technology.*;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.Technology;

//TODO THIS WHOLE CLASS IS A DUPLICATE
public class RemainingTechPurchase extends Scenario4TechnologyBuyerTestBase {
//    private HashMap<Technology, Integer> rollMap = new HashMap<>();
//    {
//        rollMap.put(ATTACK, 20);
//        rollMap.put(DEFENSE, 20);
//        rollMap.put(FIGHTERS, 8);
//        rollMap.put(SCANNER, 2);
//        rollMap.put(MINE_SWEEPER, 5);
//        rollMap.put(TACTICS, 12);
//        rollMap.put(SECURITY_FORCES, 3);
//        rollMap.put(MILITARY_ACADEMY, 4);
//        rollMap.put(POINT_DEFENSE, 3);
//        rollMap.put(CLOAKING, 3);
//        rollMap.put(BOARDING, 4);
//        rollMap.put(SHIP_SIZE, 16);
//    }

    @Test
    public void expansionsTechnologiesAvailable() {
        assertEquals(7, game.scenario.getMaxLevel(SHIP_SIZE));
        assertEquals(3, game.scenario.getMaxLevel(MINE_SWEEPER));
        assertEquals(2, game.scenario.getMaxLevel(BOARDING));
        assertEquals(2, game.scenario.getMaxLevel(SECURITY_FORCES));
        assertEquals(3, game.scenario.getMaxLevel(GROUND_COMBAT));
        assertEquals(2, game.scenario.getMaxLevel(MILITARY_ACADEMY));
    }

    @Test
    public void buyShipSize() {
        assertBuysRemaining(SHIP_SIZE, 16);
    }

    @Test
    public void buyAttack() {
        assertBuysNextRemaining(ATTACK, 0, 36, 89); //CLOAKING, FIGHTERS
        assertBuysNextRemaining(ATTACK, 1, 36, 100);
        assertBuysNextRemaining(ATTACK, 2, 36, 97);
    }

    @Test
    public void buyDefense() {
        assertBuysNextRemaining(DEFENSE, 0, 56, 89); //CLOAKING, FIGHTERS
        assertBuysNextRemaining(DEFENSE, 1, 56, 100);
        assertBuysNextRemaining(DEFENSE, 2, 56, 97);
    }

    @Test
    public void buyTacticsOrAttackOrDefense() {
        ap.setLevel(ATTACK, 1);
        ap.setLevel(Technology.DEFENSE, 1);

        sheet.setTechCP(game.scenario.getCost(ATTACK, 2));
        roller.mockRoll(58);
        buyTech();
        assertEquals(2, ap.getLevel(ATTACK));
        assertEquals(0, ap.getLevel(TACTICS));

        sheet.setTechCP(game.scenario.getCost(DEFENSE, 2));
        roller.mockRoll(68);
        buyTech();
        assertEquals(2, ap.getLevel(Technology.DEFENSE));
        assertEquals(0, ap.getLevel(Technology.TACTICS));

        sheet.setTechCP(game.scenario.getCost(TACTICS, 1));
        roller.mockRoll(28);
        buyTech();
        assertEquals(1, ap.getLevel(Technology.TACTICS));

        ap.setLevel(ATTACK, 1);
        ap.setLevel(Technology.DEFENSE, 0);
        ap.setLevel(Technology.TACTICS, 0);
        sheet.setTechCP(game.scenario.getCost(DEFENSE, 1));
        roller.mockRoll(48); // ATTACK IS NOT BUYABLE
        buyTech();
        assertEquals(1, ap.getLevel(ATTACK));
        assertEquals(1, ap.getLevel(Technology.DEFENSE));
        assertEquals(0, ap.getLevel(Technology.TACTICS));

        ap.setLevel(ATTACK, 0);
        ap.setLevel(Technology.DEFENSE, 1);
        ap.setLevel(Technology.TACTICS, 0);
        sheet.setTechCP(game.scenario.getCost(ATTACK, 1));
        roller.mockRoll(48); // DEFENSE IS NOT BUYABLE
        buyTech();
        assertEquals(1, ap.getLevel(ATTACK));
        assertEquals(1, ap.getLevel(DEFENSE));
        assertEquals(0, ap.getLevel(TACTICS));
    }

    @Test
    public void cantBuyTacticsIfHasNoAttackAndDefense() {
        sheet.setTechCP(15);
        assertEquals(false, techBuyer.canBuyNextLevel(ap, Technology.TACTICS));

        ap.setLevel(ATTACK, 2);
        assertEquals(false, techBuyer.canBuyNextLevel(ap, Technology.TACTICS));

        ap.setLevel(ATTACK, 1);
        ap.setLevel(Technology.DEFENSE, 0);
        sheet.setTechCP(game.scenario.getCost(DEFENSE, 1));
        assertEquals(true, techBuyer.canBuyNextLevel(ap, Technology.TACTICS));
    }

    @Test
    public void buyTactics() {
        ap.setLevel(ATTACK, 2);
        ap.setLevel(Technology.DEFENSE, 2);
        assertBuysRemaining(Technology.TACTICS, 28); // ONLY TACTICS AND
                                                     // MINESWEEP IS BUYABLE
    }

    @Test
    public void buyCloaking() {
        assertBuysRemaining(Technology.CLOAKING, 71);
    }

    @Test
    public void cantBuyCloakingIfSeenScanner2() {
        game.setSeenLevel(Technology.SCANNER, 2);
        sheet.setTechCP(100);
        assertEquals(false, techBuyer.canBuyNextLevel(ap, Technology.CLOAKING));
    }

    @Test
    public void buyScaner() {
        assertBuysRemaining(Technology.SCANNER, 70); // CLOAK IS NOT BUYABLE
    }

    @Test
    public void buyFighters() {
        assertBuysRemaining(Technology.FIGHTERS, 78); // CLOAK IS NOT BUYABLE
    }

    @Test
    public void buyPointDefense() {
        assertBuysRemaining(Technology.POINT_DEFENSE, 73); // FIGHTER AND CLOAK
                                                           // IS NOT BUYABLE
    }

    @Test
    public void buyMineSweep() {
        assertBuysNextRemaining(Technology.MINE_SWEEPER, 0, 21); // NOTHING ELSE
                                                                 // IS BUYABLE
        assertBuysNextRemaining(Technology.MINE_SWEEPER, 1, 21); // NOTHING ELSE
                                                                 // IS BUYABLE
        assertBuysNextRemaining(Technology.MINE_SWEEPER, 2, 78); // ATT1, DEF1,
                                                                 // TA1, PD1,
                                                                 // SC1 IS
                                                                 // BUYABLE
    }

    @Test
    public void dontBuyMineSweepForDefenseFleet() {
        sheet.setTechCP(100);
        assertEquals(true, techBuyer.canBuyNextLevel(fleet, Technology.MINE_SWEEPER));
        fleet.setFleetType(FleetType.DEFENSE_FLEET);
        assertEquals(false, techBuyer.canBuyNextLevel(fleet, Technology.MINE_SWEEPER));
    }

    // TODO THIS IS NEW STUFF

    @Test
    public void buySecurityForces() {
        assertBuysRemaining(SECURITY_FORCES, 24); // MS is buyable
    }

    @Test
    public void buyMilitaryAcademy() {
        assertBuysNextRemaining(MILITARY_ACADEMY, 0, 25); // MS is buyable
        assertBuysNextRemaining(MILITARY_ACADEMY, 1, 84); // ATT1, DEF1, TA1,
                                                          // PD1, SC1 IS BUYABLE
                                                          // // MS is buyable
    }

    @Test
    public void buyBoarding() {
        assertBuysNextRemaining(BOARDING, 0, 89);
        assertBuysNextRemaining(BOARDING, 1, 97);
    }

    // TODO DUPLICATE!!!

    private void assertBuysNextRemaining(Technology technology, int currentLevel, int rollNeeded, int rollLimit) {
        ap.setLevel(technology, currentLevel);
        int nextLevel = currentLevel + 1;
        sheet.setTechCP(game.scenario.getCost(technology, nextLevel));
        assertBuys(technology, nextLevel, rollNeeded, rollLimit);
        assertEquals(0, sheet.getTechCP());
    }

    private void assertBuys(Technology technology, int techonogyLevel, int roll, int limit) {
        roller.mockRoll(limit, roll);
        buyTech();
        assertLevel(technology, techonogyLevel);
    }

    private void buyTech() {
        techBuyer.spendRemainingTechCP(fleet);
    }

}
