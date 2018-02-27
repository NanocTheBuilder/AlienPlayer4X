package com.thilian.se4x.robot.game;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;

import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.Technology;

public abstract class TechnologyBuyerTestBase extends Fixture{


    protected Fleet fleet;

    @Before
    public void setUp() {
        for (Technology t : game.scenario.getAvailableTechs()) {
            assertLevel(t, game.scenario.getStartingLevel(t));
        }
        buildFleet();
    }

    @After
    public void assertAllRollsUsed() {
        assertEquals(0, roller.rolls.size());
    }

    protected void buildFleet() {
        fleet = new Fleet(ap, FleetType.REGULAR_FLEET, -1);
    }

    protected void assertLevel(Technology technology, int expectedLevel) {
        assertEquals(expectedLevel, ap.getLevel(technology));
    }

    protected void assertBuyShipSize(int newLevel, int rollNeeded) {
        roller.mockRoll(rollNeeded + 1);
        assertDontBuyShipSize(newLevel);
        roller.mockRoll(rollNeeded);
        assertBuyShipSize(newLevel);
    }

    protected void assertBuyShipSize(int expectedLevel) {
        assertBuyOptional(expectedLevel, Technology.SHIP_SIZE, new BuyAction() {
            @Override
            protected void buy(AlienPlayer ap) {
                techBuyer.buyShipSizeIfRolled(ap);
            }
        });
    }

    protected void assertDontBuyShipSize(int expectedLevel) {
        assertDontBuyOptional(expectedLevel, Technology.SHIP_SIZE, new BuyAction() {
            @Override
            protected void buy(AlienPlayer ap) {
                techBuyer.buyShipSizeIfRolled(ap);
            }
        });
    }

    protected void assertBuyOptional(int expectedLevel, Technology technology, BuyAction buyAction) {
        assertOptionalBuy(technology, expectedLevel, 100 - game.scenario.getCost(technology, expectedLevel), buyAction);
    }

    protected void assertDontBuyOptional(int expectedLevel, Technology technology, BuyAction buyAction) {
        assertOptionalBuy(technology, expectedLevel - 1, 100, buyAction);
    }

    protected void assertOptionalBuy(Technology technology, int newLevel, int remainingCP, BuyAction buyAction) {
        sheet.setTechCP(100);
        buyAction.buy(ap);
        assertLevel(technology, newLevel);
        assertEquals(remainingCP, sheet.getTechCP());
    }

    protected abstract class BuyAction {
        protected abstract void buy(AlienPlayer ap);
    }
}