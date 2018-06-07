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

package com.thilian.se4x.robot.app.views;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thilian.se4x.robot.app.fragments.PlayerFragment;
import com.thilian.se4x.robot.app.R;
import com.thilian.se4x.robot.app.SE4XGameActivity;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4Player;

public class FleetView extends RelativeLayout{

    private Fleet fleet;

    public FleetView(final PlayerFragment fragment, final Fleet fleet){
        super(fragment.gameActivity);
        this.setId(7133700 + fleet.getIndex());
        this.fleet = fleet;

        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
        li.inflate(R.layout.fleet_layout, this, true);

        Button firstCombatButton = findViewById(R.id.first_combat_button);
        firstCombatButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fleet.getAp() instanceof Scenario4Player){
                    fragment.gameActivity.showFirstCombatDialog(fleet, FleetView.this, fragment);
                }
                else {
                    fragment.firstCombatPushed(fleet, FleetView.this, false, false);
                }
            }
        });

        Button removeFleetButton = findViewById(R.id.remove_fleet_button);
        removeFleetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.onFleetRemoved(fleet.getIndex(), FleetView.this);
            }
        });

        update();
    }

    public void update(){
        SE4XGameActivity activity = (SE4XGameActivity) getContext();
        TextView fleetNameText = findViewById(R.id.fleet_name_text);
        fleetNameText.setText(activity.getFleetName(fleet));

        TextView groupsText = findViewById(R.id.groups_text);
        groupsText.setText(activity.getFleetDetails(fleet));

        Button firstCombatButton = findViewById(R.id.first_combat_button);
        firstCombatButton.setVisibility(fleet.hadFirstCombat() ? INVISIBLE : VISIBLE);

        Button removeFleetButton = findViewById(R.id.remove_fleet_button);
        removeFleetButton.setVisibility(fleet.hadFirstCombat() ? VISIBLE : INVISIBLE);
    }

    public interface FleetRemoveListener{
        void onFleetRemoved(int index, FleetView view);
    }
}
