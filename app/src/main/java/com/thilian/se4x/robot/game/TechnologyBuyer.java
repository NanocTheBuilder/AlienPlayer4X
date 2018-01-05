package com.thilian.se4x.robot.game;

import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.Technology;

import static com.thilian.se4x.robot.game.enums.FleetType.DEFENSE_FLEET;
import static com.thilian.se4x.robot.game.enums.FleetType.RAIDER_FLEET;
import static com.thilian.se4x.robot.game.enums.Technology.ATTACK;
import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static com.thilian.se4x.robot.game.enums.Technology.DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.FIGHTERS;
import static com.thilian.se4x.robot.game.enums.Technology.MINE_SWEEPER;
import static com.thilian.se4x.robot.game.enums.Technology.POINT_DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.SCANNER;
import static com.thilian.se4x.robot.game.enums.Technology.SHIP_SIZE;
import static com.thilian.se4x.robot.game.enums.Technology.TACTICS;

/**
 * Created by thili on 2017. 12. 05..
 */

public abstract class TechnologyBuyer {
    protected Game game;

    protected TechnologyBuyer(Game game){
        this.game = game;
    }

    public void buyTechs(Fleet fleet){
        buyOptionalTechs(fleet);
        spendRemainingTechCP(fleet);
    }

    public abstract void buyOptionalTechs(Fleet fleet);

    public abstract void spendRemainingTechCP(Fleet fleet);

    protected abstract int[] getShipSizeRollTable();

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
        if (technology.equals(CLOAKING) && game.getSeenLevel(SCANNER) == game.technologyPrices.getMaxLevel(SCANNER)) {
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

    public void buyCloakingIfNeeded(Fleet fleet) {
        AlienPlayer ap = fleet.getAp();
        if (fleet.getFleetType().equals(RAIDER_FLEET) && ap.getLevel(CLOAKING) != 0) {
            if (game.roller.roll() <= 6)
                buyNextLevel(ap, CLOAKING);
        }
    }

    public void buyFightersIfNeeded(AlienPlayer ap) {
        if (game.getSeenLevel(POINT_DEFENSE) == 0 && ap.getLevel(FIGHTERS) != 0)
            if (game.roller.roll() <= 6)
                buyNextLevel(ap, FIGHTERS);
    }

    public void buyShipSizeIfRolled(AlienPlayer ap) {
        if (ap.getLevel(SHIP_SIZE) < game.technologyPrices.getMaxLevel(SHIP_SIZE))
            if (game.roller.roll() <= getShipSizeRollTable()[ap.getLevel(SHIP_SIZE)])
                buyNextLevel(ap, SHIP_SIZE);
    }

    public void buyScannerIfNeeded(AlienPlayer ap) {
        if (game.getSeenLevel(CLOAKING) > ap.getLevel(SCANNER)) {
            if (game.roller.roll() <= 4) {
                int levelsNeeded = game.getSeenLevel(CLOAKING) - ap.getLevel(SCANNER);
                for (int i = 0; i < levelsNeeded; i++)
                    buyNextLevel(ap, SCANNER);
            }
        }
    }

    public void buyMineSweepIfNeeded(AlienPlayer ap) {
        if (game.isSeenThing(Seeable.MINES) && ap.getLevel(MINE_SWEEPER) == 0) {
            buyNextLevel(ap, MINE_SWEEPER);
        }
    }

    public void buyPointDefenseIfNeeded(AlienPlayer ap) {
        if (game.isSeenThing(Seeable.FIGHTERS) && ap.getLevel(POINT_DEFENSE) == 0) {
            buyNextLevel(ap, POINT_DEFENSE);
        }
    }

    public void buySecurityIfNeeded(AlienPlayer ap) {
        if(game.isSeenThing(Seeable.BOARDING_SHIPS)&& ap.getLevel(Technology.SECURITY_FORCES) == 0)
            buyNextLevel(ap, Technology.SECURITY_FORCES);
    }

    public void buyGroundCombatIfNeeded(AlienPlayer ap, boolean combatIsAbovePlanet) {
        if(combatIsAbovePlanet)
            buyNextLevel(ap, Technology.GROUND_COMBAT);
    }

    public void buyMilitaryAcademyIfNeeded(AlienPlayer ap) {
        if(game.isSeenThing(Seeable.VETERANS))
            if(game.roller.roll() <= 6)
                buyNextLevel(ap, Technology.MILITARY_ACADEMY);
    }

    public void buyBoardingIfNeeded(AlienPlayer ap) {
        if(game.isSeenThing(Seeable.SIZE_3_SHIPS) && ap.getLevel(Technology.BOARDING) == 0)
            if(game.roller.roll() <= 4)
                buyNextLevel(ap, Technology.BOARDING);
    }

}
