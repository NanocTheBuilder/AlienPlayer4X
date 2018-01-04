package com.thilian.se4x.robot.game.scenario4;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.TechnologyBuyer;
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
        if(game.seenLevels.get(Technology.BOARDING) != 0 && ap.getLevel(Technology.SECURITY_FORCES) == 0)
            buyNextLevel(ap, Technology.SECURITY_FORCES);
    }

    public void buyGroundCombatIfNeeded(AlienPlayer ap, boolean combatIsAbovePlanet) {
        if(combatIsAbovePlanet)
            buyNextLevel(ap, Technology.GROUND_COMBAT);
    }

    public void buyMilitaryAcademyIfNeeded() {
    }
}
