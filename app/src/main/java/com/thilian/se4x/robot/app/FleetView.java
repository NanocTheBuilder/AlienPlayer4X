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

    public FleetView(final Context context, final Fleet fleet){
        super(context);
        this.setId(7133700 + fleet.getIndex());
        this.fleet = fleet;

        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
        li.inflate(R.layout.fleet_layout, this, true);

        final FirstCombatDialog.FirstCombatListener firstCombatListener
                = (FirstCombatDialog.FirstCombatListener)context;
        Button firstCombatButton = findViewById(R.id.first_combat_button);
        firstCombatButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fleet.getAp() instanceof Scenario4Player){
                    ((SE4XActivity)context).showFirstCombatDialog(fleet, FleetView.this, firstCombatListener);
                }
                else {
                    firstCombatListener.firstCombatPushed(fleet, FleetView.this, false, false);
                }
            }
        });

        final FleetRemoveListener fleetRemoveListener = (FleetRemoveListener)context;
        Button removeFleetButton = findViewById(R.id.remove_fleet_button);
        removeFleetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fleetRemoveListener.onFleetRemoved(fleet.getIndex(), FleetView.this);
            }
        });

        update();
    }

    public void update(){
        SE4XActivity activity = (SE4XActivity) getContext();
        TextView fleetNameText = findViewById(R.id.fleet_name_text);
        fleetNameText.setText(activity.getFleetName(fleet));

        TextView groupsText = findViewById(R.id.groups_text);
        groupsText.setText(activity.getFleetDetails(fleet));

        Button firstCombatButton = findViewById(R.id.first_combat_button);
        firstCombatButton.setVisibility(fleet.hadFirstCombat() ? INVISIBLE : VISIBLE);

        Button removeFleetButton = findViewById(R.id.remove_fleet_button);
        removeFleetButton.setVisibility(fleet.hadFirstCombat() ? VISIBLE : INVISIBLE);
    }

    public interface FleetRemoveListener{
        void onFleetRemoved(int index, FleetView view);
    }
}
