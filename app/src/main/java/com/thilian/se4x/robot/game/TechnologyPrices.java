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

import com.thilian.se4x.robot.game.enums.Technology;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class TechnologyPrices {
    private Collection<Technology> availableTechs = new ArrayList<>();
    private Map<Technology, int[]> map = new HashMap<>();

    protected void init(Technology technology, int... ints) {
        map.put(technology, ints);
        availableTechs.add(technology);
    }

    public int getStartingLevel(Technology technology) {
        return map.get(technology)[0];
    }

    public int getCost(Technology technology, int level) {
        return map.get(technology)[level];
    }

    public int getMaxLevel(Technology technology) {
        return map.get(technology).length - 1;
    }

    public Collection<Technology> getAvailableTechs() {
        return availableTechs;
    }
}
