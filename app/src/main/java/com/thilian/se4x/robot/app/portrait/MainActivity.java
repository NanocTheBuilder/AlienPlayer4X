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

package com.thilian.se4x.robot.app.portrait;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.thilian.se4x.robot.app.PlayerView;
import com.thilian.se4x.robot.app.R;
import com.thilian.se4x.robot.app.SE4XGameActivity;
import com.thilian.se4x.robot.app.dialogs.SeenTechnologiesDialog;
import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.EconPhaseResult;

import java.util.List;

public class MainActivity extends SE4XGameActivity {
    private static final String tag = SE4XGameActivity.class.getSimpleName();
    private boolean exitGame = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        loadBackgroundImage();

        LinearLayout players = findViewById(R.id.players);

        for (int i = 0; i < getGame().aliens.size(); i++) {
            AlienPlayer ap = getGame().aliens.get(i);
            PlayerView playerView = new PlayerView(this, getGame(), ap);
            final int finalI = i;
            playerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                    intent.putExtra(Intent.EXTRA_INDEX, finalI);
                    startActivity(intent);
                }
            });
            players.addView(playerView);
        }

        final Button econButton = findViewById(R.id.econPhase);
        econButton.setText(getString(R.string.econPhase, getGame().currentTurn));
        econButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<EconPhaseResult> results = getGame().doEconomicPhase();
                if(results.size() != 0) {
                    showEconPhaseResult(results);
                    updatePlayerViews();
                }
                if(getGame().currentTurn  <= 20)
                    econButton.setText(getString(R.string.econPhase, getGame().currentTurn));
                else {
                    econButton.setText(getString(R.string.gameOver));
                    econButton.setEnabled(false);
                }
            }});

        Button setSeenLevelButton = findViewById(R.id.set_seen_levels_button);
        setSeenLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SeenTechnologiesDialog().show(getFragmentManager(), "seenLevels");
                //Intent intent = new Intent(MainActivity.this, SeenTechnologyActivity.class);
                //startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePlayerViews();
    }

    private void updatePlayerViews() {
        LinearLayout players = findViewById(R.id.players);
        PlayerView view;
        for(int i = 0; i < players.getChildCount(); i++){
            view = (PlayerView) players.getChildAt(i);
            view.update(getGame(), getGame().aliens.get(i), isShowDetails());
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.areYouSure)
            .setMessage(R.string.finish_game)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    deleteGame();
                    MainActivity.super.finish();
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            })
            .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(exitGame){
            deleteGame();
        }
    }
}
