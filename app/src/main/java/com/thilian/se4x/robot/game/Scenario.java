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

package com.thilian.se4x.robot.game;

import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.PlayerColor;
import com.thilian.se4x.robot.game.enums.Technology;

import java.util.Collection;
import java.util.List;

public abstract class Scenario{

    //TODO: private these
    public TechnologyBuyer techBuyer;
    public TechnologyPrices techPrices;
    public FleetBuilder fleetBuilder;
    public DefenseBuilder defenseBuilder;
    public FleetLauncher fleetLauncher;

    public abstract void init(Game game);

    public abstract AlienPlayer newPlayer(Game game, Difficulty difficulty, PlayerColor color);

    public abstract List<? extends Difficulty> getDifficulties();

    public Collection<Technology> getAvailableTechs() {
        return techPrices.getAvailableTechs();
    }

    public int getStartingLevel(Technology technology) {
        return techPrices.getStartingLevel(technology);
    }

    public void buildFleet(Fleet fleet, FleetBuildOption... options) {
        fleetBuilder.buildFleet(fleet, options);
    }

    public Fleet buildHomeDefense(AlienPlayer alienPlayer) {
        return defenseBuilder.buildHomeDefense(alienPlayer);
    }

    public boolean canBuyNextLevel(AlienPlayer ap, Technology technology){
        return techBuyer.canBuyNextLevel(ap, technology);
    }

    public void buyNextLevel(AlienPlayer alienPlayer, Technology technology) {
        techBuyer.buyNextLevel(alienPlayer, technology);
    }

    public void buyTechs(Fleet fleet, FleetBuildOption... options) {
        techBuyer.buyTechs(fleet, options);
    }

    public int getCost(Technology technology, int level) {
        return techPrices.getCost(technology, level);
    }

    public int getMaxLevel(Technology technology) {
        return techPrices.getMaxLevel(technology);
    }

    public Fleet rollFleetLaunch(AlienPlayer alienPlayer, int turn){
        return fleetLauncher.rollFleetLaunch(alienPlayer, turn);
    }
}
