package com.thilian.se4x.robot.game.basegame;

import com.thilian.se4x.robot.game.DefenseBuilder;
import com.thilian.se4x.robot.game.FleetBuilder;
import com.thilian.se4x.robot.game.FleetLauncher;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Scenario;

public class BaseGameScenario extends Scenario {

    public BaseGameScenario(Game game) {
        techBuyer = new BaseGameTechnologyBuyer(game);
        techPrices = new BaseGameTechnologyPrices();
        fleetBuilder = new FleetBuilder(game);
        defenseBuilder = new DefenseBuilder(game);
        fleetLauncher = new FleetLauncher(game);
    }


}
