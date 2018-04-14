package com.thilian.se4x.robot.game.scenarios.vpscenario;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.FleetLauncher;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.PlayerColor;
import com.thilian.se4x.robot.game.scenarios.scenario4.DefenseBuilder;
import com.thilian.se4x.robot.game.scenarios.scenario4.FleetBuilder;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4TechnologyBuyer;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4TechnologyPrices;

public class VpScenario extends Scenario4 {
    @Override
    public void init(Game game) {
        techBuyer = new Scenario4TechnologyBuyer(game);
        techPrices = new Scenario4TechnologyPrices();
        fleetBuilder = new FleetBuilder(game);
        defenseBuilder = new DefenseBuilder(game);
        fleetLauncher = new VpFleetLauncher(game);

    }

    @Override
    public AlienPlayer newPlayer(Game game, Difficulty difficulty, PlayerColor color) {
        return new VpAlienPlayer(new VpEconomicSheet((VpDifficulty) difficulty), game, color);
    }

    @Override
    public Difficulty[] getDifficulties() {
        return VpDifficulty.values();
    }
}
