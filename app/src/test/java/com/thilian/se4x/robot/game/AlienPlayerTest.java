package com.thilian.se4x.robot.game;

import org.junit.Assert;
import org.junit.Test;

import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameDifficulty;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameScenario;


public class AlienPlayerTest {
    @Test
    public void testFleetNames(){
        Game game = new Game();
        game.createGame(BaseGameDifficulty.NORMAL, new BaseGameScenario());
        AlienPlayer ap = game.aliens.get(0);

        Assert.assertEquals("1", new Fleet(ap, FleetType.REGULAR_FLEET, 0).getName());

        ap.removeFleet(ap.getFleets().get(0));
        Assert.assertEquals("1", new Fleet(ap, FleetType.REGULAR_FLEET, 0).getName());
        Assert.assertEquals("1", new Fleet(ap, FleetType.DEFENSE_FLEET, 0).getName());
    }
    
    
}
