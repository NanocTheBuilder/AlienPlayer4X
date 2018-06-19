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

package com.thilian.se4x.robot.game.scenarios.scenario4;

import com.thilian.se4x.robot.game.buyers.technology.BoardingBuyer;
import com.thilian.se4x.robot.game.buyers.technology.GroundBuyer;
import com.thilian.se4x.robot.game.buyers.technology.MilitaryAcademyBuyer;
import com.thilian.se4x.robot.game.buyers.technology.SecurityBuyer;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.Seeable;

import org.junit.Test;

public class OptionalTechPurchaseTest extends Scenario4TechnologyBuyerTestBase {

    @Test
    public void buyOptionalSecurity() {
        assertDontBuySecurity(1);
        game.addSeenThing(Seeable.BOARDING_SHIPS);
        assertBuySecurity(1);
        assertDontBuySecurity(2);
    }

    private void assertBuySecurity(int expectedLevel) {
        assertBuyOptional(expectedLevel, new SecurityBuyer(game));
    }

    private void assertDontBuySecurity(int expectedLevel) {
        assertDontBuyOptional(expectedLevel, new SecurityBuyer(game));
    }

    @Test
    public void buyOptionalGroundCombat(){
        assertDontBuyGroundCombat(2);
        assertBuyGroundCombat(2, FleetBuildOption.COMBAT_IS_ABOVE_PLANET);
        assertBuyGroundCombat(3, FleetBuildOption.COMBAT_IS_ABOVE_PLANET);
        assertDontBuyGroundCombat(4, FleetBuildOption.COMBAT_IS_ABOVE_PLANET);
    }

    private void assertBuyGroundCombat(int level, FleetBuildOption... options) {
        assertBuyOptional(level, new GroundBuyer(game), options);
    }

    private void assertDontBuyGroundCombat(int level, FleetBuildOption... options) {
        assertDontBuyOptional(level, new GroundBuyer(game), options);
    }

    @Test
    public void buyOptionalMilitaryAcademy(){
        assertDontBuyMilitaryAcademy(1);

        game.addSeenThing(Seeable.VETERANS);
        roller.mockRoll(7);
        assertDontBuyMilitaryAcademy(1);
        roller.mockRoll(6);
        assertBuyMilitaryAcademy(1);

        roller.mockRoll(7);
        assertDontBuyMilitaryAcademy(2);
        roller.mockRoll(6);
        assertBuyMilitaryAcademy(2);

        roller.mockRoll(6);
        assertDontBuyMilitaryAcademy(3);
    }

    private void assertBuyMilitaryAcademy(int level) {
        assertBuyOptional(level, new MilitaryAcademyBuyer(game));
    }

    private void assertDontBuyMilitaryAcademy(int level) {
        assertDontBuyOptional(level, new MilitaryAcademyBuyer(game));
    }

    @Test
    public void buyOptionalBoarding(){
        assertDontBuyOptionalBoarding(1);
        game.addSeenThing(Seeable.SIZE_3_SHIPS);
        roller.mockRoll(5);
        assertDontBuyOptionalBoarding(1);
        roller.mockRoll(4);
        assertBuyOptionalBoarding(1);

        assertDontBuyOptionalBoarding(2);

    }

    private void assertBuyOptionalBoarding(int level) {
        assertBuyOptional(level, new BoardingBuyer(game));
    }

    private void assertDontBuyOptionalBoarding(int level) {
        assertDontBuyOptional(level, new BoardingBuyer(game));
    }

    @Test
    public void buyOptionalShipSize() {
        roller.mockRoll(10);
        assertBuyShipSize(2);

        assertBuyShipSize(3, 7);
        assertBuyShipSize(4, 6);
        assertBuyShipSize(5, 5);
        assertBuyShipSize(6, 3);
        assertBuyShipSize(7, 6);

        assertDontBuyShipSize(8);
    }

}
