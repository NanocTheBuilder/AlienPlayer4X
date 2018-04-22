package com.thilian.se4x.robot.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;

import com.thilian.se4x.robot.game.EconPhaseResult;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
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

    public void showNewFleets(Activity activity, List<EconPhaseResult> newFleets) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        StringBuilder message = new StringBuilder();
        /*
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
        */
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
