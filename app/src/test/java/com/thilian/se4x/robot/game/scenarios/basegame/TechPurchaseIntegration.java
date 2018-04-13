package com.thilian.se4x.robot.game.scenarios.basegame;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.thilian.se4x.robot.game.AlienEconomicSheet;
import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.DefenseBuilder;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.FleetBuilder;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Scenario;
import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.PlayerColor;
import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.Technology;

public class TechPurchaseIntegration extends BasegameTechnologyBuyerTestBase {

    @Override
    protected void buildFleet() {
        fleet = new Fleet(ap, FleetType.REGULAR_FLEET, -1);
    }

    @Test
    public void integration() {

        sheet.setTechCP(120);
        ap.setLevel(Technology.SHIP_SIZE, 3);
        game.addSeenThing(Seeable.MINES);
        ap.setLevel(Technology.FIGHTERS, 1);
        ap.setLevel(Technology.ATTACK, 2);
        ap.setLevel(Technology.DEFENSE, 1);
        roller.mockRoll(10); // no ship shize
        roller.mockRoll(3); // buys fighter
        roller.mockRoll(5);
        roller.mockRoll(5);
        roller.mockRoll(6);
        ap.buildFleet(fleet);
        assertLevel(Technology.FIGHTERS, 2);
        assertLevel(Technology.DEFENSE, 2);
        assertLevel(Technology.TACTICS, 1);
        assertLevel(Technology.CLOAKING, 1);
        assertEquals(10, sheet.getTechCP());
    }

    @Override
    protected void createGame(){
        game.createGame(BaseGameDifficulty.NORMAL, new Scenario(){
            @Override
            public void init(Game game) {
                defenseBuilder = new DefenseBuilder(game);
                techBuyer = new BaseGameTechnologyBuyer(game);
                techPrices = new BaseGameTechnologyPrices();
                fleetBuilder = new FleetBuilder(game) {
                    @Override
                    public void buildFleet(Fleet fleet) {
                    }
                };
            }

            @Override
            public AlienPlayer newPlayer(Game game, Difficulty difficulty, PlayerColor color) {
                return new AlienPlayer(new AlienEconomicSheet(difficulty), game, color);
            }

            @Override
            public Difficulty[] getDifficulties() {
                return new Difficulty[0];
            }
        });
    }
}
