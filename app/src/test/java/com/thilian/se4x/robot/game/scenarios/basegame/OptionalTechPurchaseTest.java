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
import com.thilian.se4x.robot.game.buyers.technology.FightersBuyer;
import com.thilian.se4x.robot.game.buyers.technology.MineSweeperBuyer;
import com.thilian.se4x.robot.game.buyers.technology.PointDefenseBuyer;
import com.thilian.se4x.robot.game.buyers.technology.ScannerBuyer;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.Technology;

import org.junit.Test;

import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static com.thilian.se4x.robot.game.enums.Technology.POINT_DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.SHIP_SIZE;
import static org.junit.Assert.assertEquals;

public class OptionalTechPurchaseTest extends BasegameTechnologyBuyerTestBase {

    @Test
    public void buyOptionalPontDefense() {
        assertDontBuyPD(1);
        game.addSeenThing(Seeable.FIGHTERS);
        assertBuyPD(1);
        assertDontBuyPD(2);
    }

    private void assertBuyPD(int expectedLevel) {
        assertBuyOptional(expectedLevel, new PointDefenseBuyer(game));
    }

    private void assertDontBuyPD(int expectedLevel) {
        assertDontBuyOptional(expectedLevel, new PointDefenseBuyer(game));
    }

    @Test
    public void buyOptionalMineSweep() {
        assertDontBuyMS(1);
        game.addSeenThing(Seeable.MINES);
        assertBuyMS(1);
        assertDontBuyMS(2);
    }

    private void assertBuyMS(int expectedLevel) {
        assertBuyOptional(expectedLevel, new MineSweeperBuyer(game));
    }

    private void assertDontBuyMS(int expectedLevel) {
        assertDontBuyOptional(expectedLevel, new MineSweeperBuyer(game));
    }

    @Test
    public void buyOptionalScan() {
        game.setSeenLevel(CLOAKING, 1);
        roller.mockRoll(4);
        assertBuyScanner(1);
        assertDontBuyScanner(2);

        game.setSeenLevel(CLOAKING, 2);
        roller.mockRoll(5);
        assertDontBuyScanner(2);

        roller.mockRoll(4);
        assertBuyScanner(2);

        game.setSeenLevel(CLOAKING, 2);
        ap.setLevel(Technology.SCANNER, 0);
        roller.mockRoll(4);
        assertOptionalBuy(
                2,
                60,
                new ScannerBuyer(game));
    }

    private void assertBuyScanner(int expectedLevel) {
        assertBuyOptional(expectedLevel, new ScannerBuyer(game));
    }

    private void assertDontBuyScanner(int expectedLevel) {
        assertDontBuyOptional(expectedLevel, new ScannerBuyer(game));
    }

    @Test
    public void buyOptionalShipSize() {
        roller.mockRoll(10);
        assertBuyShipSize(2);

        assertBuyShipSize(3, 7);
        assertBuyShipSize(4, 6);
        assertBuyShipSize(5, 5);
        assertBuyShipSize(6, 3);

        assertDontBuyShipSize(7);
    }

    @Test
    public void buyOptionalFighterLevel() {
        game.setSeenLevel(Technology.POINT_DEFENSE, 0);
        assertDontBuyFighters(1);

        ap.setLevel(Technology.FIGHTERS, 1);
        roller.mockRoll(6);
        assertBuyFighters(2);

        game.setSeenLevel(Technology.POINT_DEFENSE, 1);
        assertDontBuyFighters(3);

        game.setSeenLevel(Technology.POINT_DEFENSE, 0);
        roller.mockRoll(7);
        assertDontBuyFighters(3);

        roller.mockRoll(6);
        assertBuyFighters(3);
    }

    private void assertBuyFighters(int expectedLevel) {
        assertBuyOptional(expectedLevel, new FightersBuyer(game));
    }

    private void assertDontBuyFighters(int expectedLevel) {
        assertDontBuyOptional(expectedLevel, new FightersBuyer(game));
    }

    @Test
    public void buyOptionalCloak() {
        fleet.setFleetType(FleetType.RAIDER_FLEET);

        ap.setLevel(CLOAKING, 1);
        roller.mockRoll(7);
        assertDontBuyCloaking(2);

        roller.mockRoll(6);
        assertBuyCloaking(2);

        ap.setLevel(CLOAKING, 1);
        fleet.setFleetType(FleetType.REGULAR_FLEET);
        assertDontBuyCloaking(2);
    }

    private void assertBuyCloaking(int expectedLevel) {
        sheet.setTechCP(100);
        new CloakingBuyer(game).buyTechIfNeeded(fleet);
        assertLevel(CLOAKING, expectedLevel);
        assertEquals(100 - game.scenario.getCost(CLOAKING, expectedLevel), sheet.getTechCP());
    }

    private void assertDontBuyCloaking(int expectedLevel) {
        sheet.setTechCP(100);
        new CloakingBuyer(game).buyTechIfNeeded(fleet);
        assertLevel(CLOAKING, expectedLevel - 1);
        assertEquals(100, sheet.getTechCP());
    }


    @Test
    public void shouldBuyPDBeforeCloak(){
        game.addSeenThing(Seeable.FIGHTERS);
        fleet.setFleetType(FleetType.RAIDER_FLEET);
        setLevel(SHIP_SIZE, 2); //ignore ship size
        roller.mockRoll(10); //ignore ship size
        sheet.setTechCP(30);
        ap.setLevel(CLOAKING, 1);
        roller.mockRoll(1); //cloak is needed
        techBuyer.buyOptionalTechs(fleet);
        assertLevel(POINT_DEFENSE, 1);
        assertLevel(CLOAKING, 1);
    }
}
