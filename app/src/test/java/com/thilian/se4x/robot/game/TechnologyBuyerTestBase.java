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

package com.thilian.se4x.robot.game;

import com.thilian.se4x.robot.game.buyers.TechBuyer;
import com.thilian.se4x.robot.game.buyers.technology.ShipSizeBuyer;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.Technology;

import org.junit.Before;

import static org.junit.Assert.assertEquals;

public abstract class TechnologyBuyerTestBase extends Fixture{


    protected Fleet fleet;

    @Before
    public void setUp() {
        for (Technology t : game.scenario.getAvailableTechs()) {
            assertLevel(t, game.scenario.getStartingLevel(t));
        }
        buildFleet();
    }

    protected void buildFleet() {
        fleet = new Fleet(ap, FleetType.REGULAR_FLEET, -1);
    }

    protected void assertLevel(Technology technology, int expectedLevel) {
        assertEquals(expectedLevel, ap.getLevel(technology));
    }

    protected void assertBuyShipSize(int newLevel, int rollNeeded) {
        roller.mockRoll(rollNeeded + 1);
        assertDontBuyShipSize(newLevel);
        roller.mockRoll(rollNeeded);
        assertBuyShipSize(newLevel);
    }

    protected void assertBuyShipSize(int expectedLevel) {
        assertBuyOptional(expectedLevel, new ShipSizeBuyer(game));
    }

    protected void assertDontBuyShipSize(int expectedLevel) {
        assertDontBuyOptional(expectedLevel, new ShipSizeBuyer(game));
    }

    protected void assertBuyOptional(int expectedLevel, TechBuyer techBuyer, FleetBuildOption... options) {
        int expectedRemainingCP= 100 - game.scenario.getCost(techBuyer.getTechnology(), expectedLevel);
        assertOptionalBuy(expectedLevel, expectedRemainingCP, techBuyer, options);
    }

    protected void assertDontBuyOptional(int expectedLevel, TechBuyer techBuyer, FleetBuildOption... options) {
        assertOptionalBuy(expectedLevel - 1, 100, techBuyer, options);
    }

    protected void assertOptionalBuy(int newLevel, int expectedRemainingCP, TechBuyer techBuyer, FleetBuildOption... options) {
        sheet.setTechCP(100);
        techBuyer.buyTechIfNeeded(fleet, options);
        assertLevel(techBuyer.getTechnology(), newLevel);
        assertEquals(expectedRemainingCP, sheet.getTechCP());
    }

    protected abstract class BuyAction {
        protected abstract void buy();
    }
}