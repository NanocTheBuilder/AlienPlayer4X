package com.thilian.se4x.robot.game.scenarios.scenario4;

import com.thilian.se4x.robot.game.AlienEconomicSheet;
import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.FleetBuildResult;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.PlayerColor;

public class Scenario4Player extends AlienPlayer {
    public Scenario4Player(AlienEconomicSheet sheet, Game game, PlayerColor color) {
        super(sheet, game, color);
    }

    public FleetBuildResult buildColonyDefense() {
        FleetBuildResult result = new FleetBuildResult(this);
        Fleet fleet = ((Scenario4) game.scenario).buildColonyDefense(this);
        if(fleet != null){
            economicSheet.spendDefCP(fleet.getBuildCost());
            fleet.setFirstCombat(true);
            result.addNewFleet(fleet);
        }
        return result;
    }

}
