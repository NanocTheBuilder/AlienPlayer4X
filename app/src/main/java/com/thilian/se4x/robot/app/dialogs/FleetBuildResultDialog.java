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
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thilian.se4x.robot.app.R;
import com.thilian.se4x.robot.app.SE4XGameActivity;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.FleetBuildResult;
import com.thilian.se4x.robot.game.enums.Technology;

import org.w3c.dom.Text;

import java.util.Map;

public class FleetBuildResultDialog {
    private SE4XGameActivity activity;

    public FleetBuildResultDialog(SE4XGameActivity activity) {
        this.activity = activity;
    }

    public void show(FleetBuildResult result) {
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(8, 8, 8, 8);
        layout.setBackgroundColor(activity.getColor(result.getAlienPlayer()));

        String packageName = activity.getPackageName();
        Resources resources = activity.getResources();

        for (Map.Entry<Technology, Integer> entry : result.getNewTechs().entrySet()) {
            int sid = resources.getIdentifier(entry.getKey().toString(), "string", packageName);
            TextView textView = newTextView(resources.getString(sid, entry.getValue()));
            layout.addView(textView);
        }

        for(Fleet fleet : result.getNewFleets()){
            TextView textView = newTextView(TextUtils.concat(activity.getFleetName(fleet), "\n", activity.getFleetDetails(fleet)));
            layout.addView(textView);
        }

        if(result.getNewFleets().isEmpty() && result.getNewTechs().isEmpty()){
            TextView textView = newTextView(R.string.no_new_fleets);
            layout.addView(textView);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(layout)
                .setTitle(R.string.newFleets)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
    }

    private TextView newTextView(CharSequence text){
        TextView textView = newTextView();
        textView.setText(text);
        return textView;
    }

    private TextView newTextView(int textId){
        TextView textView = newTextView();
        textView.setText(textId);
        return  textView;
    }

    @NonNull
    private TextView newTextView() {
        TextView textView = new TextView(activity);
        textView.setTextSize(18);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        return textView;
    }


}
