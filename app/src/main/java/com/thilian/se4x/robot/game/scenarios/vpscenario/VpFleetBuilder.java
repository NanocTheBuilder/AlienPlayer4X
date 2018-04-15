package com.thilian.se4x.robot.game.scenarios.vpscenario;

import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.scenarios.scenario4.FleetBuilder;

import static com.thilian.se4x.robot.game.enums.FleetBuildOption.COMBAT_IS_ABOVE_PLANET;
import static com.thilian.se4x.robot.game.enums.FleetType.EXPANSION_FLEET;

public class VpFleetBuilder extends FleetBuilder {
    public VpFleetBuilder(Game game) {
        super(game);
    }

    @Override
    protected void buildOneFullyLoadedTransport(Fleet fleet, FleetBuildOption... options) {
        if(EXPANSION_FLEET.equals(fleet.getFleetType())){
            super.buildOneFullyLoadedTransport(fleet);
        }
        else if(fleet.getRemainingCP() >= 40){
            int roll = game.roller.roll();
            if(FleetBuildOption.isOption(COMBAT_IS_ABOVE_PLANET, options))
                roll -=2;
            if(roll <= 5){
                fleet.addGroup(new Group(ShipType.TRANSPORT, 1));
                for (Group group : buildGroundUnits(fleet)) {
                    fleet.addGroup(group);
                }
            }
        }
    }
}
