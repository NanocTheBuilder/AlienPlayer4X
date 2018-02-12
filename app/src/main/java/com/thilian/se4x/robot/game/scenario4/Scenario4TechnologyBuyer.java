package com.thilian.se4x.robot.game.scenario4;

import static com.thilian.se4x.robot.game.enums.Technology.*;

import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.TechnologyBuyer;

/**
 * Created by thili on 2017. 12. 06..
 */

class Scenario4TechnologyBuyer extends TechnologyBuyer {
	private static int[] SHIP_SIZE_ROLL_TABLE = new int[] { 0, 10, 7, 6, 5, 3, 6 };

	public Scenario4TechnologyBuyer(Game game) {
		super(game);
	}

	@Override
	protected void initRollTable() {
		addToRollTable(ATTACK, 20);
		addToRollTable(DEFENSE, 20);
		addToRollTable(TACTICS, 12);
		addToRollTable(CLOAKING, 3);
		addToRollTable(SCANNER, 2);
		addToRollTable(FIGHTERS, 8);
		addToRollTable(POINT_DEFENSE, 3);
		addToRollTable(MINE_SWEEPER, 5);
		addToRollTable(SECURITY_FORCES, 3);
		addToRollTable(MILITARY_ACADEMY, 4);
	}

	@Override
	protected int[] getShipSizeRollTable() {
		return SHIP_SIZE_ROLL_TABLE;
	}

	@Override
	public void buyOptionalTechs(Fleet fleet) {

	}

}
