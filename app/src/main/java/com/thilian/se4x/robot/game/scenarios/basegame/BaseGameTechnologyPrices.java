/*
 * Copyright (C) 2018 Balázs Péter
 *
 * This file is part of Alien Player 4X.
 *
 * Alien Player 4X is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alien Player 4X is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Alien Player 4X.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.thilian.se4x.robot.game.scenarios.basegame;

import com.thilian.se4x.robot.game.TechnologyPrices;

import static com.thilian.se4x.robot.game.enums.Technology.ATTACK;
import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static com.thilian.se4x.robot.game.enums.Technology.DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.FIGHTERS;
import static com.thilian.se4x.robot.game.enums.Technology.MINE_SWEEPER;
import static com.thilian.se4x.robot.game.enums.Technology.MOVE;
import static com.thilian.se4x.robot.game.enums.Technology.POINT_DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.SCANNER;
import static com.thilian.se4x.robot.game.enums.Technology.SHIP_SIZE;
import static com.thilian.se4x.robot.game.enums.Technology.TACTICS;

public class BaseGameTechnologyPrices extends TechnologyPrices {

    public BaseGameTechnologyPrices() {
        init(MOVE, 1, 0, 20, 25, 25, 25, 20, 20);
        init(SHIP_SIZE, 1, 0, 10, 15, 20, 20, 20);
        //init(MINES,0, 20);

        init(ATTACK, 0, 20, 30, 25);
        init(DEFENSE, 0, 20, 30, 25);
        init(TACTICS, 0, 15, 15, 15);
        init(CLOAKING, 0, 30, 30);
        init(SCANNER, 0, 20, 20);
        init(FIGHTERS, 0, 25, 25, 25);
        init(POINT_DEFENSE, 0, 20, 20, 20);
        init(MINE_SWEEPER, 0, 10, 15);
    }

}

