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


}
