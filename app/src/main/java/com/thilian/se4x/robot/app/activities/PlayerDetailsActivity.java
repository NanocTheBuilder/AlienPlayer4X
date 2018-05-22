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

package com.thilian.se4x.robot.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.thilian.se4x.robot.app.R;
import com.thilian.se4x.robot.app.SE4XGameActivity;
import com.thilian.se4x.robot.app.fragments.PlayerFragment;

public class PlayerDetailsActivity extends SE4XGameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.player_activity);
    }

    @Override
    protected void onStart() {
        int alienIndex = getIntent().getIntExtra(Intent.EXTRA_INDEX, -1);
        PlayerFragment fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.player_fragment);
        fragment.setAlienIndex(alienIndex);
        super.onStart();
    }

}
