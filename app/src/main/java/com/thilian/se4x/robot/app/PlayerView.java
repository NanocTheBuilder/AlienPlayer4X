package com.thilian.se4x.robot.app;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.Technology;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4;
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
        findViewById(R.id.eliminate_button).setEnabled(true);
    }

    public void setBackgroundColor(){
        setBackgroundColor(Color.parseColor(alienPlayer.getColor().toString()));
    }

    public void update(){
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

        TextView fleets = findViewById(R.id.fleets_text);
        fleets.setText(String.format("%d fleets.", alienPlayer.getFleets().size()));
    }
}
