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

package com.thilian.se4x.robot.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.thilian.se4x.robot.app.portrait.MainActivity;

public class MainMenuActivity extends SE4XActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        loadBackgroundImage();

        initOnClickListener(R.id.resume_text, MainActivity.class);
        initOnClickListener(R.id.new_game_text, NewGameActivity.class);
        initOnClickListener(R.id.settings_text, SettingsActivity.class);
        //TODO initOnClickListener(R.id.about_text, AboutActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView resumeText = findViewById(R.id.resume_text);
        resumeText.setVisibility((new GameSaver().isSaveExists(this)) ? View.VISIBLE : View.GONE);
    }

    private void initOnClickListener(int id, final Class<? extends Activity> activityClass) {
        final TextView textView = findViewById(id);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, activityClass));
            }
        });
    }
}
