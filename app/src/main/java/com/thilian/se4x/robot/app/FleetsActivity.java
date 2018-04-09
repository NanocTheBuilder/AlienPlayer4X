package com.thilian.se4x.robot.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4Player;

import java.util.List;

public class FleetsActivity extends Activity implements FleetView.FleetRevealListener{
    private AlienPlayer alienPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleets);

        Button okButton = findViewById(R.id.close_fleets_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FleetsActivity.this.onBackPressed();
            }
        });

        Button buildHomeDefenseButton = findViewById(R.id.build_home_defense_button);
        buildHomeDefenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildHomeDefenseButtonClicked();
            }
        });


        Button buildColonyDefenseButton = findViewById(R.id.build_colony_defense_button);
        buildColonyDefenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildColonyDefenseButtonClicked();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        int index = getIntent().getIntExtra(Intent.EXTRA_INDEX, -1);

        SE4XApplication app = (SE4XApplication) this.getApplication();
        alienPlayer = app.getGame().aliens.get(index);

        PlayerView playerView = findViewById(R.id.player_view);
        playerView.setAlienPlayer(alienPlayer);
        playerView.setGame(app.getGame());
        playerView.initTexts();
        playerView.setBackgroundColor();
        playerView.update();

        findViewById(R.id.build_colony_defense_button).setVisibility(alienPlayer instanceof Scenario4Player ? View.VISIBLE : View.GONE);

        LinearLayout fleets = (LinearLayout) findViewById(R.id.fleets);
        fleets.setBackgroundColor(Color.parseColor(alienPlayer.getColor().toString()));
        fleets.removeAllViews();
        for(Fleet fleet : alienPlayer.getFleets()){
            fleets.addView(new FleetView(this, fleet, this));
        }
    }

    private void buildHomeDefenseButtonClicked(){
        LinearLayout fleets = (LinearLayout) findViewById(R.id.fleets);
        List<Fleet> newFleets = alienPlayer.buildHomeDefense();
        if(newFleets.size() != 0) {
            SE4XApplication app = (SE4XApplication) this.getApplication();
            app.showNewFleets(this, newFleets);
            for (Fleet fleet : newFleets) {
                fleets.addView(new FleetView(this, fleet, this));
            }
            onFleetRevealed(null);
        }
    }
    private void buildColonyDefenseButtonClicked(){
        LinearLayout fleets = (LinearLayout) findViewById(R.id.fleets);
        List<Fleet> newFleets = ((Scenario4Player)alienPlayer).buildColonyDefense();
        if(newFleets.size() != 0) {
            SE4XApplication app = (SE4XApplication) this.getApplication();
            app.showNewFleets(this, newFleets);
            for (Fleet fleet : newFleets) {
                fleets.addView(new FleetView(this, fleet, this));
            }
            onFleetRevealed(null);
        }
    }

    @Override
    public void onFleetRevealed(Fleet fleet) {
        PlayerView playerView = findViewById(R.id.player_view);
        playerView.update();
    }
}
