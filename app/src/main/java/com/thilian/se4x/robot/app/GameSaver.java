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
import java.io.PrintWriter;

public class GameSaver {
    static final String GAME_FILE = "saved.game";
    JsonParser jsonParser = new JsonParser();

    public GameSaver() {
    }

    public Game loadGame(Context context) {
        Game game = null;
        BufferedReader reader = null;
        File file = new File(context.getFilesDir(), GAME_FILE);
        if(file.exists()) {
            try {
                reader = new BufferedReader(new FileReader(file));
                String json = reader.readLine();
                if (!json.isEmpty())
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

    public void deleteGame(Context context){
        File file = new File(context.getFilesDir(), GAME_FILE);
        try {
            new PrintWriter(file).close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}