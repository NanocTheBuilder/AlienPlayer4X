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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thilian.se4x.robot.game.enums.PlayerColor;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameDifficulty;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameScenario;
import com.thilian.se4x.robot.game.enums.FleetType;

public class FleetNameTest {
    AlienPlayer ap;

    @Before
    public void setUp(){
        Game game = Game.newGame(new BaseGameScenario(), BaseGameDifficulty.NORMAL, PlayerColor.GREEN, PlayerColor.YELLOW, PlayerColor.RED);
        ap = game.aliens.get(0);
    }

    @Test
    public void firstFleetIsCalledOneSecondIsTwo(){
        Fleet fleet = new Fleet(ap, FleetType.REGULAR_FLEET, 0);
        Assert.assertEquals("1", fleet.getName());
        fleet = new Fleet(ap, FleetType.REGULAR_FLEET, 0);
        Assert.assertEquals("2", fleet.getName());
    }

    @Test
    public void namesFillHoles(){
        Fleet fleet1 = new Fleet(ap, FleetType.REGULAR_FLEET, 0);
        Fleet fleet2 = new Fleet(ap, FleetType.REGULAR_FLEET, 0);
        ap.removeFleet(fleet1);

        Fleet fleet = new Fleet(ap, FleetType.REGULAR_FLEET, 0);
        Assert.assertEquals("1", fleet.getName());
    }

    @Test
    public void raiderFleetsAreDifferent(){
        Fleet fleet1 = new Fleet(ap, FleetType.REGULAR_FLEET, 0);

        Fleet fleet = new Fleet(ap, FleetType.RAIDER_FLEET, 0);
        Assert.assertEquals("1", fleet.getName());
    }

    @Test
    public void setTypeChangesName(){
        Fleet fleet1 = new Fleet(ap, FleetType.REGULAR_FLEET, 0);

        Fleet fleet = new Fleet(ap, FleetType.REGULAR_FLEET, 0);
        Assert.assertEquals("2", fleet.getName());

        fleet.setFleetType(FleetType.DEFENSE_FLEET);
        Assert.assertEquals("1", fleet.getName());
    }


    @Test
    public void setTypeFromRegularToExDontChangeName(){
        Fleet fleet1 = new Fleet(ap, FleetType.REGULAR_FLEET, 0);

        Fleet fleet = new Fleet(ap, FleetType.REGULAR_FLEET, 0);
        Assert.assertEquals("2", fleet.getName());

        fleet.setFleetType(FleetType.EXPANSION_FLEET);
        Assert.assertEquals("2", fleet.getName());

        fleet.setFleetType(FleetType.EXTERMINATION_FLEET);
        Assert.assertEquals("2", fleet.getName());
    }

    @Test
    public void setTypeFromRegularToRaiderRenames(){
        Fleet fleet1 = new Fleet(ap, FleetType.REGULAR_FLEET, 0);

        Fleet fleet = new Fleet(ap, FleetType.REGULAR_FLEET, 0);
        Assert.assertEquals("2", fleet.getName());

        fleet.setFleetType(FleetType.RAIDER_FLEET);
        Assert.assertEquals("1", fleet.getName());

    }
}
