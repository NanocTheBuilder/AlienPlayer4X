package com.thilian.se4x.robot.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.TechnologyPrices;
import com.thilian.se4x.robot.game.enums.Seeable;
import com.thilian.se4x.robot.game.enums.Technology;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4;

import java.util.ArrayList;
import java.util.List;

public class SeenTechnologyActivity extends SE4XActivity{
/*
    //POINT_DEFENSE, SCANNERS, CLOAKING must remain seenLevel
    FIGHTERS,
    MINES,
    BOARDING_SHIPS,
    VETERANS,
    SIZE_3_SHIPS,
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seen_technology);

        createSpinner(R.id.cloaking_spinner, Technology.CLOAKING);
        createSpinner(R.id.scanner_spinner, Technology.SCANNER);
        createSpinner(R.id.point_defense_spinner, Technology.POINT_DEFENSE);

        createCheckbox(R.id.fighters_checkbox, Seeable.FIGHTERS);
        createCheckbox(R.id.mines_checkbox, Seeable.MINES);

        createCheckbox(R.id.boarding_checkbox, Seeable.BOARDING_SHIPS);
        createCheckbox(R.id.veterans_checkbox, Seeable.VETERANS);
        createCheckbox(R.id.size3_checkbox, Seeable.SIZE_3_SHIPS);

        Button okButton = findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        initSpinner(R.id.cloaking_spinner, Technology.CLOAKING);
        initSpinner(R.id.scanner_spinner, Technology.SCANNER);
        initSpinner(R.id.point_defense_spinner, Technology.POINT_DEFENSE);

        initCheckbox(R.id.fighters_checkbox, Seeable.FIGHTERS);
        initCheckbox(R.id.mines_checkbox, Seeable.MINES);
        if(getGame().scenario instanceof Scenario4) {
            initCheckbox(R.id.boarding_checkbox, Seeable.BOARDING_SHIPS);
            initCheckbox(R.id.veterans_checkbox, Seeable.VETERANS);
            initCheckbox(R.id.size3_checkbox, Seeable.SIZE_3_SHIPS);
        }
        else{
            findViewById(R.id.boarding_checkbox).setVisibility(View.GONE);
            findViewById(R.id.veterans_checkbox).setVisibility(View.GONE);
            findViewById(R.id.size3_checkbox).setVisibility(View.GONE);
        }
    }

    private void createSpinner(int id, Technology technology) {
        TechnologyPrices technologyPrices = getGame().scenario.techPrices;
        Spinner spinner = (Spinner) findViewById(id);
        List<Integer> values = new ArrayList<>();
        for(int i = 0; i <= technologyPrices.getMaxLevel(technology); i++)
            values.add(i);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values.toArray(new Integer[values.size()]));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerListener(technology));
    }

    private void createCheckbox(int id, Seeable seeable) {
        TechnologyPrices technologyPrices = getGame().scenario.techPrices;
        CheckBox checkBox = (CheckBox) findViewById(id);
        checkBox.setOnClickListener(new CheckBoxListener(seeable));
    }

    private void initSpinner(int id, Technology technology) {
        Spinner spinner = (Spinner) findViewById(id);
        spinner.setSelection(getGame().getSeenLevel(technology));
    }

    private void initCheckbox(int id, Seeable seeable) {
        CheckBox checkBox = (CheckBox) findViewById(id);
        checkBox.setVisibility(View.VISIBLE);
        checkBox.setChecked(getGame().isSeenThing(seeable));
    }

    private class SpinnerListener implements AdapterView.OnItemSelectedListener {

        private Technology technology;

        SpinnerListener(Technology technology){
            this.technology = technology;
        };

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Integer level = (Integer) parent.getItemAtPosition(position);
            if(!getGame().getSeenLevel(technology).equals(level)) {
                getGame().setSeenLevel(technology, level);
                saveGame();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            //nothing
        }
    }

    private class CheckBoxListener implements View.OnClickListener{

        private Seeable seeable;

        CheckBoxListener(Seeable seeable){
            this.seeable = seeable;
        }

        @Override
        public void onClick(View view) {
            boolean checked = ((CheckBox) view).isChecked();
            if(checked){
                getGame().addSeenThing(seeable);
            }
            else{
                getGame().removeSeenThing(seeable);
            }
            saveGame();
        }
    }
}
