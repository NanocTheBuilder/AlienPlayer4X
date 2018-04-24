package com.thilian.se4x.robot.app;

import android.content.Context;
import android.graphics.Color;
import android.preference.EditTextPreference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.Technology;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpAlienPlayer;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpEconomicSheet;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpSoloScenario;

/**
 * TODO: document your custom view class.
 */
public class PlayerView extends LinearLayout {

    private Game game;
    private AlienPlayer alienPlayer;

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
        li.inflate(R.layout.player_layout, this, true);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setAlienPlayer(AlienPlayer alienPlayer) {
        this.alienPlayer = alienPlayer;
    }

    public PlayerView(Context context, Game game, final AlienPlayer alienPlayer) {
        this(context, null);
        this.game = game;
        this.alienPlayer = alienPlayer;
        initTexts();

        ((Button)findViewById(R.id.eliminate_button)).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alienPlayer.setEliminated(true);
                        view.setEnabled(false);
                    }
                }
        );

        setBackgroundColor();
        update();
    }

    public void initTexts() {
        if(!(game.scenario instanceof Scenario4)){
            findViewById(R.id.ground_combat_text).setVisibility(GONE);
            findViewById(R.id.boarding_text).setVisibility(GONE);
            findViewById(R.id.security_forces_text).setVisibility(GONE);
            findViewById(R.id.military_academy_text).setVisibility(GONE);
        }
        if(!(game.scenario instanceof VpSoloScenario)){
            findViewById(R.id.colonies_text).setVisibility(GONE);
            findViewById(R.id.colonies_edit).setVisibility(GONE);
        }
        else{
            EditText coloniesEdit = (EditText) findViewById(R.id.colonies_edit);
            if(getContext() instanceof FleetsActivity) {
                coloniesEdit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String str = editable.toString();
                        int colonies = "".equals(str) ? 0 : Integer.parseInt(str);
                        ((VpAlienPlayer) alienPlayer).setColonies(colonies);
                    }
                });
            }
            else{
                coloniesEdit.setEnabled(false);
            }
        }
        findViewById(R.id.eliminate_button).setEnabled(true);
    }

    public void setBackgroundColor(){
        setBackgroundColor(Color.parseColor(alienPlayer.getColor().toString()));
    }

    public void update(){
        ((TextView)findViewById(R.id.fleet_cp_text)).setText(getResources().getString(R.string.fleetCp, alienPlayer.getEconomicSheet().getFleetCP()));
        ((TextView)findViewById(R.id.tech_cp_text)).setText(getResources().getString(R.string.techCp, alienPlayer.getEconomicSheet().getTechCP()));
        ((TextView)findViewById(R.id.def_cp_text)).setText(getResources().getString(R.string.defCp, alienPlayer.getEconomicSheet().getDefCP()));
        if(alienPlayer instanceof VpAlienPlayer){
            ((TextView)findViewById(R.id.bank_text)).setText(getResources().getString(R.string.bank,
                    ((VpEconomicSheet)alienPlayer.getEconomicSheet()).getBank()));
        }
        else{
            ((TextView)findViewById(R.id.bank_text)).setVisibility(GONE);
        }

        TextView textView;
        int id,sid;
        for(Technology technology : game.scenario.techPrices.getAvailableTechs()){
            String name = technology.toString().toLowerCase() + "_text";
            id = getResources().getIdentifier(name, "id", getContext().getPackageName());
            if(id != 0){
                textView = findViewById(id);
                sid = getResources().getIdentifier(technology.toString(), "string", getContext().getPackageName());
                int level = alienPlayer.getLevel(technology);
                textView.setText(getResources().getString(sid, level));
                if(level != game.scenario.techPrices.getStartingLevel(technology)){
                    textView.setTypeface(textView.getTypeface(), 1);
                }
            }
            else{
                System.out.println(String.format("Not found <%s>", name));
            }
        }

        EditText coloniesEdit = (EditText) findViewById(R.id.colonies_edit);
        if(coloniesEdit.getVisibility() == VISIBLE){
            coloniesEdit.setText(String.valueOf(((VpAlienPlayer)alienPlayer).getColonies()));
        }

        TextView fleets = findViewById(R.id.fleets_text);
        fleets.setText(String.format("%d fleets.", alienPlayer.getFleets().size()));
    }
}
