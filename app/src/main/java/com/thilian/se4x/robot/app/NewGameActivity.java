package com.thilian.se4x.robot.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.Difficulty;

public class NewGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        Spinner spinner = (Spinner) findViewById(R.id.difficultySpinner);
        spinner.setAdapter(new ArrayAdapter<Difficulty>(this, android.R.layout.simple_spinner_item, Difficulty.values()));

        Button  startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createGame();
                startMainActivity();
            }
        });
    }

    public void createGame(){
        final SE4XApplication app = (SE4XApplication) this.getApplication();
        Spinner spinner = (Spinner) findViewById(R.id.difficultySpinner);
        app.getGame().createGame((Difficulty) spinner.getSelectedItem());
    }

    private void startMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
    }
}
