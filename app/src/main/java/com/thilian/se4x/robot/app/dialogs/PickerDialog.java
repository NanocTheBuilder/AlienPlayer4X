package com.thilian.se4x.robot.app.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.thilian.se4x.robot.app.R;
import com.thilian.se4x.robot.app.SE4XGameActivity;
import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.enums.Technology;

public class PickerDialog {
    SE4XGameActivity activity;

    public PickerDialog(SE4XGameActivity activity) {
        this.activity = activity;
    }

    public void showLevelPickerDialog(final AlienPlayer alienPlayer, final Technology technology, PickerClickAction action){
        String sid = String.format("%s_label", technology.toString());
        show(
                activity.getResources().getIdentifier(sid, "string", activity.getPackageName()),
                activity.getGame().scenario.getStartingLevel(technology),
                activity.getGame().scenario.getMaxLevel(technology),
                alienPlayer.getLevel(technology),
                action);
    }

    public void showSeenLevelPickerDialog(Technology technology, PickerClickAction action){
        String sid = String.format("%s_label", technology.toString());
        show(
                activity.getResources().getIdentifier(sid, "string", activity.getPackageName()),
                activity.getGame().scenario.getStartingLevel(technology),
                activity.getGame().scenario.getMaxLevel(technology),
                activity.getGame().getSeenLevel(technology),
                action);
    }

    public void show(int labelString, int minValue, int maxValue, int value, final PickerClickAction action) {
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) activity.getSystemService(service);
        ConstraintLayout levelPickerView = new ConstraintLayout(activity);
        li.inflate(R.layout.level_picker_dialog, levelPickerView, true);

        TextView label = levelPickerView.findViewById(R.id.technology_label);
        label.setText(labelString);

        final NumberPicker picker = levelPickerView.findViewById(R.id.level_picker);
        picker.setMinValue(minValue);
        picker.setMaxValue(maxValue);
        picker.setValue(value);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Select value")
                .setView(levelPickerView)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        action.action(picker.getValue());
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    public interface PickerClickAction{
        void action(int value);
    }

}
