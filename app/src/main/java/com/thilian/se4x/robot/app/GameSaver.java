/*
 *  Copyright (C) 2018 Balázs Péter
 *
 *  This file is part of Alien Player 4X.
 *
 *  Alien Player 4X is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Alien Player 4X is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Alien Player 4X.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.thilian.se4x.robot.app;

import android.content.Context;

import com.thilian.se4x.robot.app.parser.JsonParser;
import com.thilian.se4x.robot.game.Game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameSaver {
    static final String GAME_FILE = "saved.game";
    JsonParser jsonParser = new JsonParser();

    public Game loadGame(Context context) {
        //Log.i(getClass().getSimpleName(), "LOAD_GAME");
        Game game = null;
        BufferedReader reader = null;
        File file = new File(context.getFilesDir(), GAME_FILE);
        if(file.exists()) {
            try {
                reader = new BufferedReader(new FileReader(file));
                String json = reader.readLine();
                if (json != null && !json.isEmpty())
                    game = jsonParser.fromJson(json);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return game;
    }

    public void saveGame(Game game, Context context) {
        //Log.i(getClass().getSimpleName(), "SAVE_GAME");
        File file = new File(context.getFilesDir(), GAME_FILE);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, false));
            writer.write(jsonParser.toJson(game));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public long getGameDate(Context context){
        File file = new File(context.getFilesDir(), GAME_FILE);
        return file.lastModified();
    }

    public boolean isSaveExists(Context context){
        File file = new File(context.getFilesDir(), GAME_FILE);
        return file.exists() && file.length() != 4; //"null"
    }
}