package com.thilian.se4x.robot.game.scenario4;

import static com.thilian.se4x.robot.game.enums.Technology.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.Technology;


//TODO THIS WHOLE CLASS IS A DUPLICATE
public class RemainingTechPurchase extends Scenario4TechnologyBuyerTestBase{
    @Test
    public void expansionsTechnologiesAvailable(){
    	assertEquals(7, game.technologyPrices.getMaxLevel(SHIP_SIZE));
    	assertEquals(3, game.technologyPrices.getMaxLevel(MINE_SWEEPER));
        assertEquals(2, game.technologyPrices.getMaxLevel(BOARDING));
        assertEquals(2, game.technologyPrices.getMaxLevel(SECURITY_FORCES));
        assertEquals(3, game.technologyPrices.getMaxLevel(GROUND_COMBAT));
        assertEquals(2, game.technologyPrices.getMaxLevel(MILITARY_ACADEMY));
    }

    @Test
    public void buyShipSize(){
    	assertBuysRemaining(SHIP_SIZE, 16);
    }

    @Test
    public void buyAttack() {
        assertBuysRemaining(ATTACK, 36);
    }

    @Test
    public void buyDefense() {
        assertBuysRemaining(DEFENSE, 56);
    }
    
    @Test
    public void buyTacticsOrAttackOrDefense() {
        ap.setLevel(ATTACK, 1);
        ap.setLevel(Technology.DEFENSE, 1);

        sheet.setTechCP(game.technologyPrices.getCost(ATTACK, 2));
        roller.mockRoll(58);
        buyTech();
        assertEquals(2, ap.getLevel(ATTACK));
        assertEquals(0, ap.getLevel(TACTICS));

        sheet.setTechCP(game.technologyPrices.getCost(DEFENSE, 2));
        roller.mockRoll(68);
        buyTech();
        assertEquals(2, ap.getLevel(Technology.DEFENSE));
        assertEquals(0, ap.getLevel(Technology.TACTICS));

        sheet.setTechCP(game.technologyPrices.getCost(TACTICS, 1));
        roller.mockRoll(28);
        buyTech();
        assertEquals(1, ap.getLevel(Technology.TACTICS));

        ap.setLevel(ATTACK, 1);
        ap.setLevel(Technology.DEFENSE, 0);
        ap.setLevel(Technology.TACTICS, 0);
        sheet.setTechCP(game.technologyPrices.getCost(DEFENSE, 1));
        roller.mockRoll(48);  //ATTACK IS NOT BUYABLE
        buyTech();
        assertEquals(1, ap.getLevel(ATTACK));
        assertEquals(1, ap.getLevel(Technology.DEFENSE));
        assertEquals(0, ap.getLevel(Technology.TACTICS));

        ap.setLevel(ATTACK, 0);
        ap.setLevel(Technology.DEFENSE, 1);
        ap.setLevel(Technology.TACTICS, 0);
        sheet.setTechCP(game.technologyPrices.getCost(ATTACK, 1));
        roller.mockRoll(48);  //DEFENSE IS NOT BUYABLE
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
        sheet.setTechCP(game.technologyPrices.getCost(DEFENSE, 1));
        assertEquals(true, techBuyer.canBuyNextLevel(ap, Technology.TACTICS));
    }
    
    @Test
    public void buyTactics() {
        ap.setLevel(ATTACK, 2);
        ap.setLevel(Technology.DEFENSE, 2);
        assertBuysRemaining(Technology.TACTICS, 28); //ONLY TACTICS AND MINESWEEP IS BUYABLE
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
        assertBuysRemaining(Technology.SCANNER, 70); //CLOAK IS NOT BUYABLE
    }
    
    @Test
    public void buyFighters() {
        assertBuysRemaining(Technology.FIGHTERS, 78); //CLOAK IS NOT BUYABLE
    }
    
    @Test
    public void buyPointDefense() {
        assertBuysRemaining(Technology.POINT_DEFENSE, 73); //FIGHTER AND CLOAK IS NOT BUYABLE
    }
    
    @Test
    public void buyMineSweep() {
        assertBuysNextRemaining(Technology.MINE_SWEEPER, 0, 21); //NOTHING ELSE IS BUYABLE
        assertBuysNextRemaining(Technology.MINE_SWEEPER, 1, 21); //NOTHING ELSE IS BUYABLE
        assertBuysNextRemaining(Technology.MINE_SWEEPER, 2, 78); //ATT1, DEF1, TA1, PD1, SC1 IS BUYABLE
    }
    
    @Test
    public void dontBuyMineSweepForDefenseFleet() {
        sheet.setTechCP(100);
        assertEquals(true, techBuyer.canBuyNextLevel(fleet, Technology.MINE_SWEEPER));
        fleet.setFleetType(FleetType.DEFENSE_FLEET);
        assertEquals(false, techBuyer.canBuyNextLevel(fleet, Technology.MINE_SWEEPER));
    }
    
    //TODO THIS IS NEW STUFF
    
    @Test
    public void buySecurityForces(){
    	assertBuysRemaining(SECURITY_FORCES, 24); //MS is buyable
    }
    
    @Test
    public void buyMilitaryAcademy(){
    	assertBuysNextRemaining(MILITARY_ACADEMY, 0, 25); // MS is buyable
    	assertBuysNextRemaining(MILITARY_ACADEMY, 1, 84); //ATT1, DEF1, TA1, PD1, SC1 IS BUYABLE // MS is buyable
    }
    
    @Test
    public void buyBoarding(){
    	assertBuysNextRemaining(BOARDING, 0, 89);
    	assertBuysNextRemaining(BOARDING, 1, 97);
    }

    //TODO DUPLICATE!!!
    private void assertBuysRemaining(Technology technology, int rollNeeded) {
        for (int i = game.technologyPrices.getStartingLevel(technology); i < game.technologyPrices.getMaxLevel(technology); i++)
            assertBuysNextRemaining(technology, i, rollNeeded);
    }

    private void assertBuysNextRemaining(Technology technology, int currentLevel, int rollNeeded) {
        ap.setLevel(technology, currentLevel);
        int nextLevel = currentLevel + 1;
        sheet.setTechCP(game.technologyPrices.getCost(technology, nextLevel));
        assertBuys(technology, nextLevel, rollNeeded);
        assertEquals(0, sheet.getTechCP());
    }

    private void assertBuys(Technology technology, int techonogyLevel, int roll) {
        roller.mockRoll(roll);
        buyTech();
        assertLevel(technology, techonogyLevel);
    }

	private void buyTech() {
		techBuyer.spendRemainingTechCP(fleet);
	}

}
