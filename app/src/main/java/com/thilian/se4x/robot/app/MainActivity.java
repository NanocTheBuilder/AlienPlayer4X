package com.thilian.se4x.robot.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout players = (LinearLayout) findViewById(R.id.players);

        final SE4XApplication app = (SE4XApplication) getApplication();
        for (int i = 0; i < app.getGame().aliens.size(); i++) {
            AlienPlayer ap = app.getGame().aliens.get(i);
            PlayerView playerView = new PlayerView(this, ap);
            final int finalI = i;
            playerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, FleetsActivity.class);
                    intent.putExtra(Intent.EXTRA_INDEX, finalI);
                    startActivity(intent);
                }
            });
            players.addView(playerView);
        }

        final Button econButton = (Button) findViewById(R.id.econPhase);
        setButtonText(app, econButton);
        econButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SE4XApplication app = (SE4XApplication) getApplication();
                List<Fleet> newFleets = app.getGame().doEconomicPhase();
                if(newFleets.size() != 0) {
                    app.showNewFleets(MainActivity.this, newFleets);
                    LinearLayout players = (LinearLayout) findViewById(R.id.players);
                    for (int i = 0; i < players.getChildCount(); i++) {
                        ((PlayerView) players.getChildAt(i)).update();
                    }
                }
                setButtonText(app, econButton);
            }});

        Button setSeenLevelButton = findViewById(R.id.set_seen_levels_button);
        setSeenLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SeenTechnologyActivity.class);
                startActivity(intent);
            }
        });
    }


    private void setButtonText(SE4XApplication app, Button econButton) {
        econButton.setText(getString(R.string.econPhase, app.getGame().currentTurn));
        if(app.getGame().currentTurn >= 20)
            econButton.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LinearLayout players = (LinearLayout) findViewById(R.id.players);
        PlayerView view;
        for(int i = 0; i < players.getChildCount(); i++){
            view = (PlayerView) players.getChildAt(i);
            view.update();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.areYouSure);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }
}
