package com.thilian.se4x.robot.game.basegame;

import com.thilian.se4x.robot.game.AlienEconomicSheet;
import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.DefenseBuilder;
import com.thilian.se4x.robot.game.FleetBuilder;
import com.thilian.se4x.robot.game.FleetLauncher;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Scenario;
import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.PlayerColor;

public class BaseGameScenario extends Scenario {

    @Override
    public void init(Game game) {
        techBuyer = new BaseGameTechnologyBuyer(game);
        techPrices = new BaseGameTechnologyPrices();
        fleetBuilder = new FleetBuilder(game);
        defenseBuilder = new DefenseBuilder(game);
        fleetLauncher = new FleetLauncher(game);
    }

    @Override
    public AlienPlayer newPlayer(Game game, Difficulty difficulty, PlayerColor color) {
        return new AlienPlayer(new AlienEconomicSheet(difficulty), game, color);
    }

}
