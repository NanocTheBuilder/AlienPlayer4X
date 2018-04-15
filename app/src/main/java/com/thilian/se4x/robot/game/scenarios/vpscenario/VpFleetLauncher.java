package com.thilian.se4x.robot.game.scenarios.vpscenario;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.FleetLauncher;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;

import static com.thilian.se4x.robot.game.enums.FleetType.EXPANSION_FLEET;
import static com.thilian.se4x.robot.game.enums.FleetType.EXTERMINATION_FLEET;
import static com.thilian.se4x.robot.game.enums.FleetType.REGULAR_FLEET;

public class VpFleetLauncher extends FleetLauncher {
    public VpFleetLauncher(Game game) {
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
                fleet.setFleetType(EXTERMINATION_FLEET);
            }
        }

        if (fleet.getFleetType().equals(EXPANSION_FLEET) && bank >= 50) {
            fleet.addFleetCp(50);
            vpSheet.spendBank(50);
        }
    }
}
