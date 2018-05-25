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

import android.content.Context;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.thilian.se4x.robot.app.R;
import com.thilian.se4x.robot.app.SE4XGameActivity;
import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.enums.Technology;

public class PickerDialog {
    SE4XGameActivity activity;

    public PickerDialog(SE4XGameActivity activity) {
        this.activity = activity;
    }

    public void showLevelPickerDialog(final AlienPlayer alienPlayer, final Technology technology, PickerClickAction action){
        String sid = String.format("%s_label", technology.toString());
        show(
                activity.getResources().getIdentifier(sid, "string", activity.getPackageName()),
                activity.getGame().scenario.getStartingLevel(technology),
                activity.getGame().scenario.getMaxLevel(technology),
                alienPlayer.getLevel(technology),
                action);
    }

    public void showSeenLevelPickerDialog(Technology technology, PickerClickAction action){
        String sid = String.format("%s_label", technology.toString());
        show(
                activity.getResources().getIdentifier(sid, "string", activity.getPackageName()),
                activity.getGame().scenario.getStartingLevel(technology),
                activity.getGame().scenario.getMaxLevel(technology),
                activity.getGame().getSeenLevel(technology),
                action);
    }

    public void show(int labelString, int minValue, int maxValue, int value, final PickerClickAction action) {
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) activity.getSystemService(service);
        ConstraintLayout levelPickerView = new ConstraintLayout(activity);
        li.inflate(R.layout.level_picker_dialog, levelPickerView, true);

        TextView label = levelPickerView.findViewById(R.id.technology_label);
        label.setText(labelString);

        final NumberPicker picker = levelPickerView.findViewById(R.id.level_picker);
        picker.setMinValue(minValue);
        picker.setMaxValue(maxValue);
        picker.setValue(value);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Select value")
                .setView(levelPickerView)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        action.action(picker.getValue());
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    public interface PickerClickAction{
        void action(int value);
    }

}
