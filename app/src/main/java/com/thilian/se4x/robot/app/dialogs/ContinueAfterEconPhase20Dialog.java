/*
 * Copyright (C) 2018 Balázs Péter
 *
 * This file is part of Alien Player 4X.
 *
 * Alien Player 4X is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alien Player 4X is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Alien Player 4X.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.thilian.se4x.robot.app.dialogs;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.thilian.se4x.robot.app.R;
import com.thilian.se4x.robot.app.SE4XActivity;

public class ContinueAfterEconPhase20Dialog {
    private final SE4XActivity activity;

    public ContinueAfterEconPhase20Dialog(SE4XActivity activity){
        this.activity = activity;
    }

    public void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.do_you_want_to_continue_title)
                .setMessage(R.string.do_you_want_to_continue_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(activity instanceof  ContinueAfterEconPhase20Listener){
                            ((ContinueAfterEconPhase20Listener)activity).continueAfterEconPhase20Clicked();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    public static interface ContinueAfterEconPhase20Listener{
        void continueAfterEconPhase20Clicked();
    }
}
