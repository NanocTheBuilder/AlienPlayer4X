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

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.TechnologyBuyer;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;

import static com.thilian.se4x.robot.game.enums.Technology.ATTACK;
import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static com.thilian.se4x.robot.game.enums.Technology.DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.FIGHTERS;
import static com.thilian.se4x.robot.game.enums.Technology.MINE_SWEEPER;
import static com.thilian.se4x.robot.game.enums.Technology.POINT_DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.SCANNER;
import static com.thilian.se4x.robot.game.enums.Technology.TACTICS;

public class BaseGameTechnologyBuyer extends TechnologyBuyer {

    private int[] SHIP_SIZE_ROLL_TABLE = new int[]{0, 10, 7, 6, 5, 3};

    public BaseGameTechnologyBuyer(Game game) {
        super(game);
    }

    @Override
    protected void initRollTable() {
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
    public void buyOptionalTechs(Fleet fleet, FleetBuildOption... options) {
        AlienPlayer ap = fleet.getAp();
        buyPointDefenseIfNeeded(ap);
        buyMineSweepIfNeeded(ap);
        buyScannerIfNeeded(ap);
        buyShipSizeIfRolled(ap);
        buyFightersIfNeeded(ap);
        buyCloakingIfNeeded(fleet);
    }

}
