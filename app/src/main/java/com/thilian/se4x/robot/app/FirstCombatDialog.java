package com.thilian.se4x.robot.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.thilian.se4x.robot.game.Fleet;

public class FirstCombatDialog extends DialogFragment {

    FirstCombatListener listener;

    public static FirstCombatDialog newInstance(Fleet fleet, int view){
        FirstCombatDialog dialog = new FirstCombatDialog();
        Bundle args = new Bundle();
        args.putInt("fleet", fleet.getIndex());
        args.putInt("view", view);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final int fleet = getArguments().getInt("fleet");
        final int view = getArguments().getInt("view");
        final boolean[] selection = new boolean[2];
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.first_combat)
                .setMultiChoiceItems(R.array.first_combat_options, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean checked) {
                        selection[which] = checked;
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.firstCombatPushed(fleet, view, selection[0], selection[1]);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirstCombatDialog.super.dismiss();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (FirstCombatListener) activity;
    }

    public interface FirstCombatListener{
        void firstCombatPushed(int fleet, int view, boolean abovePlanet, boolean enemyNPA);
    }
}
