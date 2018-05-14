package com.thilian.se4x.robot.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thilian.se4x.robot.app.dialogs.FirstCombatDialog;
import com.thilian.se4x.robot.app.dialogs.PickerDialog;
import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.FleetBuildResult;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.enums.Technology;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4Player;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpAlienPlayer;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpSoloScenario;

import java.util.ArrayList;
import java.util.List;

import static com.thilian.se4x.robot.game.enums.FleetBuildOption.COMBAT_IS_ABOVE_PLANET;
import static com.thilian.se4x.robot.game.enums.FleetBuildOption.COMBAT_WITH_NPAS;

public class PlayerActivity extends SE4XActivity implements FirstCombatDialog.FirstCombatListener, PlayerView.PlayerEliminationListener, FleetView.FleetRemoveListener{

    private int alienIndex;

    private AlienPlayer getAlienPlayer(){
        return getGame().aliens.get(alienIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity);

        loadBackgroundImage();

        Button okButton = findViewById(R.id.close_fleets_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerActivity.this.onBackPressed();
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
        alienIndex = getIntent().getIntExtra(Intent.EXTRA_INDEX, -1);
        PlayerView playerView = findViewById(R.id.player_view);
        playerView.setBackgroundColor(getColor(getAlienPlayer()));
        playerView.initTextVisibilities(getGame(), isShowDetails());
        initTextClicks();
        playerView.initEliminateButton(getAlienPlayer(), this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AlienPlayer alienPlayer = getGame().aliens.get(alienIndex);

        PlayerView playerView = findViewById(R.id.player_view);
        playerView.update(getGame(), alienPlayer, isShowDetails());
        playerView.initEliminateButton(alienPlayer, this);

        findViewById(R.id.build_colony_defense_button).setVisibility(alienPlayer instanceof Scenario4Player ? View.VISIBLE : View.GONE);

        LinearLayout fleets = (LinearLayout) findViewById(R.id.fleets);
        fleets.setBackgroundColor(getColor(alienPlayer));
        fleets.removeAllViews();
        for(Fleet fleet : alienPlayer.getFleets()){
            fleets.addView(new FleetView(this, fleet));
        }
    }

    @Override
    public void onPlayerEliminationClicked(final AlienPlayer alienPlayer){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String message = getString(R.string.eliminate_player, alienPlayer.getColor());
        builder.setTitle(R.string.areYouSure)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eliminatePlayer(alienPlayer);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    private void eliminatePlayer(AlienPlayer alienPlayer){
        alienPlayer.setEliminated(true);
        PlayerView playerView = findViewById(R.id.player_view);
        playerView.initEliminateButton(alienPlayer, null);
    }

    private void buildHomeDefenseButtonClicked(){
        FleetBuildResult result = getAlienPlayer().buildHomeDefense();
        LinearLayout fleets = findViewById(R.id.fleets);
        for (Fleet fleet : result.getNewFleets()) {
            fleets.addView(new FleetView(this, fleet));
        }
        updatePlayerView();
        showFleetBuildResult(result);
    }

    private void buildColonyDefenseButtonClicked(){
        FleetBuildResult result = ((Scenario4Player)getAlienPlayer()).buildColonyDefense();
        LinearLayout fleets = findViewById(R.id.fleets);
        for (Fleet fleet : result.getNewFleets()) {
            fleets.addView(new FleetView(this, fleet));
        }
        updatePlayerView();
        showFleetBuildResult(result);
    }

    public void initTextClicks() {
        PlayerView playerView = findViewById(R.id.player_view);
        if(getGame().scenario instanceof VpSoloScenario) {
            for(Technology technology : getGame().scenario.getAvailableTechs()) {
                initTechnologyClick(playerView, technology);
            }
            initColoniesClick();
        }
    }

    public void initTechnologyClick(final PlayerView playerView, final Technology technology) {
            String sid = String.format("%s_text", technology.toString().toLowerCase());
            int id = getResources().getIdentifier(sid, "id", getPackageName());
            TextView view = playerView.findViewById(id);
            view.setBackgroundResource(R.drawable.editable_text_background);
            view.setClickable(true);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlienPlayer alienPlayer = getAlienPlayer();
                    showLevelPickerDialog(alienPlayer, technology,
                            new PickerDialog.PickerClickAction() {
                                @Override
                                public void action(int value) {
                                    alienPlayer.setLevel(technology, value);
                                    updatePlayerView();
                                }
                            }
                        );
                }
            });
    }

    public void initColoniesClick() {
        final VpAlienPlayer vpAlienPlayer = (VpAlienPlayer) getAlienPlayer();

        View view = findViewById(R.id.colonies_text);
        view.setClickable(true);
        view.setBackgroundResource(R.drawable.editable_text_background);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickerDialog(
                        R.string.colonies_label,
                        0, 9, vpAlienPlayer.getColonies(),
                        new PickerDialog.PickerClickAction() {
                            @Override
                            public void action(int value) {
                                vpAlienPlayer.setColonies(value);
                                updatePlayerView();
                            }
                        });
            };
        });
    }

    public void updatePlayerView() {
        PlayerView playerView = findViewById(R.id.player_view);
        playerView.update(getGame(), getAlienPlayer(), isShowDetails());
    }

    @Override
    public void firstCombatPushed(Fleet fleet, FleetView fleetView, boolean abovePlanet, boolean enemyNPA) {
        List<FleetBuildOption> options = new ArrayList<>();
        if(abovePlanet) options.add(COMBAT_IS_ABOVE_PLANET);
        if(enemyNPA) options.add(COMBAT_WITH_NPAS);
        FleetBuildResult result = getAlienPlayer().firstCombat(fleet, options.toArray(new FleetBuildOption[options.size()]));
        fleetView.update();
        updatePlayerView();
        showFleetBuildResult(result);
    }

    @Override
    public void onFleetRemoved(int index, FleetView view) {
        Fleet fleet = getAlienPlayer().getFleets().get(index);
        getAlienPlayer().removeFleet(fleet);
        fleet.getAp().removeFleet(fleet);
        LinearLayout layout = findViewById(R.id.fleets);
        layout.removeView(view);
        updatePlayerView();
    }
}
