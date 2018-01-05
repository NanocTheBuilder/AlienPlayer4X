package com.thilian.se4x.robot.game.scenario4;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.TechnologyBuyer;
import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.Technology;

/**
 * Created by thili on 2017. 12. 06..
 */

class Scenario4TechnologyBuyer extends TechnologyBuyer {
    private static int[] SHIP_SIZE_ROLL_TABLE = new int[]{0, 10, 7, 6, 5, 3, 6};

    public Scenario4TechnologyBuyer(Game game){
        super(game);
    }


    @Override
    protected int[] getShipSizeRollTable() {
        return SHIP_SIZE_ROLL_TABLE;
    }

    @Override
    public void buyOptionalTechs(Fleet fleet) {

    }

    @Override
    public void spendRemainingTechCP(Fleet fleet) {

    }
}
