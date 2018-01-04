package com.thilian.se4x.robot.game.scenario4;

import com.thilian.se4x.robot.game.TechnologyPrices;

import static com.thilian.se4x.robot.game.enums.Technology.ATTACK;
import static com.thilian.se4x.robot.game.enums.Technology.BOARDING;
import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static com.thilian.se4x.robot.game.enums.Technology.DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.FIGHTERS;
import static com.thilian.se4x.robot.game.enums.Technology.GROUND_COMBAT;
import static com.thilian.se4x.robot.game.enums.Technology.MILITARY_ACADEMY;
import static com.thilian.se4x.robot.game.enums.Technology.MINES;
import static com.thilian.se4x.robot.game.enums.Technology.MINE_SWEEPER;
import static com.thilian.se4x.robot.game.enums.Technology.MOVE;
import static com.thilian.se4x.robot.game.enums.Technology.POINT_DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.SCANNER;
import static com.thilian.se4x.robot.game.enums.Technology.SECURITY_FORCES;
import static com.thilian.se4x.robot.game.enums.Technology.SHIP_SIZE;
import static com.thilian.se4x.robot.game.enums.Technology.TACTICS;

/**
 * Created by thili on 2017. 11. 25..
 */

public class Scenario4TechnologyPrices extends TechnologyPrices {

    public Scenario4TechnologyPrices(){
        init(MOVE, 1, 0, 0, 20, 25, 25, 25, 20, 20);
        init(SHIP_SIZE, 1, 0, 0, 10, 15, 20, 20, 20);
        init(MINES,0, 0, 20);

        init(ATTACK,0, 0, 20, 30, 25);
        init(DEFENSE, 0, 0, 20, 30, 25);
        init(TACTICS,0, 0, 15, 15, 15);
        init(CLOAKING, 0, 0, 30, 30);
        init(SCANNER, 0, 0, 20, 20);
        init(FIGHTERS, 0, 0, 25, 25, 25);
        init(POINT_DEFENSE, 0, 0, 20, 20, 20);
        init(MINE_SWEEPER, 0, 0, 10, 15);

        init(BOARDING, 0, 0, 20, 25);
        init(SECURITY_FORCES, 0, 0, 15, 15);
        init(GROUND_COMBAT, 1, 0, 0, 10, 15);
        init(MILITARY_ACADEMY, 0, 0, 10, 20);
    }

}

