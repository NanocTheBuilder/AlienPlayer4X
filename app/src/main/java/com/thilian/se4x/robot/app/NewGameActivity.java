package com.thilian.se4x.robot.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.enums.Scenarios;

public class NewGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        createEnumSpinner(R.id.difficultySpinner, Difficulty.EASY);

        createEnumSpinner(R.id.scenarioSpinner, Scenarios.BASE_GAME);

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

    private <T extends Enum> void createEnumSpinner(int id, T enumValue){
        Spinner spinner = (Spinner) findViewById(id);
        ArrayAdapter<T> adapter = new ArrayAdapter<T>(this, android.R.layout.simple_spinner_dropdown_item, (T[])enumValue.getDeclaringClass().getEnumConstants());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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
