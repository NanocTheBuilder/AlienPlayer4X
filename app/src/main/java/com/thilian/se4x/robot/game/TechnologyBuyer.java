package com.thilian.se4x.robot.game;

import com.thilian.se4x.robot.game.enums.Technology;

import static com.thilian.se4x.robot.game.enums.FleetType.DEFENSE_FLEET;
import static com.thilian.se4x.robot.game.enums.Technology.ATTACK;
import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static com.thilian.se4x.robot.game.enums.Technology.DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.MINE_SWEEPER;
import static com.thilian.se4x.robot.game.enums.Technology.SCANNER;
import static com.thilian.se4x.robot.game.enums.Technology.TACTICS;

/**
 * Created by thili on 2017. 12. 05..
 */

public abstract class TechnologyBuyer {
    protected Game game;

    protected TechnologyBuyer(Game game){
        this.game = game;
    }

    public abstract  void buyTechs(Fleet fleet);

     public void buyNextLevel(AlienPlayer ap, Technology technology) {
        int currentLevel = ap.getLevel(technology);
        if (canBuyNextLevel(ap, technology)) {
            int nextLevel = currentLevel + 1;
            int cost = game.technologyPrices.getCost(technology, nextLevel);
            ap.setLevel(technology, nextLevel);
            ap.getEconomicSheet().spendTechCP(cost);
        }
    }

    public boolean canBuyNextLevel(AlienPlayer ap, Technology technology) {
        int currentLevel = ap.getLevel(technology);
        if (technology.equals(TACTICS)) {
            int min = Math.min(ap.getLevel(ATTACK), ap.getLevel(DEFENSE));
            if (min < 2) {
                return canBuyNextLevel(ap, ATTACK) | canBuyNextLevel(ap, DEFENSE);
            }
        }
        if (technology.equals(CLOAKING) && ap.getSeenLevel(SCANNER) == game.technologyPrices.getMaxLevel(SCANNER)) {
            return false;
        }

        return currentLevel < game.technologyPrices.getMaxLevel(technology)
                && ap.getEconomicSheet().getTechCP() >= game.technologyPrices.getCost(technology, currentLevel + 1);
    }

    public boolean canBuyNextLevel(Fleet fleet, Technology technology) {
        if (fleet.getFleetType().equals(DEFENSE_FLEET) && technology.equals(MINE_SWEEPER))
            return false;
        else
            return canBuyNextLevel(fleet.getAp(), technology);
    }
}
