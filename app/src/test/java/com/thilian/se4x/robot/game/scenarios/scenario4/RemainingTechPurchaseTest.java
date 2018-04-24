package com.thilian.se4x.robot.game.scenarios.scenario4;

import com.thilian.se4x.robot.game.enums.Technology;

import org.junit.Test;

import static com.thilian.se4x.robot.game.enums.FleetBuildOption.HOME_DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.ATTACK;
import static com.thilian.se4x.robot.game.enums.Technology.BOARDING;
import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static com.thilian.se4x.robot.game.enums.Technology.DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.FIGHTERS;
import static com.thilian.se4x.robot.game.enums.Technology.GROUND_COMBAT;
import static com.thilian.se4x.robot.game.enums.Technology.MILITARY_ACADEMY;
import static com.thilian.se4x.robot.game.enums.Technology.MINE_SWEEPER;
import static com.thilian.se4x.robot.game.enums.Technology.POINT_DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.SCANNER;
import static com.thilian.se4x.robot.game.enums.Technology.SECURITY_FORCES;
import static com.thilian.se4x.robot.game.enums.Technology.SHIP_SIZE;
import static com.thilian.se4x.robot.game.enums.Technology.TACTICS;
import static org.junit.Assert.assertEquals;

//TODO THIS WHOLE CLASS IS A DUPLICATE
public class RemainingTechPurchaseTest extends Scenario4TechnologyBuyerTestBase {
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
        assertBuysNextRemaining(SHIP_SIZE, 1, 16, 25);
        assertBuysNextRemaining(SHIP_SIZE, 2, 16, 28);
        assertBuysNextRemaining(SHIP_SIZE, 3, 16, 89);
        assertBuysNextRemaining(SHIP_SIZE, 4, 16, 89);
        assertBuysNextRemaining(SHIP_SIZE, 5, 16, 89);
        assertBuysNextRemaining(SHIP_SIZE, 6, 16, 100);
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
        roller.mockRoll(100,58);
        buyTech();
        assertEquals(2, ap.getLevel(ATTACK));
        assertEquals(0, ap.getLevel(TACTICS));

        sheet.setTechCP(game.scenario.getCost(DEFENSE, 2));
        roller.mockRoll(100,68);
        buyTech();
        assertEquals(2, ap.getLevel(Technology.DEFENSE));
        assertEquals(0, ap.getLevel(Technology.TACTICS));

        sheet.setTechCP(game.scenario.getCost(TACTICS, 1));
        roller.mockRoll(40,28);
        buyTech();
        assertEquals(1, ap.getLevel(Technology.TACTICS));

        ap.setLevel(ATTACK, 1);
        ap.setLevel(Technology.DEFENSE, 0);
        ap.setLevel(Technology.TACTICS, 0);
        sheet.setTechCP(game.scenario.getCost(DEFENSE, 1));
        roller.mockRoll(69,48); // ATTACK IS NOT BUYABLE
        buyTech();
        assertEquals(1, ap.getLevel(ATTACK));
        assertEquals(1, ap.getLevel(Technology.DEFENSE));
        assertEquals(0, ap.getLevel(Technology.TACTICS));

        ap.setLevel(ATTACK, 0);
        ap.setLevel(Technology.DEFENSE, 1);
        ap.setLevel(Technology.TACTICS, 0);
        sheet.setTechCP(game.scenario.getCost(ATTACK, 1));
        roller.mockRoll(69, 48); // DEFENSE IS NOT BUYABLE
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
        assertBuysNextRemaining(TACTICS, 0, 28, 40);
        assertBuysNextRemaining(TACTICS, 1, 28, 40);
        assertBuysNextRemaining(TACTICS, 2, 28, 40);
    }

    @Test
    public void buyCloaking() {
        assertBuysNextRemaining(CLOAKING, 0, 71, 100);
        assertBuysNextRemaining(CLOAKING, 1, 71, 100);
    }

    @Test
    public void cantBuyCloakingIfSeenScanner2() {
        game.setSeenLevel(Technology.SCANNER, 2);
        sheet.setTechCP(100);
        assertEquals(false, techBuyer.canBuyNextLevel(ap, Technology.CLOAKING));
    }

    @Test
    public void buyScaner() {
        assertBuysNextRemaining(SCANNER, 0, 70, 89);// CLOAK & FIGHTERS NOT BUYABLE
        assertBuysNextRemaining(SCANNER, 1, 70, 89);
    }

    @Test
    public void buyFighters() {
        assertBuysNextRemaining(FIGHTERS, 0, 78, 97);// CLOAK IS NOT BUYABLE
        assertBuysNextRemaining(FIGHTERS, 1, 78, 97);
        assertBuysNextRemaining(FIGHTERS, 2, 78, 97);
    }

    @Test
    public void buyPointDefense() {
        assertBuysNextRemaining(POINT_DEFENSE, 0, 73, 89);// CLOAK & FIGHTERS NOT BUYABLE
        assertBuysNextRemaining(POINT_DEFENSE, 1, 73, 89);
        assertBuysNextRemaining(POINT_DEFENSE, 2, 73, 89);
    }

    @Test
    public void buyMineSweep() {
        assertBuysNextRemaining(Technology.MINE_SWEEPER, 0, 21,25); // NOTHING ELSE
                                                                 // IS BUYABLE
        assertBuysNextRemaining(Technology.MINE_SWEEPER, 1, 21,28); // NOTHING ELSE
                                                                 // IS BUYABLE
        assertBuysNextRemaining(Technology.MINE_SWEEPER, 2, 74,89); // ATT1, DEF1,
                                                                 // TA1, PD1,
                                                                 // SC1 IS
                                                                 // BUYABLE
    }

    @Test
    public void dontBuyMineSweepForDefenseFleet() {
        sheet.setTechCP(100);
        assertEquals(true, techBuyer.canBuyNextLevel(fleet, Technology.MINE_SWEEPER));
        assertEquals(false, techBuyer.canBuyNextLevel(fleet, Technology.MINE_SWEEPER, HOME_DEFENSE));
    }

    // TODO THIS IS NEW STUFF

    @Test
    public void buySecurityForces() {
        assertBuysNextRemaining(SECURITY_FORCES, 0, 24, 28);
        assertBuysNextRemaining(SECURITY_FORCES, 1, 24, 28);
    }

    @Test
    public void buyMilitaryAcademy() {
        assertBuysNextRemaining(MILITARY_ACADEMY, 0, 25, 25); // MS is buyable
        assertBuysNextRemaining(MILITARY_ACADEMY, 1, 84, 89); // ATT1, DEF1, TA1,
                                                          // PD1, SC1 IS BUYABLE
                                                          // // MS is buyable
    }

    @Test
    public void buyBoarding() {
        assertBuysNextRemaining(BOARDING, 0, 89, 89);
        assertBuysNextRemaining(BOARDING, 1, 97, 97);
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
