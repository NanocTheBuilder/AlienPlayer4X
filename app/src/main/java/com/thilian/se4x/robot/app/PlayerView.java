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
        boolean showDetails = ((SE4XActivity) getContext()).isShowDetails();

        initTexts(showDetails);
        setBackgroundColor();
        update(showDetails);
    }

    public void initEliminateButton(final AlienPlayer alienPlayer) {
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
                            alienPlayer.setEliminated(true);
                            ((Button)view).setText(R.string.eliminated);
                            view.setEnabled(false);
                        }
                    }
            );
        }
    }

    public void initTexts(boolean showDetails) {
        initLongClick(Technology.MOVE);
        initLongClick(Technology.SHIP_SIZE);
        initLongClick(Technology.ATTACK);
        initLongClick(Technology.DEFENSE);
        initLongClick(Technology.TACTICS);
        initLongClick(Technology.FIGHTERS);
        initLongClick(Technology.POINT_DEFENSE);
        initLongClick(Technology.CLOAKING);
        initLongClick(Technology.SCANNER);
        initLongClick(Technology.MINE_SWEEPER);

        if(!showDetails){
            findViewById(R.id.fleet_cp_text).setVisibility(GONE);
            findViewById(R.id.tech_cp_text).setVisibility(GONE);
            findViewById(R.id.def_cp_text).setVisibility(GONE);
            findViewById(R.id.bank_text).setVisibility(GONE);
        }

        if(game.scenario instanceof Scenario4) {
            initLongClick(Technology.GROUND_COMBAT);
            initLongClick(Technology.BOARDING);
            initLongClick(Technology.SECURITY_FORCES);
            initLongClick(Technology.MILITARY_ACADEMY);
        }
        else{
            findViewById(R.id.ground_combat_text).setVisibility(GONE);
            findViewById(R.id.boarding_text).setVisibility(GONE);
            findViewById(R.id.security_forces_text).setVisibility(GONE);
            findViewById(R.id.military_academy_text).setVisibility(GONE);
        }

        if(game.scenario instanceof VpSoloScenario) {
            initColoniesLongClick();
        }
        else{
            findViewById(R.id.bank_text).setVisibility(GONE);
            findViewById(R.id.colonies_text).setVisibility(GONE);
        }
    }

    public void initLongClick(final Technology technology) {
        if(getContext() instanceof FleetsActivity && alienPlayer instanceof VpAlienPlayer) {
            String sid = String.format("%s_text", technology.toString().toLowerCase());
            int id = getResources().getIdentifier(sid, "id", getContext().getPackageName());
            View view = findViewById(id);
            view.setLongClickable(true);
            view.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ((SE4XActivity) getContext()).showLevelPickerDialog(alienPlayer, technology, PlayerView.this);
                    return true;
                }
            });
        }
    }

    public void initColoniesLongClick() {
        if(getContext() instanceof FleetsActivity) {
            final SE4XActivity activity = (SE4XActivity) getContext();
            final VpAlienPlayer vpAlienPlayer = (VpAlienPlayer) this.alienPlayer;

            View view = findViewById(R.id.colonies_text);
            view.setLongClickable(true);
            view.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    activity.showPickerDialog(
                            R.string.colonies_label,
                            0, 9, vpAlienPlayer.getColonies(),
                            new SE4XActivity.PickerClickAction() {
                                @Override
                                public void action(int value) {
                                    vpAlienPlayer.setColonies(value);
                                    update(activity.isShowDetails());
                                }
                            });
                    return true;
                };
            });
        }
    }

    public void setBackgroundColor(){
        setBackgroundColor(Color.parseColor(alienPlayer.getColor().toString()));
    }

    public void update(boolean showDetails){
        if(showDetails) {
            ((TextView) findViewById(R.id.fleet_cp_text)).setText(getResources().getString(R.string.fleetCp, alienPlayer.getEconomicSheet().getFleetCP()));
            ((TextView) findViewById(R.id.tech_cp_text)).setText(getResources().getString(R.string.techCp, alienPlayer.getEconomicSheet().getTechCP()));
            ((TextView) findViewById(R.id.def_cp_text)).setText(getResources().getString(R.string.defCp, alienPlayer.getEconomicSheet().getDefCP()));
            if (alienPlayer instanceof VpAlienPlayer)
                ((TextView) findViewById(R.id.bank_text)).setText(getResources().getString(R.string.bank,
                        ((VpEconomicSheet) alienPlayer.getEconomicSheet()).getBank()));
        }

        for(Technology technology : game.scenario.techPrices.getAvailableTechs()){
            updateTechnologyText(technology);
        }

        if(alienPlayer instanceof VpAlienPlayer){
            ((TextView) findViewById(R.id.colonies_text)).setText(getResources().getString(R.string.colonies, ((VpAlienPlayer)alienPlayer).getColonies()));

        }

        TextView fleets = findViewById(R.id.fleets_text);
        fleets.setText(String.format("%d fleets.", alienPlayer.getFleets().size()));
    }

    public void updateTechnologyText(Technology technology) {
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
