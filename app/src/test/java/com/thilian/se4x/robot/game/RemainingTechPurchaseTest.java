package com.thilian.se4x.robot.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.Technology;

public class RemainingTechPurchaseTest extends TechnologyPurchaseBase {
	@Test
	public void buyAttack() {
		assertBuysRemaining(Technology.ATTACK, 2);
	}

	@Test
	public void buyDefense() {
		assertBuysRemaining(Technology.DEFENSE, 4);
	}

	@Test
	public void buyTacticsOrAttackOrDefense() {
		ap.setLevel(Technology.ATTACK, 1);
		ap.setLevel(Technology.DEFENSE, 1);

		sheet.techCP = Technology.ATTACK.getCost(2);
		roller.mockRoll(5);
		buyTech();
		assertEquals(2, ap.getLevel(Technology.ATTACK));
		assertEquals(0, ap.getLevel(Technology.TACTICS));

		sheet.techCP = Technology.DEFENSE.getCost(2);
		roller.mockRoll(5);
		buyTech();
		assertEquals(2, ap.getLevel(Technology.DEFENSE));
		assertEquals(0, ap.getLevel(Technology.TACTICS));
		
		sheet.techCP = Technology.TACTICS.getCost(1);
		roller.mockRoll(1);
		buyTech();
		assertEquals(1, ap.getLevel(Technology.TACTICS));
		
		ap.setLevel(Technology.ATTACK, 1);
		ap.setLevel(Technology.DEFENSE, 0);
		ap.setLevel(Technology.TACTICS, 0);
		sheet.techCP = Technology.DEFENSE.getCost(1);
		roller.mockRoll(3);  //ATTACK IS NOT BUYABLE
		buyTech();
		assertEquals(1, ap.getLevel(Technology.ATTACK));
		assertEquals(1, ap.getLevel(Technology.DEFENSE));
		assertEquals(0, ap.getLevel(Technology.TACTICS));
		
		ap.setLevel(Technology.ATTACK, 0);
		ap.setLevel(Technology.DEFENSE, 1);
		ap.setLevel(Technology.TACTICS, 0);
		sheet.techCP = Technology.ATTACK.getCost(1);
		roller.mockRoll(3);  //DEFENSE IS NOT BUYABLE
		buyTech();
		assertEquals(1, ap.getLevel(Technology.ATTACK));
		assertEquals(1, ap.getLevel(Technology.DEFENSE));
		assertEquals(0, ap.getLevel(Technology.TACTICS));
	}

	@Test
	public void cantBuyTacticsIfHasNoAttackAndDefense() {
		sheet.techCP = 15;
		assertEquals(false, game.techBuyer.canBuyNextLevel(ap, Technology.TACTICS));
		
		ap.setLevel(Technology.ATTACK, 2);
		assertEquals(false, game.techBuyer.canBuyNextLevel(ap, Technology.TACTICS));
		
		ap.setLevel(Technology.ATTACK, 1);
		ap.setLevel(Technology.DEFENSE, 0);
		sheet.techCP = Technology.DEFENSE.getCost(1);
		assertEquals(true, game.techBuyer.canBuyNextLevel(ap, Technology.TACTICS));
	}
	
	@Test
	public void buyTactics() {
		ap.setLevel(Technology.ATTACK, 2);
		ap.setLevel(Technology.DEFENSE, 2);
		assertBuysRemaining(Technology.TACTICS, 1); //ONLY TACTICS AND MINESWEEP IS BUYABLE
	}

	@Test
	public void buyCloaking() {
		assertBuysRemaining(Technology.CLOAKING, 6);
	}

	@Test
	public void cantBuyCloakingIfSeenScanner2() {
		ap.setSeenLevel(Technology.SCANNER, 2);
		sheet.techCP = 100;
		assertEquals(false, game.techBuyer.canBuyNextLevel(ap, Technology.CLOAKING));
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
		assertBuysRemaining(Technology.MINE_SWEEP, 1); //NOTHING ELSE IS BUYABLE
	}
	
	@Test
	public void dontBuyMineSweepForDefenseFleet() {
		sheet.techCP = 100;
		assertEquals(true, game.techBuyer.canBuyNextLevel(fleet, Technology.MINE_SWEEP));
		fleet.setFleetType(FleetType.DEFENSE_FLEET);
		assertEquals(false, game.techBuyer.canBuyNextLevel(fleet, Technology.MINE_SWEEP));
	}
	
	private void assertBuysRemaining(Technology technology, int rollNeeded) {
		for (int i = technology.getStartingLevel(); i < technology.getMaxLevel(); i++)
			assertBuysNextRemaining(technology, i, rollNeeded);
	}

	private void assertBuysNextRemaining(Technology technology, int currentLevel, int rollNeeded) {
		ap.setLevel(technology, currentLevel);
		int nextLevel = currentLevel + 1;
		sheet.techCP = technology.getCost(nextLevel);
		assertBuys(technology, nextLevel, rollNeeded);
		assertEquals(0, sheet.techCP);
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
		game.techBuyer.spendRemainingTechCP(fleet);
	}

}
