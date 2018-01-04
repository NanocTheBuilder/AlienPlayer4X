package com.thilian.se4x.robot.game;

import com.thilian.se4x.robot.game.enums.Technology;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thili on 2017. 12. 06..
 */

public class TechnologyPrices {
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
        return map.get(technology)[level + 1];
    }

    public int getMaxLevel(Technology technology) {
        return map.get(technology).length - 2;
    }

    public Collection<Technology> getAvailableTechs() {
        return availableTechs;
    }
}
