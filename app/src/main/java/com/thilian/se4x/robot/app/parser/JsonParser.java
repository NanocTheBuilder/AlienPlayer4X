package com.thilian.se4x.robot.app.parser;

import android.support.annotation.NonNull;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import com.thilian.se4x.robot.game.AlienEconomicSheet;
import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.RealDiceRoller;
import com.thilian.se4x.robot.game.Scenario;
import com.thilian.se4x.robot.game.enums.Difficulty;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameDifficulty;
import com.thilian.se4x.robot.game.scenarios.basegame.BaseGameScenario;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4;
import com.thilian.se4x.robot.game.scenarios.scenario4.Scenario4Player;
import com.thilian.se4x.robot.game.scenarios.vpscenario.Vp2pScenario;
import com.thilian.se4x.robot.game.scenarios.vpscenario.Vp3pScenario;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpAlienPlayer;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpDifficulties;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpEconomicSheet;
import com.thilian.se4x.robot.game.scenarios.vpscenario.VpSoloScenario;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class JsonParser {
    private Gson gson = createGson();

    public String toJson(Game game){
        return gson.toJson(game);
    }

    public Game fromJson(String json) {
        Game game = gson.fromJson(json, Game.class);
        if(game != null) {
            game.roller = new RealDiceRoller();
            game.scenario.init(game);
            for (AlienPlayer ap : game.aliens) {
                ap.setGame(game);
                for (Fleet fleet : ap.getFleets()) {
                    fleet.setAlienPlayer(ap);
                }
            }
        }
        return game;
    }

    @NonNull
    private Gson createGson() {
        RuntimeTypeAdapterFactory<AlienPlayer> alienPlayerAdapterFactory
                = RuntimeTypeAdapterFactory.of(AlienPlayer.class)
                    .registerSubtype(AlienPlayer.class)
                    .registerSubtype(Scenario4Player.class)
                    .registerSubtype(VpAlienPlayer.class);

        RuntimeTypeAdapterFactory<AlienEconomicSheet> economicSheetAdapterFactory
                = RuntimeTypeAdapterFactory.of(AlienEconomicSheet.class)
                    .registerSubtype(AlienEconomicSheet.class)
                    .registerSubtype(VpEconomicSheet.class);

        TypeAdapterFactory difficultyTypeAdapterFactory = new TypeAdapterFactory() {
            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
                if(Difficulty.class.isAssignableFrom(type.getRawType()))
                    return (TypeAdapter<T>) new DifficultyTypeAdapter().nullSafe();
                else
                    return null;
            }
        };

        return new GsonBuilder()
                .registerTypeAdapterFactory(alienPlayerAdapterFactory)
                .registerTypeAdapterFactory(economicSheetAdapterFactory)
                .registerTypeAdapterFactory(difficultyTypeAdapterFactory)
                .registerTypeAdapter(Scenario.class, new ScenarioSerializer())
                .setExclusionStrategies(new AlienPlayerExclusionStrategy())
                .create();
    }

    static class ScenarioSerializer implements JsonSerializer<Scenario>, JsonDeserializer<Scenario>{
        private static Map<String, Class<? extends Scenario>> typeMap = new HashMap<>();
        static {
            addTypeToMap(BaseGameScenario.class);
            addTypeToMap(Scenario4.class);
            addTypeToMap(VpSoloScenario.class);
            addTypeToMap(Vp2pScenario.class);
            addTypeToMap(Vp3pScenario.class);
        }

        private static void addTypeToMap(Class clazz){
            typeMap.put(clazz.getSimpleName(), clazz);
        }

        @Override
        public Scenario deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                return  typeMap.get(json.getAsString()).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public JsonElement serialize(Scenario src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getClass().getSimpleName());
        }
    }

    static class DifficultyTypeAdapter extends TypeAdapter<Difficulty>{
        private static Map<String, Class> typeMap = new HashMap<>();
        static {
            addTypeToMap(BaseGameDifficulty.class);
            addTypeToMap(VpDifficulties.VpSoloDifficulty.class);
            addTypeToMap(VpDifficulties.Vp2pDifficulty.class);
            addTypeToMap(VpDifficulties.Vp3pDifficulty.class);
        }

        private static void addTypeToMap(Class clazz){
            typeMap.put(clazz.getSimpleName(), clazz);
        }

        @Override
        public void write(JsonWriter out, Difficulty value) throws IOException {
            String json = String.format("%s.%s", value.getClass().getSimpleName(), value.toString());
            out.value(json);
        }

        @Override
        public Difficulty read(JsonReader in) throws IOException {
            String[] typeAndValue = in.nextString().split("\\.");
            return (Difficulty) Enum.valueOf(typeMap.get(typeAndValue[0]), typeAndValue[1]);
        }
    }

    class AlienPlayerExclusionStrategy implements ExclusionStrategy{

        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getName().equals("game")
                    || f.getName().equals("ap")
                    || f.getName().equals("roller");
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    }
}
