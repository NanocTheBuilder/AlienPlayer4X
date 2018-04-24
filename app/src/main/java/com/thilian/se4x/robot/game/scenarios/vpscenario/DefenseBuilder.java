package com.thilian.se4x.robot.game.scenarios.vpscenario;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Game;

public class DefenseBuilder extends com.thilian.se4x.robot.game.scenarios.scenario4.DefenseBuilder {
    public DefenseBuilder(Game game) {
        super(game);
    }

    @Override
    protected int getDefCp(AlienPlayer ap) {
        VpEconomicSheet sheet = (VpEconomicSheet) ap.getEconomicSheet();
        return sheet.getDefCP() + sheet.getBank();
    }
}
