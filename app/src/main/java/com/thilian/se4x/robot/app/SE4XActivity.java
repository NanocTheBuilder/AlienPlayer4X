package com.thilian.se4x.robot.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thilian.se4x.robot.game.EconPhaseResult;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.FleetBuildResult;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.Technology;

import java.util.List;
import java.util.Map;

public class SE4XActivity extends Activity {

    protected Game getGame() {
        return ((SE4XApplication)getApplication()).getGame();
    }

    protected String getFleetDetails(Fleet fleet){
        if(fleet.getGroups().isEmpty()){
            return getResources().getString(R.string.fleetCp, fleet.getFleetCP());
        }
        else {
            StringBuilder sb = new StringBuilder();
            int sid;
            for (Group group : fleet.getGroups()) {
                sid = getResources().getIdentifier(group.getShipType().toString(), "string", getPackageName());
                sb.append(getResources().getString(sid, group.getSize()));
            }
            return sb.toString();
        }
    }

    protected String getFleetName(Fleet fleet){
        int sid = getResources().getIdentifier(fleet.getFleetType().toString(), "string", getPackageName());
        return getResources().getString(sid, fleet.getName());
    }

    public void showEconPhaseResult(List<EconPhaseResult> results) {
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getSystemService(service);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        for(EconPhaseResult result : results){
            ConstraintLayout playerResultView = new ConstraintLayout(this);
            li.inflate(R.layout.econ_phase_result, playerResultView, true);

            playerResultView.setBackgroundColor(Color.parseColor(result.getAlienPlayer().getColor().toString()));

            setResultText(playerResultView, R.id.fleet_cp_text, R.string.fleet_cp_result, result.getFleetCP());
            setResultText(playerResultView, R.id.tech_cp_text, R.string.tech_cp_result, result.getTechCP());
            setResultText(playerResultView, R.id.def_cp_text, R.string.def_cp_result, result.getDefCP());
            setResultText(playerResultView, R.id.extra_econ_roll_text, R.string.extra_econ_result, result.getExtraEcon());

            TextView fleetView = (TextView) playerResultView.findViewById(R.id.new_fleet_text);
            if(result.getFleet() != null){
                Fleet fleet = result.getFleet();
                fleetView.setText(String.format("%s (%s)", getFleetName(fleet), getFleetDetails(fleet)));
            }
            else{
                fleetView.setVisibility(View.GONE);
            }
            layout.addView(playerResultView);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout)
                .setTitle(getResources().getString(R.string.econPhase, getGame().currentTurn - 1))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    private void setResultText(ConstraintLayout view, int rid, int sid, int value) {
        if(value != 0) {
            ((TextView)view.findViewById(rid)).setText(getResources().getString(sid, value));
        }
        else
            view.findViewById(rid).setVisibility(View.GONE);
    }

    public void showFleetBuildResult(FleetBuildResult result) {
        StringBuilder message = new StringBuilder();
        for (Map.Entry<Technology, Integer> entry : result.getNewTechs().entrySet()) {
            int sid = getResources().getIdentifier(entry.getKey().toString(), "string", getPackageName());
            message.append(getResources().getString(sid, entry.getValue())).append("\n");
        }

        for(Fleet fleet : result.getNewFleets()){
            int sid = getResources().getIdentifier(fleet.getFleetType().toString(), "string", getPackageName());
            message.append(getFleetName(fleet)).append("\n");
            message.append(getFleetDetails(fleet)).append("\n");
        }

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(this);
        textView.setBackgroundColor(Color.parseColor(result.getAlienPlayer().getColor().toString()));
        textView.setText(message);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.newFleets).setMessage(message.toString());
        builder.setView(layout);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
