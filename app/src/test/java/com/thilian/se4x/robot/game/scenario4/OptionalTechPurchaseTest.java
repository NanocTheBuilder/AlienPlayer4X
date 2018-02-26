package com.thilian.se4x.robot.game.scenario4;

import org.junit.Test;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.Technology;

/**
 * Created by thili on 2017. 12. 05..
 */

public class OptionalTechPurchaseTest extends Scenario4TechnologyBuyerTestBase {

    @Test
    public void buyOptionalSecurity() {
        assertDontBuySecurity(1);
        game.addSeenThing(Seeable.BOARDING_SHIPS);
        assertBuySecurity(1);
        assertDontBuySecurity(2);
    }

    private void assertBuySecurity(int expectedLevel) {
        assertBuyOptional(expectedLevel, Technology.SECURITY_FORCES, new BuyAction(){
            @Override
            protected void buy(AlienPlayer ap) {
                techBuyer.buySecurityIfNeeded(ap);
            }
        });
    }

    private void assertDontBuySecurity(int expectedLevel) {
        assertDontBuyOptional(expectedLevel, Technology.SECURITY_FORCES,  new BuyAction(){
            @Override
            protected void buy(AlienPlayer ap) {
                techBuyer.buySecurityIfNeeded(ap);
            }
        });
    }

    @Test
    public void buyOptionalGroundCombat(){
        assertDontBuyGroundCombat(2, false);
        assertBuyGroundCombat(2, true);
        assertBuyGroundCombat(3, true);
        assertDontBuyGroundCombat(4, true);
    }

    private void assertBuyGroundCombat(int level, final boolean combatIsAbovePlanet) {
        assertBuyOptional(level, Technology.GROUND_COMBAT, new BuyAction(){
            @Override
            protected void buy(AlienPlayer ap) { techBuyer.buyGroundCombatIfNeeded(ap, combatIsAbovePlanet);}
        });
    }

    private void assertDontBuyGroundCombat(int level, final boolean combatIsAbovePlanet) {
        assertDontBuyOptional(level, Technology.GROUND_COMBAT, new BuyAction(){
            @Override
            protected void buy(AlienPlayer ap) { techBuyer.buyGroundCombatIfNeeded(ap, combatIsAbovePlanet);}
        });
    }

    @Test
    public void buyOptionalMilitaryAcademy(){
        assertDontBuyMilitaryAcademy(1);

        game.addSeenThing(Seeable.VETERANS);
        roller.mockRoll(7);
        assertDontBuyMilitaryAcademy(1);
        roller.mockRoll(6);
        assertBuyMilitaryAcademy(1);

        roller.mockRoll(7);
        assertDontBuyMilitaryAcademy(2);
        roller.mockRoll(6);
        assertBuyMilitaryAcademy(2);

        roller.mockRoll(6);
        assertDontBuyMilitaryAcademy(3);
    }

    private void assertBuyMilitaryAcademy(int level) {
        assertBuyOptional(level, Technology.MILITARY_ACADEMY, new BuyAction() {
            @Override
            protected void buy(AlienPlayer ap) {
                techBuyer.buyMilitaryAcademyIfNeeded(ap);
            }
        });
    }

    private void assertDontBuyMilitaryAcademy(int level) {
        assertDontBuyOptional(level, Technology.MILITARY_ACADEMY, new BuyAction() {
            @Override
            protected void buy(AlienPlayer ap) {
                techBuyer.buyMilitaryAcademyIfNeeded(ap);
            }
        });
    }

    @Test
    public void buyOptionalBoarding(){
        assertDontBuyOptionalBoarding(1);
        game.addSeenThing(Seeable.SIZE_3_SHIPS);
        roller.mockRoll(5);
        assertDontBuyOptionalBoarding(1);
        roller.mockRoll(4);
        assertBuyOptionalBoarding(1);

        assertDontBuyOptionalBoarding(2);

    }

    private void assertBuyOptionalBoarding(int level) {
        assertBuyOptional(level, Technology.BOARDING, new BuyAction() {
            @Override
            protected void buy(AlienPlayer ap) {
                techBuyer.buyBoardingIfNeeded(ap);
            }
        });
    }

    private void assertDontBuyOptionalBoarding(int level) {
        assertDontBuyOptional(level, Technology.BOARDING, new BuyAction() {
            @Override
            protected void buy(AlienPlayer ap) {
                techBuyer.buyBoardingIfNeeded(ap);
            }
        });
    }

    @Test
    public void buyOptionalShipSize() {
        roller.mockRoll(10);
        assertBuyShipSize(2);

        assertBuyShipSize(3, 7);
        assertBuyShipSize(4, 6);
        assertBuyShipSize(5, 5);
        assertBuyShipSize(6, 3);
        assertBuyShipSize(7, 6);

        assertDontBuyShipSize(8);
    }

}
