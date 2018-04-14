package com.thilian.se4x.robot.game.scenarios.vpscenario;

import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.Technology;

import junit.framework.Assert;

import org.junit.Test;

import static com.thilian.se4x.robot.game.enums.FleetType.EXPANSION_FLEET;
import static com.thilian.se4x.robot.game.enums.FleetType.EXTERMINATION_FLEET;
import static com.thilian.se4x.robot.game.enums.FleetType.RAIDER_FLEET;
import static com.thilian.se4x.robot.game.enums.Technology.CLOAKING;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class VpFleetLaunchTest extends VpScenarioFixture{

    @Test
    public void noCpLaunchesExpansionFleetFromBank(){
        assertFleetLaunched(EXPANSION_FLEET, "1", 50);
        assertBank(50);

        assertFleetLaunched(EXPANSION_FLEET, "2", 50);
        assertBank(0);

        assertNull(fleetLauncher.rollFleetLaunch(ap, 2));
        assertBank(0);
    }

    public void assertBank(int expectedBank) {
        assertEquals(expectedBank, ((VpEconomicSheet) sheet).getBank());
    }

    @Test
    public void dontSpendMoreFromTheBankThanAvailable(){
        VpEconomicSheet vpSheet = (VpEconomicSheet)sheet;
        vpSheet.spendBank(40);
        assertEquals(60, vpSheet.getBank());
        assertFleetLaunched(EXPANSION_FLEET, "1", 50);
        assertBank(10);
        assertNull(fleetLauncher.rollFleetLaunch(ap, 2));
        assertBank(10);
    }

    @Test
    public void launchExpansionFleetIfRolled(){
        VpEconomicSheet vpSheet = (VpEconomicSheet)sheet;

        vpSheet.setFleetCP(10);
        roller.mockRoll(1); //launch
        roller.mockRoll(7);
        assertFleetLaunched(EXPANSION_FLEET, "1", 60);
        assertBank(50);

        vpSheet.setFleetCP(10);
        vpSheet.setBank(60);
        roller.mockRoll(1); //launch
        roller.mockRoll(5);
        assertFleetLaunched(8, EXPANSION_FLEET, "2", 60);
        assertBank(10);

        vpSheet.setFleetCP(10);
        vpSheet.setBank(60);
        roller.mockRoll(1); //launch
        roller.mockRoll(3);
        assertFleetLaunched(11, EXPANSION_FLEET, "3", 60);
        assertBank(10);
    }

    @Test
    public void launchExterminationFleet(){
        VpEconomicSheet vpSheet = (VpEconomicSheet)sheet;

        vpSheet.setFleetCP(10);
        vpSheet.setBank(60);
        roller.mockRoll(1); //launch
        roller.mockRoll(8);
        assertFleetLaunched(EXTERMINATION_FLEET, "1", 10);
        assertBank(60);

        vpSheet.setFleetCP(10);
        roller.mockRoll(1); //launch
        roller.mockRoll(6);
        assertFleetLaunched(8, EXTERMINATION_FLEET, "2", 10);
        assertBank(60);


        vpSheet.setFleetCP(10);
        roller.mockRoll(1); //launch
        roller.mockRoll(4);
        assertFleetLaunched(11, EXTERMINATION_FLEET, "3", 10);
        assertBank(60);
    }

    @Test
    public void launchRaiderFleet(){
        VpEconomicSheet vpSheet = (VpEconomicSheet)sheet;

        vpSheet.setFleetCP(12);
        vpSheet.setBank(10);
        ap.setLevel(CLOAKING, 1);
        roller.mockRoll(1); //launch
        assertFleetLaunched(RAIDER_FLEET, "1", 12);
        assertBank(10);
    }

    public void assertFleetLaunched(FleetType fleetType, String name, int fleetCP) {
        assertFleetLaunched(2, fleetType, name, fleetCP);
    }

    private void assertFleetLaunched(int turn, FleetType fleetType, String name, int fleetCP) {
        Fleet fleet = fleetLauncher.rollFleetLaunch(ap, turn);
        assertEquals(fleetType, fleet.getFleetType());
        assertEquals(name, fleet.getName());
        assertEquals(fleetCP, fleet.getFleetCP());
    }
}
