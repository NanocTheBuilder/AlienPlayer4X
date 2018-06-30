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

package com.thilian.se4x.robot.game.scenarios.scenario4;

import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.buyers.fleet.optional.BoardingShipBuyer;
import com.thilian.se4x.robot.game.buyers.fleet.optional.DestroyerBuyer;
import com.thilian.se4x.robot.game.buyers.fleet.optional.FlagshipBuyer;
import com.thilian.se4x.robot.game.buyers.fleet.optional.FreeFullTransportBuyer;
import com.thilian.se4x.robot.game.buyers.fleet.optional.FullCarrierBuyer;
import com.thilian.se4x.robot.game.buyers.fleet.optional.JPCRaiderFleetBuyer;
import com.thilian.se4x.robot.game.buyers.fleet.optional.RaiderFleetBuyer;
import com.thilian.se4x.robot.game.buyers.fleet.optional.ScoutBuyer;

import java.util.Arrays;

public class FleetBuilder extends com.thilian.se4x.robot.game.FleetBuilder {

    public FleetBuilder(Game game) {
        super(game);
        fleetBuyers = Arrays.asList(
                new RaiderFleetBuyer(game),
                new JPCRaiderFleetBuyer(game),
                new FreeFullTransportBuyer(game),
                new BoardingShipBuyer(game),
                new ScoutBuyer(game),
                new FullCarrierBuyer(game),
                new FlagshipBuyer(game),
                new DestroyerBuyer(game));
    };
}
