package com.thilian.se4x.robot.game;

import com.thilian.se4x.robot.game.basegame.BaseGameScenario;
import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.Scenarios;

import junit.framework.Assert;

import org.junit.Test;

public class AlienPlayerTest {
    @Test
    public void testFleetNames(){
        Game game = new Game();
        game.createGame(Difficulty.NORMAL, new BaseGameScenario());
        AlienPlayer ap = new AlienPlayer(null, game, null);

        Assert.assertEquals("1", new Fleet(ap, FleetType.REGULAR_FLEET, 0).getName());

        ap.destroyFleet(ap.getFleets().get(0));
        Assert.assertEquals("1", new Fleet(ap, FleetType.REGULAR_FLEET, 0).getName());
        Assert.assertEquals("1", new Fleet(ap, FleetType.DEFENSE_FLEET, 0).getName());
    }
}
