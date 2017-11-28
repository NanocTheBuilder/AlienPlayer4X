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

        Button revealFleetButton = findViewById(R.id.reveal_fleet_button);
        revealFleetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fleet.getAp().buildFleet(fleet);
                update();
                fleetRevealListener.onFleetRevealed(fleet);
            }
        });

        Button removeFleetButton = findViewById(R.id.remove_fleet_button);
        removeFleetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fleet.getAp().destroyFleet(fleet);
                FleetsActivity activity = (FleetsActivity) getContext();
                LinearLayout layout = activity.findViewById(R.id.fleets);
                layout.removeView(FleetView.this);
            }
        });

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

        Button revealFleetButton = findViewById(R.id.reveal_fleet_button);
        revealFleetButton.setVisibility(fleet.isBuilt() ? INVISIBLE : VISIBLE);

        Button removeFleetButton = findViewById(R.id.remove_fleet_button);
        removeFleetButton.setVisibility(fleet.isBuilt() ? VISIBLE : INVISIBLE);
    }

    public interface FleetRevealListener{
        public void onFleetRevealed(Fleet fleet);
    }
}
