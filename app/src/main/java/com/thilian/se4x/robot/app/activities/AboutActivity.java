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
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.thilian.se4x.robot.app.R;
import com.thilian.se4x.robot.app.SE4XActivity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class AboutActivity extends SE4XActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        loadBackgroundImage();

        findViewById(R.id.license_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                String message;
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("LICENSE.txt")))){
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        sb.append(line).append("\n");
                    }
                    message = sb.toString();
                } catch (IOException e) {
                    message = e.getLocalizedMessage();
                }
                builder.setMessage(message);
                builder.show();
            }
        });

        findViewById(R.id.source_location_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/NanocTheBuilder/AlienPlayer4X"));
                startActivity(intent);
            }
        });

        findViewById(R.id.licenses_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutActivity.this, OssLicensesMenuActivity.class));
            }
        });
    }

}
