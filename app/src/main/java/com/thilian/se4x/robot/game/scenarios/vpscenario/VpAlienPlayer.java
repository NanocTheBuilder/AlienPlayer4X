package com.thilian.se4x.robot.game.scenarios.vpscenario;

import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.PlayerColor;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4Player;

public class VpAlienPlayer extends Scenario4Player {

    private int colonies = 0;
    
    public VpAlienPlayer(VpEconomicSheet sheet, Game game, PlayerColor color) {
        super(sheet, game, color);
    }

    public int getColonies() {
        return colonies;
    }

    public void setColonies(int colonies) {
        this.colonies = colonies;
    }

    @Override
    protected int getExtraEconRoll(int turn) {
        return colonies;
    }
}
