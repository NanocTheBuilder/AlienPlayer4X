package com.thilian.se4x.robot.game.scenarios.scenario4;

import com.thilian.se4x.robot.game.AlienEconomicSheet;
import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.PlayerColor;
import com.thilian.se4x.robot.game.enums.ShipType;

import java.util.ArrayList;
import java.util.List;

public class Scenario4Player extends AlienPlayer {
    public Scenario4Player(AlienEconomicSheet sheet, Game game, PlayerColor color) {
        super(sheet, game, color);
    }

    public List<Fleet> buildColonyDefense() {
        List<Fleet> newFleets = new ArrayList<>();
        if (economicSheet.getDefCP() >= ShipType.MINE.getCost()) {
            Fleet fleet = ((Scenario4) game.scenario).buildColonyDefense(this);
            economicSheet.spendDefCP(fleet.getBuildCost());
            newFleets.add(fleet);
        }
        return newFleets;
    }

}
