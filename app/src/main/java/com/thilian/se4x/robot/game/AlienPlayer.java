package com.thilian.se4x.robot.game;

//import android.support.annotation.Nullable;
//import android.util.Log;
import static com.thilian.se4x.robot.game.enums.FleetType.DEFENSE_FLEET;
import static com.thilian.se4x.robot.game.enums.FleetType.REGULAR_FLEET;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thilian.se4x.robot.game.enums.FleetBuildOptions;
import com.thilian.se4x.robot.game.enums.FleetType;
import com.thilian.se4x.robot.game.enums.PlayerColor;
import com.thilian.se4x.robot.game.enums.ShipType;
import com.thilian.se4x.robot.game.enums.Technology;

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

    public Fleet makeEconRoll(int turn) {
        for (int i = 0; i < economicSheet.getEconRolls(turn) + getExtraEconRoll(turn); i++)
            economicSheet.applyRoll(turn, game.roller.roll());
        Fleet newFleet = game.scenario.rollFleetLaunch(this, turn);
        if (newFleet != null) {
            buyNextMoveLevel();
        }
        return newFleet;
    }

    protected int getExtraEconRoll(int turn) {
        return economicSheet.getExtraEcon(turn);
    }

    void buyTechs(Fleet fleet, FleetBuildOptions... options) {
        setJustPurchasedCloaking(false);
        game.scenario.buyTechs(fleet, options);
    }

    public void buildFleet(Fleet fleet, FleetBuildOptions... options) {
        buyTechs(fleet, options);
        game.scenario.buildFleet(fleet);
        economicSheet.addFleetCP(fleet.getFleetCP() - fleet.getBuildCost());
    }

    public void destroyFleet(Fleet fleet) {
        fleets.remove(fleet);
    }

    public List<Fleet> buildHomeDefense() {
        List<Fleet> newFleets = new ArrayList<>();
        int currentFleetCP = economicSheet.getFleetCP();
        if (currentFleetCP >= ShipType.SCOUT.getCost()) {
            Fleet fleet = new Fleet(this, DEFENSE_FLEET, currentFleetCP);
            buyTechs(fleet, FleetBuildOptions.COMBAT_IS_ABOVE_PLANET);
            game.scenario.buildFleet(fleet);
            fleet.setFleetType(REGULAR_FLEET);
            economicSheet.spendFleetCP(fleet.getBuildCost());
            newFleets.add(fleet);
        }

        if (economicSheet.getDefCP() >= ShipType.MINE.getCost()) {
            Fleet fleet = game.scenario.buildHomeDefense(this);
            economicSheet.spendDefCP(fleet.getBuildCost());
            newFleets.add(fleet);
        }
        return newFleets;
    }

    public void buyNextMoveLevel() {
        if (game.roller.roll() <= 4) {
            game.scenario.buyNextLevel(this, Technology.MOVE);
        }
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

    // @Nullable
    private Fleet findFleetByName(String name, FleetType fleetType) {
        for (Fleet fleet : fleets) {
            if (fleet.getName().equals(name) && fleet.getFleetType().equals(fleetType))
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
}
