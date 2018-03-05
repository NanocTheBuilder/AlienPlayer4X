package com.thilian.se4x.robot.game;

import static com.thilian.se4x.robot.game.enums.FleetType.RAIDER_FLEET;
import static com.thilian.se4x.robot.game.enums.FleetType.REGULAR_FLEET;

import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

public class FleetLauncher {
    
    private Game game;


    public FleetLauncher(Game game) {
        this.game = game;
    }

    public Fleet rollFleetLaunch(AlienPlayer ap, int turn) {
        AlienEconomicSheet economicSheet = ap.getEconomicSheet();
        int currentFleetCP = economicSheet.getFleetCP();
        if (currentFleetCP >= ShipType.SCOUT.getCost()) {
            int roll = getFleetLaunchRoll(ap);
            if (roll <= economicSheet.getFleetLaunch(turn)) {
                Fleet fleet = new Fleet(ap, REGULAR_FLEET, currentFleetCP);
                if (shouldBuildRaiderFleet(ap)) {
                    fleet.setFleetType(RAIDER_FLEET);
                    game.scenario.buildFleet(fleet);
                }
                int cpSpent = fleet.getFleetType().equals(RAIDER_FLEET) ? fleet.getBuildCost() : fleet.getFleetCP();
                economicSheet.spendFleetCP(cpSpent);
                return fleet;
            }
        }
        return null;
    }

    private int getFleetLaunchRoll(AlienPlayer ap) {
        int currentFleetCP = ap.getEconomicSheet().getFleetCP();
        int roll = game.roller.roll();
        if ((currentFleetCP >= 27
                && ap.getLevel(Technology.FIGHTERS) > game.getSeenLevel(Technology.POINT_DEFENSE))
                || shouldBuildRaiderFleet(ap))
            roll -= 2;
        return roll;
    }


    private boolean shouldBuildRaiderFleet(AlienPlayer ap) {
        int currentFleetCP = ap.getEconomicSheet().getFleetCP();
        return currentFleetCP >= 12
                && ap.getLevel(Technology.CLOAKING) > game.getSeenLevel(Technology.SCANNER);
    }

}
