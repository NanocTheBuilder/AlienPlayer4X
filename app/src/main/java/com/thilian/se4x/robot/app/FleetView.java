package com.thilian.se4x.robot.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.FleetBuildOption;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4Player;

/**
 * TODO: document your custom view class.
 */
public class FleetView extends RelativeLayout{

    private Fleet fleet;
    private FleetRevealListener fleetRevealListener;

    public FleetView(final Context context, final Fleet fleet, final FleetRevealListener fleetRevealListener){
        super(context);
        this.fleet = fleet;
        this.fleetRevealListener = fleetRevealListener;

        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
        li.inflate(R.layout.fleet_layout, this, true);

        Button firstCombatButton = findViewById(R.id.first_combat_button);
        firstCombatButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fleet.getAp().firstCombat(fleet);
                update();
                fleetRevealListener.onFleetRevealed(fleet);
            }
        });

        Button removeFleetButton = findViewById(R.id.remove_fleet_button);
        removeFleetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fleet.getAp().removeFleet(fleet);
                FleetsActivity activity = (FleetsActivity) getContext();
                LinearLayout layout = activity.findViewById(R.id.fleets);
                layout.removeView(FleetView.this);
                fleetRevealListener.onFleetRevealed(null);
            }
        });

        if(fleet.getAp() instanceof Scenario4Player){
            Button firstCombatAbovePlanetButton = findViewById(R.id.first_combat_above_planet_button);
            firstCombatAbovePlanetButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    fleet.getAp().firstCombat(fleet, FleetBuildOption.COMBAT_IS_ABOVE_PLANET);
                    update();
                    fleetRevealListener.onFleetRevealed(fleet);
                }
            });
        }

        update();
    }

    public void update(){
        TextView fleetNameText = findViewById(R.id.fleet_name_text);
        int sid = getResources().getIdentifier(fleet.getFleetType().toString(), "string", getContext().getPackageName());
        fleetNameText.setText(getResources().getString(sid, fleet.getName()));

        TextView groupsText = findViewById(R.id.groups_text);
        StringBuilder sb = new StringBuilder();
        for(Group group : fleet.getGroups()){
            sid = getResources().getIdentifier(group.getShipType().toString(), "string", getContext().getPackageName());
            sb.append(getResources().getString(sid, group.getSize()));
        }
        groupsText.setText(sb.toString());

        Button revealFleetButton = findViewById(R.id.first_combat_button);
        revealFleetButton.setVisibility(fleet.hadFirstCombat() ? INVISIBLE : VISIBLE);

        Button removeFleetButton = findViewById(R.id.remove_fleet_button);
        removeFleetButton.setVisibility(fleet.hadFirstCombat() ? VISIBLE : INVISIBLE);

        if(fleet.getAp() instanceof Scenario4Player){
            Button revealAbovePlanetButton = findViewById(R.id.first_combat_above_planet_button);
            revealAbovePlanetButton.setVisibility(fleet.hadFirstCombat() ? INVISIBLE : VISIBLE);
        }
        else{
            findViewById(R.id.first_combat_above_planet_button).setVisibility(GONE);
        }
    }

    public interface FleetRevealListener{
        public void onFleetRevealed(Fleet fleet);
    }
}
