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

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class SE4XActivity extends FragmentActivity {
    protected void loadBackgroundImage() {
        ImageView background = findViewById(R.id.background_image);
        Glide.with(this).load(R.drawable.smc_wing_full_2560).into(background);
    }

    protected String loadAssetText(String fileName) {
        String text;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(fileName)))){
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                sb.append(line).append("\n");
            }
            text = sb.toString();
        } catch (IOException e) {
            text = e.getLocalizedMessage();
        }
        return text;
    }

    protected void initOkButton() {
        Button okButton = findViewById(android.R.id.closeButton);
        if(okButton != null) {
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SE4XActivity.this.onBackPressed();
                }
            });
        }
    }
}
