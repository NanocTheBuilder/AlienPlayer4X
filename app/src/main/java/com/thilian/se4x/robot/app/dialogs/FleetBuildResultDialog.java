package com.thilian.se4x.robot.app.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thilian.se4x.robot.app.R;
import com.thilian.se4x.robot.app.SE4XActivity;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.FleetBuildResult;
import com.thilian.se4x.robot.game.enums.Technology;

import java.util.Map;

public class FleetBuildResultDialog {
    private SE4XActivity activity;

    public FleetBuildResultDialog(SE4XActivity activity) {
        this.activity = activity;
    }

    public void show(FleetBuildResult result) {
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(8, 8, 8, 8);
        layout.setBackgroundColor(activity.getColor(result.getAlienPlayer()));

        String packageName = activity.getPackageName();
        Resources resources = activity.getResources();

        for (Map.Entry<Technology, Integer> entry : result.getNewTechs().entrySet()) {
            int sid = resources.getIdentifier(entry.getKey().toString(), "string", packageName);
            TextView textView = new TextView(activity);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(resources.getString(sid, entry.getValue()));
            layout.addView(textView);
        }

        for(Fleet fleet : result.getNewFleets()){
            int sid = resources.getIdentifier(fleet.getFleetType().toString(), "string", packageName);
            TextView textView = new TextView(activity);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(activity.getFleetName(fleet)+"\n"+activity.getFleetDetails(fleet));
            layout.addView(textView);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(layout)
                .setTitle(R.string.newFleets)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
    }

}
