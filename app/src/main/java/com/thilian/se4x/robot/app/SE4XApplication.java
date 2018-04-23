package com.thilian.se4x.robot.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
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
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.RealDiceRoller;

import java.util.List;

/**
 * Created by thili on 2017. 11. 20..
 */

public class SE4XApplication extends Application {

    private Game game;

    @Override
    public void onCreate() {
        super.onCreate();
        game = new Game();
        game.roller = new RealDiceRoller();
    }

    public Game getGame() {
        return game;
    }

    public void showNewFleets(Activity activity, List<Fleet> newFleets) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        StringBuilder message = new StringBuilder();
        for(Fleet fleet : newFleets){
            int sid = getResources().getIdentifier(fleet.getFleetType().toString(), "string", getPackageName());
            message.append(String.format("[%s] %s\n", fleet.getAp().getColor(), getResources().getString(sid, fleet.getName())));
        }
        builder.setTitle(R.string.newFleets).setMessage(message.toString());
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showEconPhaseResult(Activity activity, List<EconPhaseResult> results) {
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) activity.getSystemService(service);

        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.VERTICAL);
        for(EconPhaseResult result : results){
            ConstraintLayout playerResultView = new ConstraintLayout(activity);
            li.inflate(R.layout.econ_phase_result, playerResultView, true);

            playerResultView.setBackgroundColor(Color.parseColor(result.getAlienPlayer().getColor().toString()));

            setResultText(playerResultView, result.getFleetCP(), R.id.fleet_cp_text, R.string.fleet_cp_result);
            setResultText(playerResultView, result.getTechCP(), R.id.tech_cp_text, R.string.tech_cp_result);
            setResultText(playerResultView, result.getDefCP(), R.id.def_cp_text, R.string.def_cp_result);
            setResultText(playerResultView, result.getExtraEcon(), R.id.extra_econ_roll_text, R.string.extra_econ_result);

            TextView fleetView = (TextView) playerResultView.findViewById(R.id.new_fleet_text);
            if(result.getFleet() != null){
                Fleet fleet = result.getFleet();
                StringBuilder sb = new StringBuilder();
                int sid = getResources().getIdentifier(fleet.getFleetType().toString(), "string", activity.getPackageName());
                sb.append(getResources().getString(sid, fleet.getName())).append(" (");

                if(fleet.getGroups().isEmpty()){
                    sb.append(getResources().getString(R.string.fleetCp, fleet.getFleetCP()));
                }
                else {
                    for (Group group : fleet.getGroups()) {
                        sid = getResources().getIdentifier(group.getShipType().toString(), "string", activity.getPackageName());
                        sb.append(getResources().getString(sid, group.getSize()));
                    }
                }
                sb.append(")");
                fleetView.setText(sb.toString());
            }
            else{
                fleetView.setVisibility(View.GONE);
            }
            layout.addView(playerResultView);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(layout)
                .setTitle(getResources().getString(R.string.econPhase, getGame().currentTurn))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    public void setResultText(ConstraintLayout view, int cp, int rid, int sid) {
        if(cp != 0) {
            ((TextView)view.findViewById(rid)).setText(getResources().getString(sid, cp));
        }
        else
            view.findViewById(rid).setVisibility(View.GONE);
    }
}
