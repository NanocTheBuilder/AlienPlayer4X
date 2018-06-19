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

package com.thilian.se4x.robot.game.buyers;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.Technology;

public abstract class TechBuyer{
    protected Game game;
    private Technology technology;

    public TechBuyer(Game game, Technology technology){
        this.game = game;
        this.technology = technology;
    }

    public void buyTechIfNeeded(Fleet fleet, FleetBuildOption... options) {
        if(isNeeded(fleet, options))
            buyNextLevel(fleet.getAp());
    }

    public boolean shouldBeAddedToRollTable(AlienPlayer ap, FleetBuildOption... options){
        return !mustReRoll(ap, options) && canBuyNextLevel(ap);
    }

    public void buyNextLevel(AlienPlayer ap) {
        int currentLevel = ap.getLevel(technology);
        if (canBuyNextLevel(ap)) {
            int nextLevel = currentLevel + 1;
            int cost = game.scenario.getCost(technology, nextLevel);
            ap.setLevel(technology, nextLevel);
            ap.getEconomicSheet().spendTechCP(cost);
        }
    }

    protected boolean isNeeded(Fleet fleet, FleetBuildOption... options){
        return false;
    }

    protected boolean mustReRoll(AlienPlayer ap, FleetBuildOption... options){
        return false;
    }

    public boolean canBuyNextLevel(AlienPlayer ap) {
        int currentLevel = ap.getLevel(technology);
        return currentLevel < game.scenario.getMaxLevel(technology)
                && ap.getEconomicSheet().getTechCP() >= game.scenario.getCost(technology, currentLevel + 1);
    }

    public Technology getTechnology() {
        return technology;
    }
}
