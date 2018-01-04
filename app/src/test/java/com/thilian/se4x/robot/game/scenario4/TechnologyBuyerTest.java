package com.thilian.se4x.robot.game.scenario4;

import com.thilian.se4x.robot.game.AlienEconomicSheet;
import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.Technology;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by thili on 2017. 12. 05..
 */

public class TechnologyBuyerTest {

    private Game game;
    private AlienPlayer ap;
    private Scenario4TechnologyBuyer techBuyer;
    private AlienEconomicSheet sheet;

    @Before
    public void setUp(){
        sheet = new AlienEconomicSheet(Difficulty.NORMAL);
        game = new Game();
        techBuyer = new Scenario4TechnologyBuyer(game);
        game.techBuyer = techBuyer;
        game.technologyPrices = new Scenario4TechnologyPrices();
        game.resetSeenLevels();
        ap = new AlienPlayer(sheet, game, null);
    }

    @Test
    public void buyOptionalSecurity() {
        assertDontBuySecurity(1);
        game.setSeenLevel(Technology.BOARDING, 1);
        assertBuySecurity(1);
        assertDontBuySecurity(2);
    }

    @Test
    public void buyOptionalGroundCombat(){
        assertDontBuyGroundCombat(2);
        assertBuyGroundCombat(2);
        assertBuyGroundCombat(3);
        assertDontBuyGroundCombat(4);
    }

    @Test
    public void buyOptionalMilitaryAcademy(){
        assertDontBuyMilitaryAcademy(1);
        //TODO HERE
    }

    private void assertBuySecurity(int expectedLevel) {
        assertBuyOptional(expectedLevel, Technology.SECURITY_FORCES, new BuyAction(){
            @Override
            void buy(AlienPlayer ap) {
                techBuyer.buySecurityIfNeeded(ap);
            }
        });
    }

    private void assertDontBuySecurity(int expectedLevel) {
        assertDontBuyOptional(expectedLevel, Technology.SECURITY_FORCES,  new BuyAction(){
            @Override
            void buy(AlienPlayer ap) {
                techBuyer.buySecurityIfNeeded(ap);
            }
        });
    }

    private void assertBuyGroundCombat(int level) {
        assertBuyOptional(level, Technology.GROUND_COMBAT, new BuyAction(){
            @Override
            void buy(AlienPlayer ap) { techBuyer.buyGroundCombatIfNeeded(ap, true);}
        });
    }

    private void assertDontBuyGroundCombat(int level) {
        assertDontBuyOptional(level, Technology.GROUND_COMBAT, new BuyAction(){
            @Override
            void buy(AlienPlayer ap) { techBuyer.buyGroundCombatIfNeeded(ap, false);}
        });
    }

    private void assertDontBuyMilitaryAcademy(int level) {
        assertDontBuyOptional(level, Technology.MILITARY_ACADEMY, new BuyAction() {
            @Override
            void buy(AlienPlayer ap) {
                techBuyer.buyMilitaryAcademyIfNeeded();
            }
        });
    }

    private void assertBuyOptional(int expectedLevel, Technology technology, BuyAction buyAction) {
        assertOptionalBuy(
                technology,
                expectedLevel,
                100 -  game.technologyPrices.getCost(technology, expectedLevel),
                buyAction);
    }

    private void assertDontBuyOptional(int expectedLevel, Technology technology, BuyAction buyAction) {
        assertOptionalBuy(
                technology,
                expectedLevel - 1,
                100 ,
                buyAction);
    }

    private void assertOptionalBuy(Technology technology, int newLevel, int remainingCP,
                                   BuyAction buyAction) {
        sheet.setTechCP(100);
        buyAction.buy(ap);
        assertLevel(technology, newLevel);
        assertEquals(remainingCP, sheet.getTechCP());
    }

    protected void assertLevel(Technology technology, int expectedLevel) {
        assertEquals(expectedLevel, ap.getLevel(technology));
    }

    abstract class BuyAction {
        abstract void buy(AlienPlayer ap);
    }
}
