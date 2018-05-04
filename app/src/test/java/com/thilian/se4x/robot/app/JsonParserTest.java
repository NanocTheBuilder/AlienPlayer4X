package com.thilian.se4x.robot.app;

import com.thilian.se4x.robot.app.parser.JsonParser;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.MockRoller;
import com.thilian.se4x.robot.game.enums.FleetType;
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
        Game game = new Game();
        MockRoller roller = new MockRoller();
        game.roller = roller;
        game.createGame(new BaseGameScenario(), BaseGameDifficulty.GOOD_LUCK);
        Fleet fleet = new Fleet(game.aliens.get(0), FleetType.REGULAR_FLEET, 50);
        //((VpEconomicSheet)game.aliens.get(0).getEconomicSheet()).spendBank(50);
        roller.mockRoll(5);
        game.scenario.buildFleet(fleet);
        String json = parser.toJson(game);
        Game actual = parser.fromJson(json);
    }
}
