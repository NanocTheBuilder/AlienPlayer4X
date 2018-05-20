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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.thilian.se4x.robot.app.GameSaver;
import com.thilian.se4x.robot.app.R;
import com.thilian.se4x.robot.app.SE4XActivity;
import com.thilian.se4x.robot.app.activities.MainActivity;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.PlayerColor;
import com.thilian.se4x.robot.game.enums.Scenarios;

import java.util.ArrayList;
import java.util.List;

public class NewGameActivity extends SE4XActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_game_activity);

        loadBackgroundImage();

        final Spinner difficultySpinner = findViewById(R.id.difficulty_spinner);
        final ArrayAdapter<Difficulty> difficultyAdapter = new ToStringArrayAdapter<>(this,R.layout.white_spinner_item);
        difficultyAdapter.setNotifyOnChange(true);
        difficultySpinner.setAdapter(difficultyAdapter);
        difficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initPlayerColors();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner scenarioSpinner = findViewById(R.id.scenario_spinner);
        ArrayAdapter<Scenarios> scenarioAdapter = new ToStringArrayAdapter<>(this, R.layout.white_spinner_item, Scenarios.values());
        scenarioSpinner.setAdapter(scenarioAdapter);
        scenarioSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                difficultyAdapter.clear();
                try {
                    difficultyAdapter.addAll(((Scenarios)parent.getItemAtPosition(position)).getClazz().newInstance().getDifficulties());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                difficultySpinner.setSelection(0);
                initPlayerColors();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        for(PlayerColor color : PlayerColor.values()){
            ToggleButton colorButton = findColorButton(color);
            colorButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int selectedPlayers = 0;
                    for(PlayerColor playerColor : PlayerColor.values()){
                        if(findColorButton(playerColor).isChecked())
                            selectedPlayers++;
                    }
                    Button  startButton = findViewById(R.id.start_button);
                    Difficulty difficulty = (Difficulty)(((Spinner) findViewById(R.id.difficulty_spinner)).getSelectedItem());
                    startButton.setEnabled(selectedPlayers == difficulty.getNumberOfAlienPlayers());
                }
            });
        }

        Button  startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createGame();
                    startMainActivity();
                } catch (IllegalAccessException e) {
                    //TODO error dialog
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private ToggleButton findColorButton(PlayerColor playerColor) {
        int id = getResources().getIdentifier(String.format("color_button_%s", playerColor), "id", getPackageName());
        return findViewById(id);
    }

    private void initPlayerColors(){
        Difficulty difficulty = (Difficulty)(((Spinner) findViewById(R.id.difficulty_spinner)).getSelectedItem());
        int players = difficulty.getNumberOfAlienPlayers();
        PlayerColor[] colors = PlayerColor.values();
        ToggleButton button;
        for(int i = 0; i < 4; i ++){
            int id = getResources().getIdentifier(String.format("color_button_%s", colors[i]), "id", getPackageName());
            button = findViewById(id);
            button.setChecked(i < players);
        }
    }
    public void createGame() throws IllegalAccessException, InstantiationException {
        Difficulty difficulty = (Difficulty)(((Spinner) findViewById(R.id.difficulty_spinner)).getSelectedItem());
        Scenarios scenario = (Scenarios)(((Spinner)findViewById(R.id.scenario_spinner)).getSelectedItem());
        List<PlayerColor> selectedColors = new ArrayList<>();
        for(PlayerColor playerColor : PlayerColor.values()){
            if(findColorButton(playerColor).isChecked())
                selectedColors.add(playerColor);
        }
        Game game = Game.newGame(scenario.getClazz().newInstance(), difficulty, selectedColors.toArray(new PlayerColor[selectedColors.size()]));
        new GameSaver().saveGame(game, this);
    }

    private void startMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
    }

    private class ToStringArrayAdapter<T> extends ArrayAdapter<T>{

        public ToStringArrayAdapter(@NonNull Context context, int resource, @NonNull T[] objects) {
            super(context, resource, objects);
        }

        public ToStringArrayAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getView(position, convertView, parent, R.layout.white_spinner_item);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return getView(position, convertView, parent, android.R.layout.simple_spinner_dropdown_item);
        }

        @NonNull
        public View getView(int position, View convertView, ViewGroup parent, int resource) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(resource, parent, false);
            }

            T item = getItem(position);
            String string = getString(item);
            ((TextView) convertView.findViewById(android.R.id.text1))
                    .setText(string);
            return convertView;
        }

        @NonNull
        public String getString(Object item) {
            int sid = getResources().getIdentifier(item.toString(), "string", getPackageName());
            return getResources().getString(sid);
        }
    }
}
