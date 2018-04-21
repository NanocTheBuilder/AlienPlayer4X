package com.thilian.se4x.robot.game.scenarios.scenario4;

import java.util.ArrayList;
import java.util.List;

import com.thilian.se4x.robot.game.AlienEconomicSheet;
import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.PlayerColor;

public class Scenario4Player extends AlienPlayer {
    public Scenario4Player(AlienEconomicSheet sheet, Game game, PlayerColor color) {
        super(sheet, game, color);
    }

    public List<Fleet> buildColonyDefense() {
        List<Fleet> newFleets = new ArrayList<>();
        Fleet fleet = ((Scenario4) game.scenario).buildColonyDefense(this);
        if(!fleet.getGroups().isEmpty()){
            economicSheet.spendDefCP(fleet.getBuildCost());
            fleet.setFirstCombat(true);
            newFleets.add(fleet);
        }
        return newFleets;
    }

}
