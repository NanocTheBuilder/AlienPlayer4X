package com.thilian.se4x.robot.game.basegame;

import static com.thilian.se4x.robot.game.enums.FleetType.RAIDER_FLEET;
import static com.thilian.se4x.robot.game.enums.Technology.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.TechnologyBuyer;
import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.Technology;

public class BaseGameTechnologyBuyer extends TechnologyBuyer {

    private static int[] SHIP_SIZE_ROLL_TABLE = new int[]{0, 10, 7, 6, 5, 3};

    private static Map<Technology, Integer[]> TECHNOLOGY_ROLL_TABLE = new HashMap<>();

    {
        addToRollTable(ATTACK, 1, 2);
        addToRollTable(DEFENSE, 3, 4);
        addToRollTable(TACTICS, 5);
        addToRollTable(CLOAKING, 6);
        addToRollTable(SCANNER, 7);
        addToRollTable(FIGHTERS, 8);
        addToRollTable(POINT_DEFENSE, 9);
        addToRollTable(MINE_SWEEPER, 10);
    }

    private static void addToRollTable(Technology technology, Integer... values) {
        TECHNOLOGY_ROLL_TABLE.put(technology, values);
    }

    public BaseGameTechnologyBuyer(Game game) {
        super(game);
    }

    @Override
    protected int[] getShipSizeRollTable() {
        return SHIP_SIZE_ROLL_TABLE;
    }

    @Override
    public void buyOptionalTechs(Fleet fleet) {
        AlienPlayer ap = fleet.getAp();
        buyPointDefenseIfNeeded(ap);
        buyMineSweepIfNeeded(ap);
        buyScannerIfNeeded(ap);
        buyShipSizeIfRolled(ap);
        buyFightersIfNeeded(ap);
        buyCloakingIfNeeded(fleet);
    }

    @Override
    public void spendRemainingTechCP(Fleet fleet) {
        AlienPlayer ap = fleet.getAp();
        while (true) {
            List<Integer> buyable = findBuyableTechs(fleet);
            if (buyable.isEmpty())
                break;
            int roll = game.roller.roll(buyable.size());
            switch (buyable.get(roll - 1)) {
                case 1:
                case 2:
                    buyNextLevel(ap, ATTACK);
                    break;
                case 3:
                case 4:
                    buyNextLevel(ap, DEFENSE);
                    break;
                case 5:
                    if (ap.getLevel(ATTACK) < 2 && canBuyNextLevel(ap, ATTACK))
                        buyNextLevel(ap, ATTACK);
                    else if (ap.getLevel(DEFENSE) < 2 && canBuyNextLevel(ap, DEFENSE))
                        buyNextLevel(ap, DEFENSE);
                    else
                        buyNextLevel(ap, TACTICS);
                    break;
                case 6:
                    buyNextLevel(ap, CLOAKING);
                    ap.setJustPurchasedCloaking(true);
                    break;
                case 7:
                    buyNextLevel(ap, SCANNER);
                    break;
                case 8:
                    buyNextLevel(ap, FIGHTERS);
                    break;
                case 9:
                    buyNextLevel(ap, POINT_DEFENSE);
                    break;
                case 10:
                    buyNextLevel(ap, MINE_SWEEPER);
                    break;
            }
        }
    }

    private List<Integer> findBuyableTechs(Fleet fleet) {
        List<Integer> buyable = new ArrayList<>();
        for (Technology technology : TECHNOLOGY_ROLL_TABLE.keySet()) {
            if (canBuyNextLevel(fleet, technology)) {
                buyable.addAll(Arrays.asList(TECHNOLOGY_ROLL_TABLE.get(technology)));
            }
        }
        Collections.sort(buyable);
        return buyable;
    }

}
