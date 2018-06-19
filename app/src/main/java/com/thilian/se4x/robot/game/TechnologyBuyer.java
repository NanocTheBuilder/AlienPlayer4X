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

import com.thilian.se4x.robot.game.buyers.TechBuyer;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.Technology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class TechnologyBuyer {
    protected Game game;

    private Map<Technology, Integer> TECHNOLOGY_ROLL_TABLE = new TreeMap<>();
    private List<TechBuyer> techBuyerList = new ArrayList<>();
    private Map<Technology, TechBuyer> techBuyerMap = new HashMap<>();

    protected TechnologyBuyer(Game game) {
        this.game = game;
        initTechBuyers();
    }

    public void buyTechs(Fleet fleet, FleetBuildOption... options) {
        buyOptionalTechs(fleet, options);
        spendRemainingTechCP(fleet, options);
    }

    protected abstract void initTechBuyers();

    protected void addTechBuyer(Technology technology, TechBuyer techBuyer, Integer frequency) {
        techBuyerList.add(techBuyer);
        techBuyerMap.put(technology, techBuyer);
        TECHNOLOGY_ROLL_TABLE.put(technology, frequency);
    }

    public void buyOptionalTechs(Fleet fleet, FleetBuildOption... options) {
        for(TechBuyer techBuyer : techBuyerList){
            techBuyer.buyTechIfNeeded(fleet, options);
        }
    }

    public void spendRemainingTechCP(Fleet fleet, FleetBuildOption... options) {
        AlienPlayer ap = fleet.getAp();
        while (true) {
            List<Technology> buyable = findBuyableTechs(fleet, options);
            if (buyable.isEmpty())
                break;
            int roll = game.roller.roll(buyable.size());
            buyNextLevel(ap, buyable.get(roll - 1));
        }
    }

    public void buyNextLevel(AlienPlayer ap, Technology technology) {
        techBuyerMap.get(technology).buyNextLevel(ap);
    }

    private List<Technology> findBuyableTechs(Fleet fleet, FleetBuildOption... options) {
        List<Technology> buyables = new ArrayList<>();
        for (Technology technology : TECHNOLOGY_ROLL_TABLE.keySet()) {
            if(techBuyerMap.get(technology).shouldBeAddedToRollTable(fleet.getAp(), options))
                    for (int i = 0; i < TECHNOLOGY_ROLL_TABLE.get(technology); i++)
                        buyables.add(technology);
        }
        return buyables;
    }

    public boolean canBuyNextLevel(AlienPlayer ap, Technology technology) {
        return techBuyerMap.get(technology).canBuyNextLevel(ap);
    }
}
