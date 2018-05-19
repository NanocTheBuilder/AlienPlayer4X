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

package com.thilian.se4x.robot.app;

import com.thilian.se4x.robot.app.parser.JsonParser;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.MockRoller;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.PlayerColor;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameDifficulty;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameScenario;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpDifficulties;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpEconomicSheet;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpSoloScenario;

import org.junit.Test;

public class JsonParserTest {
    @Test
    public void testScenario() {
        //There are no asserts in this test. I use it by setting a breakpoint at the end, and check the values in a debugger
        JsonParser parser = new JsonParser();
        Game game = Game.newGame(new BaseGameScenario(), BaseGameDifficulty.GOOD_LUCK, PlayerColor.GREEN, PlayerColor.YELLOW, PlayerColor.RED);
        MockRoller roller = new MockRoller();
        game.roller = roller;
        Fleet fleet = new Fleet(game.aliens.get(0), FleetType.REGULAR_FLEET, 50);
        //((VpEconomicSheet)game.aliens.get(0).getEconomicSheet()).spendBank(50);
        roller.mockRoll(5);
        game.scenario.buildFleet(fleet);
        game = null;
        String json = parser.toJson(game);
        Game actual = parser.fromJson(json);
    }
}
