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

package com.thilian.se4x.robot.game.buyers.technology;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.buyers.TechBuyer;
import com.thilian.se4x.robot.game.enums.Technology;

import static com.thilian.se4x.robot.game.enums.Technology.ATTACK;
import static com.thilian.se4x.robot.game.enums.Technology.DEFENSE;

public class TacticsBuyer extends TechBuyer {
    public TacticsBuyer(Game game){
        super(game, Technology.TACTICS);
    }

    @Override
    public boolean canBuyNextLevel(AlienPlayer ap) {
        int min = Math.min(ap.getLevel(ATTACK), ap.getLevel(DEFENSE));
        if (min < 2) {
            return game.scenario.canBuyNextLevel(ap, ATTACK) | game.scenario.canBuyNextLevel(ap, DEFENSE);
        }
        else {
            return super.canBuyNextLevel(ap);
        }
    }

    @Override
    public void buyNextLevel(AlienPlayer ap) {
        int oldAttack = ap.getLevel(ATTACK);
        int oldDefense = ap.getLevel(DEFENSE);

        if (oldAttack< 2)
            game.scenario.buyNextLevel(ap, ATTACK);
        if (ap.getLevel(ATTACK) == oldAttack && oldDefense < 2)
            game.scenario.buyNextLevel(ap, DEFENSE);
        if(ap.getLevel(ATTACK) == oldAttack && ap.getLevel(DEFENSE) == oldDefense)
            super.buyNextLevel(ap);
    }
}
