package com.thilian.se4x.robot.app.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thilian.se4x.robot.app.R;
import com.thilian.se4x.robot.app.SE4XActivity;
import com.thilian.se4x.robot.game.EconPhaseResult;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.enums.Technology;

import java.util.List;

public class EconPhaseResultDialog {

    private SE4XActivity activity;

    public EconPhaseResultDialog(SE4XActivity activity){
        this.activity = activity;
    }

    public void show(List<EconPhaseResult> results) {
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) activity.getSystemService(service);

        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(8, 8, 8, 8);
        boolean noNewFleet = true;
        for(EconPhaseResult result : results){
            ConstraintLayout playerResultView = new ConstraintLayout(activity);
            li.inflate(R.layout.econ_phase_result, playerResultView, true);
            playerResultView.setBackgroundColor(activity.getColor(result.getAlienPlayer()));

            setResultText(playerResultView, R.id.fleet_cp_text, R.string.fleet_cp_result, result.getFleetCP());
            setResultText(playerResultView, R.id.tech_cp_text, R.string.tech_cp_result, result.getTechCP());
            setResultText(playerResultView, R.id.def_cp_text, R.string.def_cp_result, result.getDefCP());
            setResultText(playerResultView, R.id.extra_econ_roll_text, R.string.extra_econ_result, result.getExtraEcon());
            setResultText(playerResultView, R.id.move_text, R.string.new_move, result.getAlienPlayer().getLevel(Technology.MOVE), result.isMoveTechRolled());

            TextView fleetView = playerResultView.findViewById(R.id.new_fleet_text);
            if(result.getFleet() != null){
                noNewFleet = false;
                Fleet fleet = result.getFleet();
                String fleetDetails = activity.getFleetDetails(fleet);
                if("".equals(fleetDetails))
                    fleetView.setText(activity.getFleetName(fleet));
                else
                    fleetView.setText(String.format("%s (%s)", activity.getFleetName(fleet), activity.getFleetDetails(fleet)));
            }
            else{
                fleetView.setVisibility(View.GONE);
            }
            layout.addView(playerResultView);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if(!activity.isShowDetails() && noNewFleet){
            builder.setMessage(R.string.no_new_fleets);
        }
        else {
            builder.setView(layout);
        }
        builder.setTitle(activity.getResources().getString(R.string.econPhase, activity.getGame().currentTurn - 1))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    private void setResultText(ConstraintLayout view, int rid, int sid, int value){
        setResultText(view, rid, sid, value, value != 0 && activity.isShowDetails());
    }

    private void setResultText(ConstraintLayout view, int rid, int sid, int value, boolean isVisible) {
        if(isVisible) {
            ((TextView)view.findViewById(rid)).setText(activity.getResources().getString(sid, value));
        }
        else
            view.findViewById(rid).setVisibility(View.GONE);
    }

}
