/*
 *  Copyright (C) 2018 Balázs Péter
 *
 *  This file is part of Alien Player 4X.
 *
 *  Alien Player 4X is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Alien Player 4X is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Alien Player 4X.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.thilian.se4x.robot.app.dialogs;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.thilian.se4x.robot.app.views.FleetView;
import com.thilian.se4x.robot.app.R;
import com.thilian.se4x.robot.app.SE4XGameActivity;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpSoloScenario;

public class FirstCombatDialog{
    SE4XGameActivity activity;

    public FirstCombatDialog(SE4XGameActivity activity) {
        this.activity = activity;
    }

    public void show(final Fleet fleet, final FleetView fleetView, final FirstCombatListener listener) {
        int id = activity.getGame().scenario instanceof VpSoloScenario ?
            R.array.first_combat_options_vp : R.array.first_combat_options_s4;
        final boolean[] selection = new boolean[2];
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.first_combat)
                .setMultiChoiceItems(id, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean checked) {
                        selection[which] = checked;
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.firstCombatPushed(fleet, fleetView, selection[0], selection[1]);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    public interface FirstCombatListener{
        void firstCombatPushed(Fleet fleet, FleetView view, boolean abovePlanet, boolean enemyNPA);
    }
}
