package com.thilian.se4x.robot.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.FleetBuildResult;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4Player;

import java.util.ArrayList;
import java.util.List;

import static com.thilian.se4x.robot.game.enums.FleetBuildOption.COMBAT_IS_ABOVE_PLANET;
import static com.thilian.se4x.robot.game.enums.FleetBuildOption.COMBAT_WITH_NPAS;

public class FleetsActivity extends SE4XActivity implements FleetView.FleetRevealListener, FirstCombatDialog.FirstCombatListener{
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

        alienPlayer = getGame().aliens.get(index);

        PlayerView playerView = findViewById(R.id.player_view);
        playerView.setGame(getGame());
        playerView.setAlienPlayer(alienPlayer);
        playerView.initTexts(isShowDetails());
        playerView.initEliminateButton(alienPlayer);
        playerView.setBackgroundColor();
        playerView.update(isShowDetails());

        findViewById(R.id.build_colony_defense_button).setVisibility(alienPlayer instanceof Scenario4Player ? View.VISIBLE : View.GONE);

        LinearLayout fleets = (LinearLayout) findViewById(R.id.fleets);
        fleets.setBackgroundColor(Color.parseColor(alienPlayer.getColor().toString()));
        fleets.removeAllViews();
        for(Fleet fleet : alienPlayer.getFleets()){
            fleets.addView(new FleetView(this, fleet, this));
        }
    }

    private void buildHomeDefenseButtonClicked(){
        FleetBuildResult result = alienPlayer.buildHomeDefense();
        LinearLayout fleets = (LinearLayout) findViewById(R.id.fleets);
        for (Fleet fleet : result.getNewFleets()) {
            fleets.addView(new FleetView(this, fleet, this));
        }
        onFleetRevealed(null);
        showFleetBuildResult(result);
    }

    private void buildColonyDefenseButtonClicked(){
        FleetBuildResult result = ((Scenario4Player)alienPlayer).buildColonyDefense();
        LinearLayout fleets = (LinearLayout) findViewById(R.id.fleets);
        for (Fleet fleet : result.getNewFleets()) {
            fleets.addView(new FleetView(this, fleet, this));
        }
        onFleetRevealed(null);
        showFleetBuildResult(result);
    }

    @Override
    public void onFleetRevealed(Fleet fleet) {
        PlayerView playerView = findViewById(R.id.player_view);
        playerView.update(isShowDetails());
    }


    @Override
    public void firstCombatPushed(int index, int viewId, boolean abovePlanet, boolean enemyNPA) {
        Fleet fleet = alienPlayer.getFleets().get(index);
        List<FleetBuildOption> options = new ArrayList<>();
        if(abovePlanet) options.add(COMBAT_IS_ABOVE_PLANET);
        if(enemyNPA) options.add(COMBAT_WITH_NPAS);
        FleetBuildResult result = alienPlayer.firstCombat(fleet, options.toArray(new FleetBuildOption[options.size()]));
        ((FleetView)findViewById(viewId)).update();
        onFleetRevealed(fleet);
        showFleetBuildResult(result);
    }
}
