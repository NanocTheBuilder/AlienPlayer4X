package com.thilian.se4x.robot.app.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.thilian.se4x.robot.app.FleetView;
import com.thilian.se4x.robot.app.R;
import com.thilian.se4x.robot.app.SE4XGameActivity;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpSoloScenario;

public class FirstCombatDialog{
    SE4XGameActivity activity;

    public FirstCombatDialog(SE4XGameActivity activity) {
        this.activity = activity;
    }

    public void show(final Fleet fleet, final FleetView fleetView, final FirstCombatListener listener) {
        int id = activity.getGame().scenario instanceof VpSoloScenario ?
            R.array.first_combat_options_vp : R.array.first_combat_options_s4;
        final boolean[] selection = new boolean[2];
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.first_combat)
                .setMultiChoiceItems(id, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean checked) {
                        selection[which] = checked;
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.firstCombatPushed(fleet, fleetView, selection[0], selection[1]);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    public interface FirstCombatListener{
        void firstCombatPushed(Fleet fleet, FleetView view, boolean abovePlanet, boolean enemyNPA);
    }
}
