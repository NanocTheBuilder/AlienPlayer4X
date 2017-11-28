package com.thilian.se4x.robot.game;

import com.thilian.se4x.robot.game.enums.FleetType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by thili on 2017. 11. 23..
 */

public class FleetNameTest {
    AlienPlayer ap;

    @Before
    public void setUp(){
        ap = new AlienPlayer(null, new Game(), null);
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
        ap.destroyFleet(fleet1);

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

}
