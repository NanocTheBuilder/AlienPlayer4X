package com.thilian.se4x.robot.game.scenario4;

import com.thilian.se4x.robot.game.AlienEconomicSheet;
import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.FleetLauncher;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Scenario;
import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.PlayerColor;

public class Scenario4 extends Scenario {

    @Override
    public void init(Game game) {
        techBuyer = new Scenario4TechnologyBuyer(game);
        techPrices = new Scenario4TechnologyPrices();
        fleetBuilder = new FleetBuilder(game);
        defenseBuilder = new DefenseBuilder(game);
        fleetLauncher = new FleetLauncher(game);
    }

    @Override
    public AlienPlayer newPlayer(Game game, Difficulty difficulty, PlayerColor color) {
        return new Scenario4Player(new AlienEconomicSheet(difficulty), game, color);
    }

    public Fleet buildColonyDefense(AlienPlayer alienPlayer) {
        return ((DefenseBuilder)defenseBuilder).buildColonyDefense(alienPlayer);
    }


}
