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

public class FleetsActivity extends AppCompatActivity {
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

        Button buildDefenseButton = findViewById(R.id.build_defense_button);
        buildDefenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildDefenseButtonClicked();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        int index = getIntent().getIntExtra(Intent.EXTRA_INDEX, -1);

        SE4XApplication app = (SE4XApplication) this.getApplication();
        alienPlayer = app.getGame().aliens.get(index);

        LinearLayout fleets = (LinearLayout) findViewById(R.id.fleets);
        fleets.setBackgroundColor(Color.parseColor(alienPlayer.getColor().toString()));
        fleets.removeAllViews();
        for(Fleet fleet : alienPlayer.getFleets()){
            fleets.addView(new FleetView(this, fleet));
        }
    }

    private void buildDefenseButtonClicked(){
        LinearLayout fleets = (LinearLayout) findViewById(R.id.fleets);
        List<Fleet> newFleets = alienPlayer.buildDefense();
        if(newFleets.size() != 0) {
            SE4XApplication app = (SE4XApplication) this.getApplication();
            app.showNewFleets(this, newFleets);
            for (Fleet fleet : newFleets) {
                fleets.addView(new FleetView(this, fleet));
            }
        }
    }

}
