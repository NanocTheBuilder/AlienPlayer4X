package com.thilian.se4x.robot.game;

import java.util.Collection;

import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.PlayerColor;
import com.thilian.se4x.robot.game.enums.Technology;

public abstract class Scenario{

    //TODO: private these
    public TechnologyBuyer techBuyer;
    public TechnologyPrices techPrices;
    public FleetBuilder fleetBuilder;
    public DefenseBuilder defenseBuilder;
    public FleetLauncher fleetLauncher;

    public abstract void init(Game game);

    public abstract AlienPlayer newPlayer(Game game, Difficulty difficulty, PlayerColor color);

    public abstract Difficulty[] getDifficulties();

    public Collection<Technology> getAvailableTechs() {
        return techPrices.getAvailableTechs();
    }

    public int getStartingLevel(Technology technology) {
        return techPrices.getStartingLevel(technology);
    }

    public void buildFleet(Fleet fleet, FleetBuildOption... options) {
        fleetBuilder.buildFleet(fleet, options);
    }

    public Fleet buildHomeDefense(AlienPlayer alienPlayer) {
        return defenseBuilder.buildHomeDefense(alienPlayer);
    }

    public void buyNextLevel(AlienPlayer alienPlayer, Technology technology) {
        techBuyer.buyNextLevel(alienPlayer, technology);
    }

    public void buyTechs(Fleet fleet, FleetBuildOption[] options) {
        techBuyer.buyTechs(fleet, options);
    }

    public int getCost(Technology technology, int level) {
        return techPrices.getCost(technology, level);
    }

    public int getMaxLevel(Technology technology) {
        return techPrices.getMaxLevel(technology);
    }

    public Fleet rollFleetLaunch(AlienPlayer alienPlayer, int turn){
        return fleetLauncher.rollFleetLaunch(alienPlayer, turn);
    }
}
