package com.thilian.se4x.robot.game.scenarios.basegame;

import static com.thilian.se4x.robot.game.enums.Technology.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.Technology;

public class RemainingTechPurchaseTest extends BasegameTechnologyBuyerTestBase {

    @Test
    public void baseTechnologiesOnly(){
    	assertNotAvailabe(BOARDING);
        assertNotAvailabe(SECURITY_FORCES);
        assertNotAvailabe(GROUND_COMBAT);
        assertNotAvailabe(MILITARY_ACADEMY);
        assertEquals(6, game.scenario.getMaxLevel(SHIP_SIZE));
        assertEquals(2, game.scenario.getMaxLevel(MINE_SWEEPER));
    }

    private void assertNotAvailabe(Technology technology) {
        try {
            techBuyer.canBuyNextLevel(ap, technology);
            fail();
        }
        catch (NullPointerException e){
        }
    }

    @Test
    public void buyAttack() {
        assertBuysRemaining(ATTACK, 2);
    }

    @Test
    public void buyDefense() {
        assertBuysRemaining(DEFENSE, 4);
    }

    @Test
    public void buyTacticsOrAttackOrDefense() {
        ap.setLevel(ATTACK, 1);
        ap.setLevel(DEFENSE, 1);

        sheet.setTechCP(game.scenario.getCost(ATTACK, 2));
        roller.mockRoll(5);
        buyTech();
        assertEquals(2, ap.getLevel(ATTACK));
        assertEquals(0, ap.getLevel(TACTICS));

        sheet.setTechCP(game.scenario.getCost(DEFENSE, 2));
        roller.mockRoll(5);
        buyTech();
        assertEquals(2, ap.getLevel(DEFENSE));
        assertEquals(0, ap.getLevel(TACTICS));

        sheet.setTechCP(game.scenario.getCost(TACTICS, 1));
        roller.mockRoll(1);
        buyTech();
        assertEquals(1, ap.getLevel(TACTICS));

        ap.setLevel(ATTACK, 1);
        ap.setLevel(DEFENSE, 0);
        ap.setLevel(TACTICS, 0);
        sheet.setTechCP(game.scenario.getCost(DEFENSE, 1));
        roller.mockRoll(3);  //ATTACK IS NOT BUYABLE
        buyTech();
        assertEquals(1, ap.getLevel(ATTACK));
        assertEquals(1, ap.getLevel(DEFENSE));
        assertEquals(0, ap.getLevel(TACTICS));

        ap.setLevel(ATTACK, 0);
        ap.setLevel(Technology.DEFENSE, 1);
        ap.setLevel(Technology.TACTICS, 0);
        sheet.setTechCP(game.scenario.getCost(ATTACK, 1));
        roller.mockRoll(3);  //DEFENSE IS NOT BUYABLE
        buyTech();
        assertEquals(1, ap.getLevel(ATTACK));
        assertEquals(1, ap.getLevel(DEFENSE));
        assertEquals(0, ap.getLevel(TACTICS));
    }

    @Test
    public void cantBuyTacticsIfHasNoAttackAndDefense() {
        sheet.setTechCP(15);
        ap.setLevel(ATTACK, 0);
        ap.setLevel(DEFENSE, 0);
        assertEquals(false, techBuyer.canBuyNextLevel(ap, Technology.TACTICS));

        ap.setLevel(ATTACK, 2);
        ap.setLevel(DEFENSE, 0);
        assertEquals(false, techBuyer.canBuyNextLevel(ap, Technology.TACTICS));

        ap.setLevel(ATTACK, 0);
        ap.setLevel(DEFENSE, 2);
        assertEquals(false, techBuyer.canBuyNextLevel(ap, Technology.TACTICS));


        ap.setLevel(ATTACK, 2);
        ap.setLevel(DEFENSE, 2);
        assertEquals(true, techBuyer.canBuyNextLevel(ap, Technology.TACTICS));

        ap.setLevel(ATTACK, 1);
        ap.setLevel(Technology.DEFENSE, 0);
        sheet.setTechCP(game.scenario.getCost(DEFENSE, 1));
        assertEquals(true, techBuyer.canBuyNextLevel(ap, Technology.TACTICS));
    }

    @Test
    public void buyTactics() {
        ap.setLevel(ATTACK, 2);
        ap.setLevel(Technology.DEFENSE, 2);
        assertBuysRemaining(Technology.TACTICS, 1); //ONLY TACTICS AND MINESWEEP IS BUYABLE
    }

    @Test
    public void buyCloaking() {
        assertBuysRemaining(Technology.CLOAKING, 6);
    }

    @Test
    public void cantBuyCloakingIfSeenScanner2() {
        game.setSeenLevel(Technology.SCANNER, 2);
        sheet.setTechCP(100);
        assertEquals(false, techBuyer.canBuyNextLevel(ap, Technology.CLOAKING));
    }

    @Test
    public void buyScaner() {
        assertBuysRemaining(Technology.SCANNER, 6); //CLOAK IS NOT BUYABLE
    }

    @Test
    public void buyFighters() {
        assertBuysRemaining(Technology.FIGHTERS, 7); //CLOAK IS NOT BUYABLE
    }

    @Test
    public void buyPointDefense() {
        assertBuysRemaining(Technology.POINT_DEFENSE, 7); //FIGHTER AND CLOAK IS NOT BUYABLE
    }

    @Test
    public void buyMineSweep() {
        assertBuysRemaining(Technology.MINE_SWEEPER, 1); //NOTHING ELSE IS BUYABLE
    }

    @Test
    public void dontBuyMineSweepForDefenseFleet() {
        sheet.setTechCP(100);
        assertEquals(true, techBuyer.canBuyNextLevel(fleet, Technology.MINE_SWEEPER));
        fleet.setFleetType(FleetType.DEFENSE_FLEET);
        assertEquals(false, techBuyer.canBuyNextLevel(fleet, Technology.MINE_SWEEPER));
    }

    private void assertBuysRemaining(Technology technology, int rollNeeded) {
        for (int i = game.scenario.getStartingLevel(technology); i < game.scenario.getMaxLevel(technology); i++)
            assertBuysNextRemaining(technology, i, rollNeeded);
    }

    private void assertBuysNextRemaining(Technology technology, int currentLevel, int rollNeeded) {
        ap.setLevel(technology, currentLevel);
        int nextLevel = currentLevel + 1;
        sheet.setTechCP(game.scenario.getCost(technology, nextLevel));
        assertBuys(technology, nextLevel, rollNeeded);
        assertEquals(0, sheet.getTechCP());
    }

    private void assertBuys(Technology technology, int techonogyLevel, int roll) {
        roller.mockRoll(roll);
        doBuys(technology, techonogyLevel);
    }

    private void doBuys(Technology technology, int techonogyLevel) {
        buyTech();
        assertLevel(technology, techonogyLevel);
    }

    private void buyTech() {
        techBuyer.spendRemainingTechCP(fleet);
    }

}
