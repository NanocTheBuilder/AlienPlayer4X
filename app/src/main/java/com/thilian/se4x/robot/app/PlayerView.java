package com.thilian.se4x.robot.app;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
public class PlayerView extends ConstraintLayout {

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
        li.inflate(R.layout.player_layout, this, true);
    }

    public PlayerView(Context context, Game game, final AlienPlayer alienPlayer) {
        this(context, null);
        boolean showDetails = ((SE4XActivity) getContext()).isShowDetails();
        initTextVisibilities(game, showDetails);
        setBackgroundColor(alienPlayer);
        update(game, alienPlayer, showDetails);
    }

    public void initEliminateButton(final AlienPlayer alienPlayer, final PlayerEliminationListener listener) {
        Button eliminateButton = findViewById(R.id.eliminate_button);
        eliminateButton.setVisibility(VISIBLE);
        if(alienPlayer.isEliminated()){
            eliminateButton.setText(R.string.eliminated);
            eliminateButton.setEnabled(false);
        }
        else {
            eliminateButton.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onPlayerEliminated(alienPlayer);
                    }
                }
            );
        }
    }

    public interface PlayerEliminationListener{
        void onPlayerEliminated(AlienPlayer alienPlayer);
    }

    public void initTextVisibilities(Game game, boolean showDetails) {
        if(!showDetails){
            findViewById(R.id.fleet_cp_text).setVisibility(GONE);
            findViewById(R.id.tech_cp_text).setVisibility(GONE);
            findViewById(R.id.def_cp_text).setVisibility(GONE);
            findViewById(R.id.bank_text).setVisibility(GONE);
        }

        if(!(game.scenario instanceof Scenario4)) {
            findViewById(R.id.ground_combat_text).setVisibility(GONE);
            findViewById(R.id.boarding_text).setVisibility(GONE);
            findViewById(R.id.security_forces_text).setVisibility(GONE);
            findViewById(R.id.military_academy_text).setVisibility(GONE);
        }

        if(!(game.scenario instanceof VpSoloScenario)) {
            findViewById(R.id.bank_text).setVisibility(GONE);
            findViewById(R.id.colonies_text).setVisibility(GONE);
        }
    }

    public void setBackgroundColor(AlienPlayer alienPlayer){
        setBackgroundColor(Color.parseColor(alienPlayer.getColor().toString()));
    }

    public void update(Game game, AlienPlayer alienPlayer, boolean showDetails){
        if(showDetails) {
            ((TextView) findViewById(R.id.fleet_cp_text)).setText(getResources().getString(R.string.fleetCp, alienPlayer.getEconomicSheet().getFleetCP()));
            ((TextView) findViewById(R.id.tech_cp_text)).setText(getResources().getString(R.string.techCp, alienPlayer.getEconomicSheet().getTechCP()));
            ((TextView) findViewById(R.id.def_cp_text)).setText(getResources().getString(R.string.defCp, alienPlayer.getEconomicSheet().getDefCP()));
            if (alienPlayer instanceof VpAlienPlayer)
                ((TextView) findViewById(R.id.bank_text)).setText(getResources().getString(R.string.bank,
                        ((VpEconomicSheet) alienPlayer.getEconomicSheet()).getBank()));
        }

        for(Technology technology : game.scenario.techPrices.getAvailableTechs()){
            updateTechnologyText(game, alienPlayer, technology);
        }

        if(alienPlayer instanceof VpAlienPlayer){
            ((TextView) findViewById(R.id.colonies_text)).setText(getResources().getString(R.string.colonies, ((VpAlienPlayer)alienPlayer).getColonies()));

        }

        TextView fleets = findViewById(R.id.fleets_text);
        fleets.setText(String.format("%d fleets.", alienPlayer.getFleets().size()));
    }

    public void updateTechnologyText(Game game, AlienPlayer alienPlayer, Technology technology) {
        String name = String.format("%s_text", technology.toString().toLowerCase());
        int id = getResources().getIdentifier(name, "id", getContext().getPackageName());
        if(id != 0){
            TextView textView = findViewById(id);
            int sid = getResources().getIdentifier(technology.toString(), "string", getContext().getPackageName());
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
}
