package com.thilian.se4x.robot.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.thilian.se4x.robot.game.TechnologyPrices;
import com.thilian.se4x.robot.game.basegame.BaseGameTechnologyPrices;
import com.thilian.se4x.robot.game.enums.Technology;

import java.util.ArrayList;
import java.util.List;

public class SeenTechnologyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seen_technology);

        createAdapter(R.id.cloaking_spinner, Technology.CLOAKING);
        createAdapter(R.id.scanner_spinner, Technology.SCANNER);
        createAdapter(R.id.fighters_spinner, Technology.FIGHTERS);
        createAdapter(R.id.point_defense_spinner, Technology.POINT_DEFENSE);
        createAdapter(R.id.mines_spinner, Technology.MINES);

        Button okButton = findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okButtonClicked();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        initSpinner(R.id.cloaking_spinner, Technology.CLOAKING);
        initSpinner(R.id.scanner_spinner, Technology.SCANNER);
        initSpinner(R.id.fighters_spinner, Technology.FIGHTERS);
        initSpinner(R.id.point_defense_spinner, Technology.POINT_DEFENSE);
        initSpinner(R.id.mines_spinner, Technology.MINES);
    }

    private void okButtonClicked() {
        readSpinner(R.id.cloaking_spinner, Technology.CLOAKING);
        readSpinner(R.id.scanner_spinner, Technology.SCANNER);
        readSpinner(R.id.fighters_spinner, Technology.FIGHTERS);
        readSpinner(R.id.point_defense_spinner, Technology.POINT_DEFENSE);
        readSpinner(R.id.mines_spinner, Technology.MINES);

        final SE4XApplication app = (SE4XApplication) getApplication();

        onBackPressed();
    }

    private void readSpinner(int id, Technology technology) {
        final SE4XApplication app = (SE4XApplication) getApplication();
        Spinner spinner = (Spinner) findViewById(id);
        app.getGame().setSeenLevel(technology, (Integer) spinner.getSelectedItem());
    }

    private void initSpinner(int id, Technology technology) {
        final SE4XApplication app = (SE4XApplication) getApplication();
        Spinner spinner = (Spinner) findViewById(id);
        spinner.setSelection(app.getGame().getSeenLevel(technology));
    }

    private void createAdapter(int id, Technology technology) {
        final SE4XApplication app = (SE4XApplication) this.getApplication();
        TechnologyPrices technologyPrices = app.getGame().scenario.techPrices;
        Spinner spinner = (Spinner) findViewById(id);
        List<Integer> values = new ArrayList<>();
        for(int i = 0; i <= technologyPrices.getMaxLevel(technology); i++)
            values.add(i);
        spinner.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, values.toArray(new Integer[values.size()])));
    }
}
