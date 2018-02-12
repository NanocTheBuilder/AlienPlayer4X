package com.thilian.se4x.robot.game.basegame;

import static com.thilian.se4x.robot.game.enums.Technology.ATTACK;
import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static com.thilian.se4x.robot.game.enums.Technology.DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.FIGHTERS;
import static com.thilian.se4x.robot.game.enums.Technology.MINE_SWEEPER;
import static com.thilian.se4x.robot.game.enums.Technology.POINT_DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.SCANNER;
import static com.thilian.se4x.robot.game.enums.Technology.TACTICS;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.TechnologyBuyer;

public class BaseGameTechnologyBuyer extends TechnologyBuyer {

    private int[] SHIP_SIZE_ROLL_TABLE = new int[]{0, 10, 7, 6, 5, 3};

    public BaseGameTechnologyBuyer(Game game) {
    	super(game);
    }
    
    @Override
    protected void initRollTable()    {
        addToRollTable(ATTACK, 2);
        addToRollTable(DEFENSE, 2);
        addToRollTable(TACTICS, 1);
        addToRollTable(CLOAKING, 1);
        addToRollTable(SCANNER, 1);
        addToRollTable(FIGHTERS, 1);
        addToRollTable(POINT_DEFENSE, 1);
        addToRollTable(MINE_SWEEPER, 1);
    }

    @Override
    protected int[] getShipSizeRollTable() {
        return SHIP_SIZE_ROLL_TABLE;
    }

    @Override
    public void buyOptionalTechs(Fleet fleet) {
        AlienPlayer ap = fleet.getAp();
        buyPointDefenseIfNeeded(ap);
        buyMineSweepIfNeeded(ap);
        buyScannerIfNeeded(ap);
        buyShipSizeIfRolled(ap);
        buyFightersIfNeeded(ap);
        buyCloakingIfNeeded(fleet);
    }

}
