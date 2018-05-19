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

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.thilian.se4x.robot.app.R;
import com.thilian.se4x.robot.app.SE4XGameActivity;
import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.Technology;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4;

public class SeenTechnologiesDialog extends DialogFragment{
    ConstraintLayout dialogView;

    private SE4XGameActivity getSe4xActivity(){
        return (SE4XGameActivity) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getActivity().getSystemService(service);

        dialogView = new ConstraintLayout(getActivity());
        li.inflate(R.layout.seen_technologies_activity, dialogView, true);

        createTechnologyText(dialogView, Technology.CLOAKING);
        createTechnologyText(dialogView, Technology.SCANNER);
        createTechnologyText(dialogView, Technology.POINT_DEFENSE);

        createCheckbox(dialogView, R.id.fighters_checkbox, Seeable.FIGHTERS);
        createCheckbox(dialogView, R.id.mines_checkbox, Seeable.MINES);

        createCheckbox(dialogView, R.id.boarding_checkbox, Seeable.BOARDING_SHIPS);
        createCheckbox(dialogView, R.id.veterans_checkbox, Seeable.VETERANS);
        createCheckbox(dialogView, R.id.size3_checkbox, Seeable.SIZE_3_SHIPS);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(dialogView)
            .setTitle("Select seen technologies")
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });

        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();

        SE4XGameActivity activity = getSe4xActivity();

        initTechnologyText(Technology.CLOAKING);
        initTechnologyText(Technology.SCANNER);
        initTechnologyText(Technology.POINT_DEFENSE);

        initCheckbox(R.id.fighters_checkbox, Seeable.FIGHTERS);
        initCheckbox(R.id.mines_checkbox, Seeable.MINES);

        if (activity.getGame().scenario instanceof Scenario4) {
            initCheckbox(R.id.boarding_checkbox, Seeable.BOARDING_SHIPS);
            initCheckbox(R.id.veterans_checkbox, Seeable.VETERANS);
            initCheckbox(R.id.size3_checkbox, Seeable.SIZE_3_SHIPS);
        } else {
            dialogView.findViewById(R.id.boarding_checkbox).setVisibility(View.GONE);
            dialogView.findViewById(R.id.veterans_checkbox).setVisibility(View.GONE);
            dialogView.findViewById(R.id.size3_checkbox).setVisibility(View.GONE);
        }
    }

    public void initTechnologyText(Technology technology) {
        SE4XGameActivity activity = getSe4xActivity();
        String textViewName = String.format("%s_text", technology.toString().toLowerCase());
        int textViewId = activity.getResources().getIdentifier(textViewName, "id", activity.getPackageName());
        if (textViewId != 0) {
            TextView textView = dialogView.findViewById(textViewId);
            int sid = activity.getResources().getIdentifier(technology.toString(), "string", activity.getPackageName());
            int level = activity.getGame().getSeenLevel(technology);
            textView.setText(getResources().getString(sid, level));
        } else {
            System.out.println(String.format("Not found <%s>", textViewName));
        }
    }

    private void createTechnologyText(View view, final Technology technology) {
        String textViewName = String.format("%s_text", technology.toString().toLowerCase());
        int textViewId = getResources().getIdentifier(textViewName, "id", getActivity().getPackageName());
        if (textViewId != 0) {
            TextView textView = view.findViewById(textViewId);
            textView.setClickable(true);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPickerDialog(technology);
                }
            });
        } else {
            System.out.println(String.format("Not found <%s>", textViewName));
        }
    }

    private void showPickerDialog(final Technology technology) {
        final SE4XGameActivity activity = getSe4xActivity();
        new PickerDialog(activity).showSeenLevelPickerDialog(technology, new PickerDialog.PickerClickAction() {
            @Override
            public void action(int value) {
                activity.getGame().setSeenLevel(technology, value);
                initTechnologyText(technology);
            }
        });
    }

    private void createCheckbox(View view, int id, Seeable seeable) {
        CheckBox checkBox = view.findViewById(id);
        checkBox.setOnClickListener(new CheckBoxListener(seeable));
    }

    private void initCheckbox(int id, Seeable seeable) {
        final SE4XGameActivity activity = getSe4xActivity();
        CheckBox checkBox = dialogView.findViewById(id);
        checkBox.setVisibility(View.VISIBLE);
        checkBox.setChecked(activity.getGame().isSeenThing(seeable));
    }

    private class CheckBoxListener implements View.OnClickListener {

        private Seeable seeable;

        CheckBoxListener(Seeable seeable) {
            this.seeable = seeable;
        }

        @Override
        public void onClick(View view) {
        final SE4XGameActivity activity = getSe4xActivity();
            boolean checked = ((CheckBox) view).isChecked();
            if (checked) {
                activity.getGame().addSeenThing(seeable);
            } else {
                activity.getGame().removeSeenThing(seeable);
            }
        }
    }
}
