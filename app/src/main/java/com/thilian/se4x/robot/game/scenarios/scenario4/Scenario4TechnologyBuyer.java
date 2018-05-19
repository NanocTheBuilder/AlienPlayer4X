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

package com.thilian.se4x.robot.game.scenarios.scenario4;

import static com.thilian.se4x.robot.game.enums.FleetBuildOption.COMBAT_IS_ABOVE_PLANET;
import static com.thilian.se4x.robot.game.enums.Technology.ATTACK;
import static com.thilian.se4x.robot.game.enums.Technology.BOARDING;
import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static com.thilian.se4x.robot.game.enums.Technology.DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.FIGHTERS;
import static com.thilian.se4x.robot.game.enums.Technology.MILITARY_ACADEMY;
import static com.thilian.se4x.robot.game.enums.Technology.MINE_SWEEPER;
import static com.thilian.se4x.robot.game.enums.Technology.POINT_DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.SCANNER;
import static com.thilian.se4x.robot.game.enums.Technology.SECURITY_FORCES;
import static com.thilian.se4x.robot.game.enums.Technology.SHIP_SIZE;
import static com.thilian.se4x.robot.game.enums.Technology.TACTICS;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.TechnologyBuyer;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;

public class Scenario4TechnologyBuyer extends TechnologyBuyer {
    private static int[] SHIP_SIZE_ROLL_TABLE = new int[] { 0, 10, 7, 6, 5, 3, 6 };

    public Scenario4TechnologyBuyer(Game game) {
        super(game);
    }

    @Override
    protected void initRollTable() {
        addToRollTable(SHIP_SIZE, 16);
        addToRollTable(ATTACK, 20);
        addToRollTable(DEFENSE, 20);
        addToRollTable(TACTICS, 12);
        addToRollTable(CLOAKING, 3);
        addToRollTable(SCANNER, 2);
        addToRollTable(FIGHTERS, 8);
        addToRollTable(POINT_DEFENSE, 3);
        addToRollTable(MINE_SWEEPER, 5);
        addToRollTable(SECURITY_FORCES, 3);
        addToRollTable(MILITARY_ACADEMY, 4);
        addToRollTable(BOARDING, 4);
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
        buySecurityIfNeeded(ap);
        buyGroundCombatIfNeeded(ap, FleetBuildOption.isOption(COMBAT_IS_ABOVE_PLANET, options));
        buyMilitaryAcademyIfNeeded(ap);
        buyScannerIfNeeded(ap);
        buyBoardingIfNeeded(ap);
        buyShipSizeIfRolled(ap);
        buyFightersIfNeeded(ap);
        buyCloakingIfNeeded(fleet);
    }

}
