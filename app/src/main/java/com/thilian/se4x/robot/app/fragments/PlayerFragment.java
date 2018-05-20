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

package com.thilian.se4x.robot.app.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thilian.se4x.robot.app.R;
import com.thilian.se4x.robot.app.SE4XGameActivity;
import com.thilian.se4x.robot.app.dialogs.FirstCombatDialog;
import com.thilian.se4x.robot.app.dialogs.PickerDialog;
import com.thilian.se4x.robot.app.views.FleetView;
import com.thilian.se4x.robot.app.views.PlayerView;
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

public class PlayerFragment extends Fragment implements FirstCombatDialog.FirstCombatListener, PlayerView.PlayerEliminationListener, FleetView.FleetRemoveListener {

    public static final String ALIEN_INDEX = "alien_index";

    public SE4XGameActivity gameActivity;
    private int alienIndex;

    private AlienPlayer getAlienPlayer() {
        return gameActivity.getGame().aliens.get(alienIndex);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        gameActivity = (SE4XGameActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.player_fragment, container, false);

        Button buildHomeDefenseButton = view.findViewById(R.id.build_home_defense_button);
        buildHomeDefenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildHomeDefenseButtonClicked();
            }
        });


        Button buildColonyDefenseButton = view.findViewById(R.id.build_colony_defense_button);
        buildColonyDefenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildColonyDefenseButtonClicked();
            }
        });

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        PlayerView playerView = getView().findViewById(R.id.player_view);
        playerView.setBackgroundColor(gameActivity.getColor(getAlienPlayer()));
        playerView.initTextVisibilities(gameActivity.getGame(), gameActivity.isShowDetails());
        initTextClicks();
        playerView.initEliminateButton(getAlienPlayer(), this);
    }

    @Override
    public void onResume() {
        super.onResume();

        updatePlayerView();
        updateFleetViews();
    }


    public void initTextClicks() {
        PlayerView playerView = getView().findViewById(R.id.player_view);
        if(gameActivity.getGame().scenario instanceof VpSoloScenario) {
            for(Technology technology : gameActivity.getGame().scenario.getAvailableTechs()) {
                initTechnologyClick(playerView, technology);
            }
            initColoniesClick();
        }
    }

    public void initTechnologyClick(final PlayerView playerView, final Technology technology) {
        String sid = String.format("%s_text", technology.toString().toLowerCase());
        int id = getResources().getIdentifier(sid, "id", gameActivity.getPackageName());
        TextView view = playerView.findViewById(id);
        view.setBackgroundResource(R.drawable.editable_text_background);
        view.setClickable(true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlienPlayer alienPlayer = getAlienPlayer();
                gameActivity.showLevelPickerDialog(alienPlayer, technology,
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

        View view = getView().findViewById(R.id.colonies_text);
        view.setClickable(true);
        view.setBackgroundResource(R.drawable.editable_text_background);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameActivity.showPickerDialog(
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
    private void buildHomeDefenseButtonClicked() {
        FleetBuildResult result = getAlienPlayer().buildHomeDefense();
        LinearLayout fleets = getView().findViewById(R.id.fleets);
        for (Fleet fleet : result.getNewFleets()) {
            fleets.addView(new FleetView(this, fleet));
        }
        updatePlayerView();
        gameActivity.showFleetBuildResult(result);
    }

    private void buildColonyDefenseButtonClicked() {
        FleetBuildResult result = ((Scenario4Player) getAlienPlayer()).buildColonyDefense();
        LinearLayout fleets = getView().findViewById(R.id.fleets);
        for (Fleet fleet : result.getNewFleets()) {
            fleets.addView(new FleetView(this, fleet));
        }
        updatePlayerView();
        gameActivity.showFleetBuildResult(result);
    }

    public void updateViews(){
        updatePlayerView();
        updateFleetViews();
    }

    private void updatePlayerView() {
        AlienPlayer alienPlayer = getAlienPlayer();
        PlayerView playerView = getView().findViewById(R.id.player_view);
        playerView.update(gameActivity.getGame(), alienPlayer, gameActivity.isShowDetails());
        playerView.initEliminateButton(alienPlayer, this);
    }

    private void updateFleetViews() {
        AlienPlayer alienPlayer = getAlienPlayer();
        getView().findViewById(R.id.build_colony_defense_button).setVisibility(alienPlayer instanceof Scenario4Player ? View.VISIBLE : View.GONE);
        LinearLayout fleets = getView().findViewById(R.id.fleets);
        fleets.setBackgroundColor(gameActivity.getColor(alienPlayer));
        fleets.removeAllViews();
        for(Fleet fleet : alienPlayer.getFleets()){
            fleets.addView(new FleetView(this, fleet));
        }
    }

    @Override
    public void firstCombatPushed(Fleet fleet, FleetView fleetView, boolean abovePlanet, boolean enemyNPA) {
        List<FleetBuildOption> options = new ArrayList<>();
        if (abovePlanet) options.add(COMBAT_IS_ABOVE_PLANET);
        if (enemyNPA) options.add(COMBAT_WITH_NPAS);
        FleetBuildResult result = getAlienPlayer().firstCombat(fleet, options.toArray(new FleetBuildOption[options.size()]));
        fleetView.update();
        updatePlayerView();
        gameActivity.showFleetBuildResult(result);
    }

    @Override
    public void onFleetRemoved(int index, FleetView view) {
        Fleet fleet = getAlienPlayer().getFleets().get(index);
        getAlienPlayer().removeFleet(fleet);
        fleet.getAp().removeFleet(fleet);
        LinearLayout layout = getView().findViewById(R.id.fleets);
        layout.removeView(view);
        updatePlayerView();
    }

    @Override
    public void onPlayerEliminationClicked(final AlienPlayer alienPlayer){
        AlertDialog.Builder builder = new AlertDialog.Builder(gameActivity);
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
        PlayerView playerView = getView().findViewById(R.id.player_view);
        playerView.initEliminateButton(alienPlayer, null);
    }

    public void setAlienIndex(int alienIndex) {
        this.alienIndex = alienIndex;
    }
}
