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

package com.thilian.se4x.robot.game.buyers.fleet;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.buyers.GroupBuyer;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

import static com.thilian.se4x.robot.game.enums.Technology.SHIP_SIZE;

public class RemainingFleetBuyer extends GroupBuyer {
    private final Game game;

    public RemainingFleetBuyer(Game game){
        this.game = game;
    }

    public void buyMaxNumberOfShips(Fleet fleet){
        buildBalanced(fleet, 1);
    }

    public void buyBalancedFleet(Fleet fleet){
        AlienPlayer ap = fleet.getAp();
        int maxHullSize = Math.max(ap.getLevel(Technology.ATTACK), ap.getLevel(Technology.DEFENSE));
        buildBalanced(fleet, maxHullSize);
    }

    public void buyLargestShipsAvailable(Fleet fleet){
        while (fleet.canBuyMoreShips()) {
            buyGroup(fleet, game.scenario.findBiggest(fleet));
        }
    }

    private void buildBalanced(Fleet fleet, int minHullSize) {
        int apShipSize = fleet.getAp().getLevel(SHIP_SIZE);
        for (int i = minHullSize; i >= 0; i--) {
            ShipType cheapestType = game.scenario.findCheapest(i);
            if (apShipSize >= cheapestType.getRequiredShipSize()) {
                for (ShipType biggerType : game.scenario.findBiggerTypesInReverse(cheapestType)) {
                    if (apShipSize >= biggerType.getRequiredShipSize() && fleet.getRemainingCP() >= biggerType.getCost()) {
                        int remainder = fleet.getRemainingCP() % cheapestType.getCost();
                        int difference = biggerType.getCost() - cheapestType.getCost();
                        int shipType2ToBuy = remainder / difference;
                        buyGroup(fleet, biggerType, shipType2ToBuy);
                    }
                }
                buyGroup(fleet, cheapestType);
            }
        }
    }
}
