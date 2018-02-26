package com.thilian.se4x.robot.game.basegame;

import static com.thilian.se4x.robot.game.enums.Technology.ATTACK;
import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static com.thilian.se4x.robot.game.enums.Technology.DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.FIGHTERS;
import static com.thilian.se4x.robot.game.enums.Technology.MINES;
import static com.thilian.se4x.robot.game.enums.Technology.MINE_SWEEPER;
import static com.thilian.se4x.robot.game.enums.Technology.MOVE;
import static com.thilian.se4x.robot.game.enums.Technology.POINT_DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.SCANNER;
import static com.thilian.se4x.robot.game.enums.Technology.SHIP_SIZE;
import static com.thilian.se4x.robot.game.enums.Technology.TACTICS;

import com.thilian.se4x.robot.game.TechnologyPrices;

/**
 * Created by thili on 2017. 11. 25..
 */

public class BaseGameTechnologyPrices extends TechnologyPrices {

    public BaseGameTechnologyPrices(){
        init(MOVE, 1, 0, 20, 25, 25, 25, 20, 20);
        init(SHIP_SIZE, 1, 0, 10, 15, 20, 20, 20);
        init(MINES,0, 20);

        init(ATTACK,0, 20, 30, 25);
        init(DEFENSE, 0, 20, 30, 25);
        init(TACTICS,0, 15, 15, 15);
        init(CLOAKING, 0, 30, 30);
        init(SCANNER, 0, 20, 20);
        init(FIGHTERS, 0, 25, 25, 25);
        init(POINT_DEFENSE, 0, 20, 20, 20);
        init(MINE_SWEEPER, 0, 10, 15);
    }

}

