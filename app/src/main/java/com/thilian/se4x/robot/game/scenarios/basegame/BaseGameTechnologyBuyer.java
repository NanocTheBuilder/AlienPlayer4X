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

import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.TechnologyBuyer;
import com.thilian.se4x.robot.game.buyers.technology.AttackBuyer;
import com.thilian.se4x.robot.game.buyers.technology.CloakingBuyer;
import com.thilian.se4x.robot.game.buyers.technology.DefenseBuyer;
import com.thilian.se4x.robot.game.buyers.technology.FightersBuyer;
import com.thilian.se4x.robot.game.buyers.technology.MineSweeperBuyer;
import com.thilian.se4x.robot.game.buyers.technology.MoveBuyer;
import com.thilian.se4x.robot.game.buyers.technology.PointDefenseBuyer;
import com.thilian.se4x.robot.game.buyers.technology.ScannerBuyer;
import com.thilian.se4x.robot.game.buyers.technology.ShipSizeBuyer;
import com.thilian.se4x.robot.game.buyers.technology.TacticsBuyer;

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

public class BaseGameTechnologyBuyer extends TechnologyBuyer {

    public BaseGameTechnologyBuyer(Game game) {
        super(game);
    }

    @Override
    protected void initTechBuyers() {
        addTechBuyer(MOVE, new MoveBuyer(game), 0);
        addTechBuyer(ATTACK, new AttackBuyer(game), 2);
        addTechBuyer(DEFENSE, new DefenseBuyer(game), 2);
        addTechBuyer(TACTICS, new TacticsBuyer(game),1);

        addTechBuyer(POINT_DEFENSE, new PointDefenseBuyer(game), 1);
        addTechBuyer(MINE_SWEEPER, new MineSweeperBuyer(game), 1);
        addTechBuyer(SCANNER, new ScannerBuyer(game),1);
        addTechBuyer(SHIP_SIZE, new ShipSizeBuyer(game), 0);
        addTechBuyer(FIGHTERS, new FightersBuyer(game), 1);
        addTechBuyer(CLOAKING, new CloakingBuyer(game), 1);
    }

}
