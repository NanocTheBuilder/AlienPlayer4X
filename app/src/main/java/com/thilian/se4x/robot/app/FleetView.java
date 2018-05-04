package com.thilian.se4x.robot.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thilian.se4x.robot.app.dialogs.FirstCombatDialog;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4Player;

/**
 * TODO: document your custom view class.
 */
public class FleetView extends RelativeLayout{

    private Fleet fleet;
    private FleetRevealListener fleetRevealListener;

    public FleetView(final Context context, final Fleet fleet, final FleetRevealListener fleetRevealListener){
        super(context);
        this.setId(7133700 + fleet.getIndex());
        this.fleet = fleet;
        this.fleetRevealListener = fleetRevealListener;

        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
        li.inflate(R.layout.fleet_layout, this, true);

        final FirstCombatDialog.FirstCombatListener listener = (FirstCombatDialog.FirstCombatListener) context;
        Button firstCombatButton = findViewById(R.id.first_combat_button);
        firstCombatButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fleet.getAp() instanceof Scenario4Player){
                    ((SE4XActivity)context).showFirstCombatDialog(fleet, FleetView.this, listener);
                }
                else {
                    listener.firstCombatPushed(fleet, FleetView.this, false, false);
                }
            }
        });

        Button removeFleetButton = findViewById(R.id.remove_fleet_button);
        removeFleetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fleet.getAp().removeFleet(fleet);
                ((SE4XActivity)context).saveGame();
                FleetsActivity activity = (FleetsActivity) getContext();
                LinearLayout layout = activity.findViewById(R.id.fleets);
                layout.removeView(FleetView.this);
                fleetRevealListener.onFleetRevealed(null);
            }
        });

        update();
    }

    public void update(){
        FleetsActivity activity = (FleetsActivity) getContext();
        TextView fleetNameText = findViewById(R.id.fleet_name_text);
        fleetNameText.setText(activity.getFleetName(fleet));

        TextView groupsText = findViewById(R.id.groups_text);
        groupsText.setText(activity.getFleetDetails(fleet));

        Button firstCombatButton = findViewById(R.id.first_combat_button);
        firstCombatButton.setVisibility(fleet.hadFirstCombat() ? INVISIBLE : VISIBLE);

        Button removeFleetButton = findViewById(R.id.remove_fleet_button);
        removeFleetButton.setVisibility(fleet.hadFirstCombat() ? VISIBLE : INVISIBLE);
    }

    public interface FleetRevealListener{
        public void onFleetRevealed(Fleet fleet);
    }
}
