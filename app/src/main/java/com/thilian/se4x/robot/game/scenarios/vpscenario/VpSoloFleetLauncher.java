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

package com.thilian.se4x.robot.game.scenarios.vpscenario;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.FleetLauncher;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.FleetType;

import static com.thilian.se4x.robot.game.enums.FleetType.EXPANSION_FLEET;
import static com.thilian.se4x.robot.game.enums.FleetType.EXTERMINATION_FLEET_GALACTIC_CAPITAL;
import static com.thilian.se4x.robot.game.enums.FleetType.EXTERMINATION_FLEET_HOME_WORLD;
import static com.thilian.se4x.robot.game.enums.FleetType.REGULAR_FLEET;

public class VpSoloFleetLauncher extends FleetLauncher {
    public VpSoloFleetLauncher(Game game) {
        super(game);
    }

    @Override
    public Fleet launchFleet(AlienPlayer ap, int turn, FleetBuildOption... options) {
        VpEconomicSheet vpSheet = (VpEconomicSheet) ap.getEconomicSheet();
        int bank = vpSheet.getBank();
        Fleet fleet = super.launchFleet(ap, turn, options);
        if(fleet == null){
            if(bank >= 50){
                fleet = new Fleet(ap, EXPANSION_FLEET, 0);
            }
        }
        if (fleet != null) {
            setFleetType(fleet, turn);
        }
        return fleet;
    }

    private void setFleetType(Fleet fleet, int turn){
        VpEconomicSheet vpSheet = (VpEconomicSheet) fleet.getAp().getEconomicSheet();
        int bank = vpSheet.getBank();
        if (fleet.getFleetType().equals(REGULAR_FLEET)) {
            int roll = game.roller.roll();
            if (turn > 7)
                roll += 2;
            if (turn > 10)
                roll += 2;
            if (roll < 8) {
                fleet.setFleetType(EXPANSION_FLEET);
            } else {
                fleet.setFleetType(getExterminationFleetType(turn));
            }
        }

        if (fleet.getFleetType().equals(EXPANSION_FLEET) && bank >= 50) {
            fleet.addFleetCp(50);
            vpSheet.spendBank(50);
        }
    }

    protected FleetType getExterminationFleetType(int turn) {
         if (game.roller.roll(2) == 1)
            return EXTERMINATION_FLEET_HOME_WORLD;
        else
            return EXTERMINATION_FLEET_GALACTIC_CAPITAL;
    }
}
