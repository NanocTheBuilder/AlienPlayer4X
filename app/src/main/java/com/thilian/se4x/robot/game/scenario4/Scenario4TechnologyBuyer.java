package com.thilian.se4x.robot.game.scenario4;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.TechnologyBuyer;
import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.Technology;

/**
 * Created by thili on 2017. 12. 06..
 */

class Scenario4TechnologyBuyer extends TechnologyBuyer {
    public Scenario4TechnologyBuyer(Game game){
        super(game);
    }

    @Override
    public void buyTechs(Fleet fleet) {

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
