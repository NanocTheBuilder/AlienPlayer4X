package com.thilian.se4x.robot.game;

import java.util.Collection;

import com.thilian.se4x.robot.game.enums.FleetBuildOptions;
import com.thilian.se4x.robot.game.enums.Technology;

public abstract class Scenario{

    //TODO: private these
    public TechnologyBuyer techBuyer;
    public TechnologyPrices techPrices;
    public FleetBuilder fleetBuilder;
    public DefenseBuilder defenseBuilder;
    public FleetLauncher fleetLauncher;

    public abstract void init(Game game);

    public Collection<Technology> getAvailableTechs() {
        return techPrices.getAvailableTechs();
    }

    public int getStartingLevel(Technology technology) {
        return techPrices.getStartingLevel(technology);
    }

    public void buildFleet(Fleet fleet) {
        fleetBuilder.buildFleet(fleet);
    }

    public Fleet buildHomeDefense(AlienPlayer alienPlayer) {
        return defenseBuilder.buildHomeDefense(alienPlayer);
    }

    public void buyNextLevel(AlienPlayer alienPlayer, Technology technology) {
        techBuyer.buyNextLevel(alienPlayer, technology);
    }

    public void buyTechs(Fleet fleet, FleetBuildOptions[] options) {
        techBuyer.buyTechs(fleet, options);
    }

    public int getCost(Technology technology, int level) {
        return techPrices.getCost(technology, level);
    }

    public int getMaxLevel(Technology technology) {
        return techPrices.getMaxLevel(technology);
    }

    public Fleet buildColonyDefense(AlienPlayer alienPlayer) {
        return defenseBuilder.buildColonyDefense(alienPlayer);
    }

    public Fleet rollFleetLaunch(AlienPlayer alienPlayer, int turn){
        return fleetLauncher.rollFleetLaunch(alienPlayer, turn);
    }
}
