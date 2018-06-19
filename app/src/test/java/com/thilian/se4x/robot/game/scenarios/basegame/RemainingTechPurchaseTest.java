/*
 * Copyright (C) 2018 Balázs Péter
 *
 * This file is part of Alien Player 4X.
 *
 * Alien Player 4X is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alien Player 4X is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Alien Player 4X.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.thilian.se4x.robot.game.scenarios.basegame;

import com.thilian.se4x.robot.game.buyers.technology.CloakingBuyer;
import com.thilian.se4x.robot.game.buyers.technology.MineSweeperBuyer;
import com.thilian.se4x.robot.game.buyers.technology.TacticsBuyer;
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
import static org.junit.Assert.assertFalse;

public class RemainingTechPurchaseTest extends BasegameTechnologyBuyerTestBase {

    @Test
    public void baseTechnologiesOnly() {
        assertNotAvailable(BOARDING);
        assertNotAvailable(SECURITY_FORCES);
        assertNotAvailable(GROUND_COMBAT);
        assertNotAvailable(MILITARY_ACADEMY);
        assertEquals(6, game.scenario.getMaxLevel(SHIP_SIZE));
        assertEquals(2, game.scenario.getMaxLevel(MINE_SWEEPER));
    }

    private void assertNotAvailable(Technology technology) {
        assertFalse(game.scenario.getAvailableTechs().contains(technology));
    }

    @Test
    public void buyAttack() {
        assertBuysNextRemaining(ATTACK, 0, 2, 8);
        assertBuysNextRemaining(ATTACK, 1, 2, 10);
        assertBuysNextRemaining(ATTACK, 2, 2, 9);
    }

    @Test
    public void buyDefense() {
        assertBuysNextRemaining(DEFENSE, 0, 4, 8);
        assertBuysNextRemaining(DEFENSE, 1, 4, 10);
        assertBuysNextRemaining(DEFENSE, 2, 4, 9);
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
        roller.mockRoll(2, 1); //TACTICS & MINE SWEEPER (for 15)
        buyTech();
        assertEquals(1, ap.getLevel(TACTICS));

        ap.setLevel(ATTACK, 1);
        ap.setLevel(DEFENSE, 0);
        ap.setLevel(TACTICS, 0);
        sheet.setTechCP(game.scenario.getCost(DEFENSE, 1));
        roller.mockRoll(6, 3);  //ATTACK FIGHTERS AND CLOAK IS NOT BUYABLE
        buyTech();
        assertEquals(1, ap.getLevel(ATTACK));
        assertEquals(1, ap.getLevel(DEFENSE));
        assertEquals(0, ap.getLevel(TACTICS));

        ap.setLevel(ATTACK, 0);
        ap.setLevel(Technology.DEFENSE, 1);
        ap.setLevel(Technology.TACTICS, 0);
        sheet.setTechCP(game.scenario.getCost(ATTACK, 1));
        roller.mockRoll(6, 3);  //DEFENSE FIGHTERS AND CLOAK IS NOT BUYABLE
        buyTech();
        assertEquals(1, ap.getLevel(ATTACK));
        assertEquals(1, ap.getLevel(DEFENSE));
        assertEquals(0, ap.getLevel(TACTICS));
    }

    @Test
    public void cantBuyTacticsIfHasNoAttackAndDefense() {
        TacticsBuyer tacticsBuyer = new TacticsBuyer(game);
        sheet.setTechCP(15);
        ap.setLevel(ATTACK, 0);
        ap.setLevel(DEFENSE, 0);
        assertEquals(false, tacticsBuyer.shouldBeAddedToRollTable(ap));

        ap.setLevel(ATTACK, 2);
        ap.setLevel(DEFENSE, 0);
        assertEquals(false, tacticsBuyer.shouldBeAddedToRollTable(ap));

        ap.setLevel(ATTACK, 0);
        ap.setLevel(DEFENSE, 2);
        assertEquals(false, tacticsBuyer.shouldBeAddedToRollTable(ap));


        ap.setLevel(ATTACK, 2);
        ap.setLevel(DEFENSE, 2);
        assertEquals(true, tacticsBuyer.shouldBeAddedToRollTable(ap));

        ap.setLevel(ATTACK, 1);
        ap.setLevel(Technology.DEFENSE, 0);
        sheet.setTechCP(game.scenario.getCost(DEFENSE, 1));
        assertEquals(true, tacticsBuyer.shouldBeAddedToRollTable(ap));
    }

    @Test
    public void buyTactics() {
        ap.setLevel(ATTACK, 2);
        ap.setLevel(Technology.DEFENSE, 2);
        assertBuysNextRemaining(TACTICS, 0, 1, 2);
        assertBuysNextRemaining(TACTICS, 1, 1, 2);
        assertBuysNextRemaining(TACTICS, 2, 1, 2);
    }

    @Test
    public void buyCloaking() {
        assertBuysNextRemaining(CLOAKING, 0, 6, 10);
        assertBuysNextRemaining(CLOAKING, 1, 6, 10);
    }

    @Test
    public void cantBuyCloakingIfSeenScanner2() {
        game.setSeenLevel(Technology.SCANNER, 2);
        sheet.setTechCP(100);
        assertEquals(false, new CloakingBuyer(game).shouldBeAddedToRollTable(ap));
    }

    @Test
    public void buyScaner() {
        assertBuysNextRemaining(SCANNER, 0, 6, 8); //CLOAK AND FIGHTERS NOT BUYABLE
        assertBuysNextRemaining(SCANNER, 1, 6, 8);
    }

    @Test
    public void buyFighters() {
        assertBuysNextRemaining(FIGHTERS, 0, 7, 9);//CLOAK IS NOT BUYABLE
        assertBuysNextRemaining(FIGHTERS, 1, 7, 9);
        assertBuysNextRemaining(FIGHTERS, 2, 7, 9);
    }

    @Test
    public void buyPointDefense() {
        assertBuysNextRemaining(POINT_DEFENSE, 0, 7, 8);//FIGHTER AND CLOAK IS NOT BUYABLE
        assertBuysNextRemaining(POINT_DEFENSE, 1, 7, 8);
        assertBuysNextRemaining(POINT_DEFENSE, 2, 7, 8);

    }

    @Test
    public void buyMineSweep() {
        assertBuysNextRemaining(MINE_SWEEPER, 0, 1, 1);//NOTHING ELSE IS BUYABLE
        assertBuysNextRemaining(MINE_SWEEPER, 1, 1, 1);
    }

    @Test
    public void dontBuyMineSweepForDefenseFleet() {
        sheet.setTechCP(100);
        assertEquals(true, new MineSweeperBuyer(game).shouldBeAddedToRollTable(ap));
        assertEquals(false, new MineSweeperBuyer(game).shouldBeAddedToRollTable(ap, HOME_DEFENSE));
    }

    private void assertBuysNextRemaining(Technology technology, int currentLevel, int rollNeeded, int rollLimit) {
        ap.setLevel(technology, currentLevel);
        int nextLevel = currentLevel + 1;
        sheet.setTechCP(game.scenario.getCost(technology, nextLevel));
        assertBuys(technology, nextLevel, rollNeeded, rollLimit);
        assertEquals(0, sheet.getTechCP());
    }

    private void assertBuys(Technology technology, int techonogyLevel, int roll, int limit) {
        roller.mockRoll(limit, roll);
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
