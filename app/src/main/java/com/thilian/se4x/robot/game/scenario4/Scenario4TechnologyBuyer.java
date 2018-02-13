package com.thilian.se4x.robot.game.scenario4;

import static com.thilian.se4x.robot.game.enums.Technology.ATTACK;
import static com.thilian.se4x.robot.game.enums.Technology.BOARDING;
import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static com.thilian.se4x.robot.game.enums.Technology.DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.FIGHTERS;
import static com.thilian.se4x.robot.game.enums.Technology.MILITARY_ACADEMY;
import static com.thilian.se4x.robot.game.enums.Technology.MINE_SWEEPER;
import static com.thilian.se4x.robot.game.enums.Technology.POINT_DEFENSE;
import static com.thilian.se4x.robot.game.enums.Technology.SCANNER;
import static com.thilian.se4x.robot.game.enums.Technology.SECURITY_FORCES;
import static com.thilian.se4x.robot.game.enums.Technology.SHIP_SIZE;
import static com.thilian.se4x.robot.game.enums.Technology.TACTICS;

import java.util.Arrays;

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
		addToRollTable(SHIP_SIZE, 16);
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
		addToRollTable(BOARDING, 4);
	}

	@Override
	protected int[] getShipSizeRollTable() {
		return SHIP_SIZE_ROLL_TABLE;
	}

	@Override
	public void buyOptionalTechs(Fleet fleet) {

	}

}
