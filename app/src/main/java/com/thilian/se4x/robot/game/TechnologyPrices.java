package com.thilian.se4x.robot.game;

import com.thilian.se4x.robot.game.enums.Technology;

import java.util.HashMap;
import java.util.Map;

import static com.thilian.se4x.robot.game.enums.Technology.*;

/**
 * Created by thili on 2017. 11. 25..
 */

public class TechnologyPrices {
    private Map<Technology, int[]> map = new HashMap<>();

    public TechnologyPrices(){
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
        init(MINE_SWEEP, 0, 0, 10, 15);

    }

    private void init(Technology technology, int... ints) {
        map.put(technology, ints);
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
}

