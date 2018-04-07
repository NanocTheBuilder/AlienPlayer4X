package com.thilian.se4x.robot.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.thilian.se4x.robot.game.Scenario;
import com.thilian.se4x.robot.game.basegame.BaseGameScenario;
import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.Scenarios;

public class NewGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        Spinner difficultySpinner = (Spinner) findViewById(R.id.difficultySpinner);
        difficultySpinner.setAdapter(new ArrayAdapter<Difficulty>(this, android.R.layout.simple_spinner_item, Difficulty.values()));

        Spinner scenarioSpinner = (Spinner) findViewById(R.id.scenarioSpinner);
        scenarioSpinner.setAdapter(new ArrayAdapter<Scenarios>(this, android.R.layout.simple_spinner_item, Scenarios.values()));

        Button  startButton = (Button) findViewById(R.id.startButton);
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

    public void createGame() throws IllegalAccessException, InstantiationException {
        final SE4XApplication app = (SE4XApplication) this.getApplication();
        Difficulty difficulty = (Difficulty)(((Spinner) findViewById(R.id.difficultySpinner)).getSelectedItem());
        Scenarios scenario = (Scenarios)(((Spinner)findViewById(R.id.scenarioSpinner)).getSelectedItem());
        app.getGame().createGame(difficulty, scenario.getClazz().newInstance());
    }

    private void startMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
    }
}
