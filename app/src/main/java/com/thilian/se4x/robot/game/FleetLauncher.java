package com.thilian.se4x.robot.game;

import static com.thilian.se4x.robot.game.enums.FleetBuildOption.HOME_DEFENSE;
import static com.thilian.se4x.robot.game.enums.FleetType.RAIDER_FLEET;
import static com.thilian.se4x.robot.game.enums.FleetType.REGULAR_FLEET;

import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

public class FleetLauncher {
    
    protected Game game;


    public FleetLauncher(Game game) {
        this.game = game;
    }

    public Fleet rollFleetLaunch(AlienPlayer ap, int turn) {
        AlienEconomicSheet economicSheet = ap.getEconomicSheet();
        int roll = getFleetLaunchRoll(ap);
        if (roll <= economicSheet.getFleetLaunch(turn)) {
            return launchFleet(ap, turn);
        }
        return null;
    }

    public Fleet launchFleet(AlienPlayer ap, int turn, FleetBuildOption... options) {
        AlienEconomicSheet economicSheet = ap.getEconomicSheet();
        int currentFleetCP = economicSheet.getFleetCP();
        if (currentFleetCP >= ShipType.SCOUT.getCost()) {
            Fleet fleet = new Fleet(ap, REGULAR_FLEET, currentFleetCP);
            if (shouldLaunchRaiderFleet(ap, options)) {
                fleet.setFleetType(RAIDER_FLEET);
                game.scenario.buildFleet(fleet);
            }
            int cpSpent = fleet.getFleetType().equals(RAIDER_FLEET) ? fleet.getBuildCost() : fleet.getFleetCP();
            economicSheet.spendFleetCP(cpSpent);
            return fleet;
        }
        return null;
    }

    private int getFleetLaunchRoll(AlienPlayer ap) {
        int currentFleetCP = ap.getEconomicSheet().getFleetCP();
        int roll = game.roller.roll();
        if ((currentFleetCP >= 27
                && ap.getLevel(Technology.FIGHTERS) > game.getSeenLevel(Technology.POINT_DEFENSE))
                || shouldLaunchRaiderFleet(ap))
            roll -= 2;
        return roll;
    }


    private boolean shouldLaunchRaiderFleet(AlienPlayer ap, FleetBuildOption... options) {
        if(FleetBuildOption.isOption(HOME_DEFENSE, options))
            return false;
        int currentFleetCP = ap.getEconomicSheet().getFleetCP();
        return currentFleetCP >= 12
                && ap.getLevel(Technology.CLOAKING) > game.getSeenLevel(Technology.SCANNER);
    }

}
