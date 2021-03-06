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

import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.PlayerColor;
import com.thilian.se4x.robot.game.enums.Technology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thilian.se4x.robot.game.enums.FleetBuildOption.COMBAT_IS_ABOVE_PLANET;
import static com.thilian.se4x.robot.game.enums.FleetBuildOption.HOME_DEFENSE;
import static com.thilian.se4x.robot.game.enums.FleetType.RAIDER_FLEET;
import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;

public class AlienPlayer {

    private List<Fleet> fleets = new ArrayList<>();
    protected AlienEconomicSheet economicSheet;
    protected Game game;
    private PlayerColor color;
    private Map<Technology, Integer> technologyLevels = new HashMap<>();

    private boolean purchasedCloakThisTurn = false;

    private boolean eliminated = false;

    public AlienPlayer(AlienEconomicSheet sheet, Game game, PlayerColor color) {
        this.economicSheet = sheet;
        this.game = game;
        this.color = color;

        for (Technology technology : game.scenario.getAvailableTechs()) {
            int startingLevel = game.scenario.getStartingLevel(technology);
            technologyLevels.put(technology, startingLevel);
        }
    }

    public AlienEconomicSheet getEconomicSheet() {
        return this.economicSheet;
    }

    public EconPhaseResult makeEconRoll(int turn) {
        EconPhaseResult result = new EconPhaseResult(this);
        int econRolls = economicSheet.getEconRolls(turn) + getExtraEconRoll(turn);
        for (int i = 0; i < econRolls; i++) {
            EconRollResult rollResult = economicSheet.makeRoll(turn, game.roller);
            result.add(rollResult);
        }
        Fleet newFleet = game.scenario.rollFleetLaunch(this, turn);
        if (newFleet != null) {
            result.setFleet(newFleet);
            boolean nextMoveLevel = buyNextMoveLevel();
            result.setMoveTechRolled(nextMoveLevel);
        }
        return result;
    }

    protected int getExtraEconRoll(int turn) {
        return economicSheet.getExtraEcon(turn);
    }

    private void buyTechs(Fleet fleet, FleetBuildOption... options) {
        int oldCloaking = getLevel(CLOAKING);
        game.scenario.buyTechs(fleet, options);
        setJustPurchasedCloaking(getLevel(CLOAKING) != oldCloaking);
    }

    public FleetBuildResult firstCombat(Fleet fleet, FleetBuildOption... options) {
        FleetBuildResult result = new FleetBuildResult(this);

        Map<Technology, Integer> oldTechValues = new HashMap<>();
        for(Technology technology : game.scenario.getAvailableTechs()){
            oldTechValues.put(technology, getLevel(technology));
        }

        buyTechs(fleet, options);
        if(!RAIDER_FLEET.equals(fleet.getFleetType())) {
            game.scenario.buildFleet(fleet, options);
            economicSheet.addFleetCP(fleet.getFleetCP() - fleet.getBuildCost());
            result.addNewFleet(fleet);
        }
        fleet.setFirstCombat(true);

        for(Technology technology : game.scenario.getAvailableTechs()){
            if(!oldTechValues.get(technology).equals(getLevel(technology))){
                result.addNewTech(technology, getLevel(technology));
            }
        }

        return result;
    }

    public void removeFleet(Fleet fleet) {
        fleets.remove(fleet);
    }

    public FleetBuildResult buildHomeDefense() {
        FleetBuildResult result;
        Fleet fleet = game.scenario.fleetLauncher.launchFleet(this, game.currentTurn, HOME_DEFENSE);
        if(fleet != null) {
            result = firstCombat(fleet, HOME_DEFENSE, COMBAT_IS_ABOVE_PLANET);
        }
        else{
            result = new FleetBuildResult(this);
        }

        fleet = game.scenario.buildHomeDefense(this);
        if (fleet != null) {
            economicSheet.spendDefCP(fleet.getBuildCost());
            fleet.setFirstCombat(true);
            result.addNewFleet(fleet);
        }
        return result;
    }

    public boolean buyNextMoveLevel() {
        int oldLevel = getLevel(Technology.MOVE);
        if (game.roller.roll() <= 4) {
            game.scenario.buyNextLevel(this, Technology.MOVE);
        }
        return getLevel(Technology.MOVE) != oldLevel;
    }

    public List<Fleet> getFleets() {
        return fleets;
    }

    public boolean isJustPurchasedCloaking() {
        return purchasedCloakThisTurn;
    }

    public void setJustPurchasedCloaking(boolean purchasedCloakThisTurn) {
        this.purchasedCloakThisTurn = purchasedCloakThisTurn;
    }

    public int getLevel(Technology technology) {
        return technologyLevels.get(technology);
    }

    public void setLevel(Technology technology, int level) {
        technologyLevels.put(technology, level);
    }

    public PlayerColor getColor() {
        return color;
    }

    public String findFleetName(FleetType fleetType) {
        for (int i = 1; i < 100; i++) {
            if (findFleetByName(String.valueOf(i), fleetType) == null) {
                return String.valueOf(i);
            }
        }
        return "?";
    }

    private Fleet findFleetByName(String name, FleetType fleetType) {
        for (Fleet fleet : fleets) {
            if (fleet.getName().equals(name) && fleet.getFleetType().isSameNameSequence(fleetType))
                return fleet;
        }
        return null;
    }

    public boolean isEliminated() {
        return eliminated;
    }

    public void setEliminated(boolean eliminated) {
        this.eliminated = eliminated;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
