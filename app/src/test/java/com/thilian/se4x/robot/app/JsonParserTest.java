package com.thilian.se4x.robot.app;

import com.thilian.se4x.robot.app.parser.JsonParser;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.MockRoller;
import com.thilian.se4x.robot.game.Scenario;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.Scenarios;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameDifficulty;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameScenario;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpDifficulties;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpEconomicSheet;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpSoloScenario;

import org.json.JSONException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonParserTest {
    @Test
    public void testScenario() throws JSONException {
        JsonParser parser = new JsonParser();
        Game game = new Game();
        MockRoller roller = new MockRoller();
        game.roller = roller;
        game.createGame(new VpSoloScenario(), VpDifficulties.VpSoloDifficulty.NORMAL);
        Fleet fleet = new Fleet(game.aliens.get(0), FleetType.EXPANSION_FLEET, 50);
        ((VpEconomicSheet)game.aliens.get(0).getEconomicSheet()).spendBank(50);
        roller.mockRoll(5);
        game.scenario.buildFleet(fleet);
        String json = parser.toJson(game);
        //assertEquals("alma", json);
        Game actual = parser.fromJson(json);
        //assertEquals(1, actual);
    }
}
