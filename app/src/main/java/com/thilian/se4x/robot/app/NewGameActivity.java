package com.thilian.se4x.robot.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.Scenarios;

public class NewGameActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_settings:
            {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner difficultySpinner = (Spinner) findViewById(R.id.difficultySpinner);
        final ArrayAdapter<Difficulty> difficultyAdapter = new ArrayAdapter<Difficulty>(this, android.R.layout.simple_spinner_dropdown_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficultyAdapter);

        Spinner scenarioSpinner = (Spinner) findViewById(R.id.scenarioSpinner);
        ArrayAdapter<Scenarios> scenarioAdapter = new ArrayAdapter<Scenarios>(this, android.R.layout.simple_spinner_dropdown_item, Scenarios.values());
        scenarioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                difficultyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
        app.getGame().createGame(scenario.getClazz().newInstance(), difficulty);
    }

    private void startMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
    }
}
