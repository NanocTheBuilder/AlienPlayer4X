package com.thilian.se4x.robot.game.scenario4;

import com.thilian.se4x.robot.game.AlienEconomicSheet;
import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.PlayerColor;

public class Scenario4Player extends AlienPlayer {
    public Scenario4Player(AlienEconomicSheet sheet, Game game, PlayerColor color) {
        super(sheet, game, color);
    }

    public Fleet buildColonyDefense() {
        Fleet fleet = ((Scenario4)game.scenario).buildColonyDefense(this);
        economicSheet.spendDefCP(fleet.getBuildCost());
        return fleet;
    }

}
