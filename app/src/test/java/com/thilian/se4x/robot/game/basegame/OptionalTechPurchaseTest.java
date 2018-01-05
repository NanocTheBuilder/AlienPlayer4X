package com.thilian.se4x.robot.game.basegame;

import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.Technology;

public class OptionalTechPurchaseTest  extends BasegameTechnologyBuyerTestBase {

	@Test
	public void buyOptionalPontDefense() {
		assertDontBuyPD(1);
		game.addSeenThing(Seeable.FIGHTERS);
		assertBuyPD(1);
		assertDontBuyPD(2);
	}

	private void assertBuyPD(int expectedLevel) {
		assertBuyOptional(expectedLevel, Technology.POINT_DEFENSE, new BuyAction(){
			@Override
			protected void buy(AlienPlayer ap) {
				techBuyer.buyPointDefenseIfNeeded(ap);
			}
		});
	}

	private void assertDontBuyPD(int expectedLevel) {
		assertDontBuyOptional(expectedLevel, Technology.POINT_DEFENSE, new BuyAction(){
			@Override
			protected void buy(AlienPlayer ap) {
				techBuyer.buyPointDefenseIfNeeded(ap);
			}
		});
	}

	@Test
	public void buyOptionalMineSweep() {
		assertDontBuyMS(1);
		game.addSeenThing(Seeable.MINES);
		assertBuyMS(1);
		assertDontBuyMS(2);
	}

	private void assertBuyMS(int expectedLevel) {
		assertBuyOptional(expectedLevel, Technology.MINE_SWEEPER, new BuyAction(){
			@Override
			protected void buy(AlienPlayer ap) {
				techBuyer.buyMineSweepIfNeeded(ap);
			}
		});
	}

	private void assertDontBuyMS(int expectedLevel) {
		assertDontBuyOptional(expectedLevel, Technology.MINE_SWEEPER,  new BuyAction(){
			@Override
			protected void buy(AlienPlayer ap) {
				techBuyer.buyMineSweepIfNeeded(ap);
			}
		});
	}

	@Test
	public void buyOptionalScan() {
		game.setSeenLevel(CLOAKING, 1);
		roller.mockRoll(4);
		assertBuyScanner(1);
		assertDontBuyScanner(2);

		game.setSeenLevel(CLOAKING, 2);
		roller.mockRoll(5);
		assertDontBuyScanner(2);

		roller.mockRoll(4);
		assertBuyScanner(2);

		game.setSeenLevel(CLOAKING, 2);
		ap.setLevel(Technology.SCANNER, 0);
		roller.mockRoll(4);
		assertOptionalBuy(
				Technology.SCANNER,
				2,
				60,
				new BuyAction(){
					@Override
					protected void buy(AlienPlayer ap) {
						techBuyer.buyScannerIfNeeded(ap);
					}
				});
	}

	private void assertBuyScanner(int expectedLevel) {
		assertBuyOptional(expectedLevel, Technology.SCANNER, new BuyAction(){
			@Override
			protected void buy(AlienPlayer ap) {
				techBuyer.buyScannerIfNeeded(ap);
			}
		});
	}

	private void assertDontBuyScanner(int expectedLevel) {
		assertDontBuyOptional(expectedLevel, Technology.SCANNER, new BuyAction(){
			@Override
			protected void buy(AlienPlayer ap) {
				techBuyer.buyScannerIfNeeded(ap);
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

		assertDontBuyShipSize(7);
	}

	@Test
	public void buyOptionalFighterLevel() {
		game.setSeenLevel(Technology.POINT_DEFENSE, 0);
		assertDontBuyFighters(1);

		ap.setLevel(Technology.FIGHTERS, 1);
		roller.mockRoll(6);
		assertBuyFighters(2);

		game.setSeenLevel(Technology.POINT_DEFENSE, 1);
		assertDontBuyFighters(3);

		game.setSeenLevel(Technology.POINT_DEFENSE, 0);
		roller.mockRoll(7);
		assertDontBuyFighters(3);

		roller.mockRoll(6);
		assertBuyFighters(3);
	}

	private void assertBuyFighters(int expectedLevel) {
		assertBuyOptional(expectedLevel, Technology.FIGHTERS, new BuyAction(){
			@Override
			protected void buy(AlienPlayer ap) {
				techBuyer.buyFightersIfNeeded(ap);
			}
		});
	}

	private void assertDontBuyFighters(int expectedLevel) {
		assertDontBuyOptional(expectedLevel, Technology.FIGHTERS, new BuyAction(){
			@Override
			protected void buy(AlienPlayer ap) {
				techBuyer.buyFightersIfNeeded(ap);
			}
		});
	}

	@Test
	public void buyOptionalCloak() {
		fleet.setFleetType(FleetType.RAIDER_FLEET);;
		ap.setLevel(CLOAKING, 1);
		roller.mockRoll(7);
		assertDontBuyCloaking(2);

		roller.mockRoll(6);
		assertBuyCloaking(2);

		ap.setLevel(CLOAKING, 1);
		fleet.setFleetType(FleetType.REGULAR_FLEET);
		assertDontBuyCloaking(2);
	}

	private void assertBuyCloaking(int expectedLevel) {
		sheet.setTechCP(100);
		techBuyer.buyCloakingIfNeeded(fleet);
		assertLevel(CLOAKING, expectedLevel);
		assertEquals(100 - game.technologyPrices.getCost(CLOAKING, expectedLevel), sheet.getTechCP());
	}

	private void assertDontBuyCloaking(int expectedLevel) {
		sheet.setTechCP(100);
		techBuyer.buyCloakingIfNeeded(fleet);
		assertLevel(CLOAKING, expectedLevel - 1);
		assertEquals(100, sheet.getTechCP());
	}
}
