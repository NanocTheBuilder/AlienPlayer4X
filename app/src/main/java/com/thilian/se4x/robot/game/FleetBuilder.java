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

import com.thilian.se4x.robot.game.buyers.FleetCompositionModifier;
import com.thilian.se4x.robot.game.buyers.OptionalGroupBuyer;
import com.thilian.se4x.robot.game.buyers.fleet.ExtraScoutBuyer;
import com.thilian.se4x.robot.game.buyers.fleet.RemainingFleetBuyer;
import com.thilian.se4x.robot.game.buyers.fleet.optional.DestroyerBuyer;
import com.thilian.se4x.robot.game.buyers.fleet.optional.FlagshipBuyer;
import com.thilian.se4x.robot.game.buyers.fleet.optional.FullCarrierBuyer;
import com.thilian.se4x.robot.game.buyers.fleet.optional.JPCRaiderFleetBuyer;
import com.thilian.se4x.robot.game.buyers.fleet.optional.RaiderFleetBuyer;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;

import java.util.Arrays;
import java.util.List;

import static com.thilian.se4x.robot.game.enums.FleetBuildOption.HOME_DEFENSE;
import static com.thilian.se4x.robot.game.enums.FleetType.RAIDER_FLEET;
import static com.thilian.se4x.robot.game.enums.ShipType.RAIDER;
import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static com.thilian.se4x.robot.game.enums.Technology.SCANNER;

public class FleetBuilder extends GroupBuilder {

    protected Game game;
    protected List<OptionalGroupBuyer> fleetBuyers;
    private FleetCompositionModifier fleetCompositionModifier;
    private ExtraScoutBuyer extraScoutBuyer;
    RemainingFleetBuyer remainingFleetBuyer;

    public FleetBuilder(Game game) {
        this.game = game;
        fleetBuyers = Arrays.asList(
                new RaiderFleetBuyer(game),
                new FullCarrierBuyer(game),
                new JPCRaiderFleetBuyer(game),
                new FlagshipBuyer(game),
                new DestroyerBuyer(game));
        fleetCompositionModifier = new FleetCompositionModifier(game);
        extraScoutBuyer = new ExtraScoutBuyer(game);
        remainingFleetBuyer = new RemainingFleetBuyer(game);
    }

    public void buildFleet(Fleet fleet, FleetBuildOption... options) {
        for(OptionalGroupBuyer buyer : fleetBuyers){
            buyer.buyGroups(fleet, options);
            if(RAIDER_FLEET.equals(fleet.getFleetType()) && fleet.getGroups().size() != 0)
                break;
        }
        buildRemainderFleet(fleet);
    }

    protected void buildRemainderFleet(Fleet fleet) {
        if (fleet.canBuyMoreShips()) {
            int fleetCompositionRoll = game.roller.roll();

            if(fleetCompositionModifier != null){
                fleetCompositionRoll += fleetCompositionModifier.getModifier(fleet);
            }

            if(extraScoutBuyer != null){
                extraScoutBuyer.buyGroups(fleet, fleetCompositionRoll);
            }

            if (fleetCompositionRoll <= 3) {
                remainingFleetBuyer.buyMaxNumberOfShips(fleet);
            } else if (fleetCompositionRoll <= 6) {
                remainingFleetBuyer.buyBalancedFleet(fleet);
            } else {
                remainingFleetBuyer.buyLargestShipsAvailable(fleet);
            }
        }
    }

    protected void buyFullCarriers(Fleet fleet, FleetBuildOption... options) {
        new FullCarrierBuyer(game).buyGroups(fleet, options);
    }

    protected void buildPossibleDD(Fleet fleet) {
        new DestroyerBuyer(game).buyGroups(fleet);
    }

    protected void buildRaiderFleet(Fleet fleet) {
        fleet.setFleetType(RAIDER_FLEET);
        buildGroup(fleet, RAIDER);
    }

    protected void buildFlagship(Fleet fleet) {
        new FlagshipBuyer(game).buyGroups(fleet);
    }

    protected boolean shouldBuildRaiderFleet(Fleet fleet, FleetBuildOption... options) {
        // TODO more test for this. Especially the resetting of
        // isPurchasedThisTurn
        if (FleetBuildOption.isOption(HOME_DEFENSE, options))
            return false;
        AlienPlayer ap = fleet.getAp();
        return fleet.getGroups().size() == 0 && fleet.getFleetCP() >= RAIDER.getCost() && ap.isJustPurchasedCloaking()
                && ap.getLevel(CLOAKING) > game.getSeenLevel(SCANNER);
    }

}
