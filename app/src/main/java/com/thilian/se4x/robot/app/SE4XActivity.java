package com.thilian.se4x.robot.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.thilian.se4x.robot.app.dialogs.EconPhaseResultDialog;
import com.thilian.se4x.robot.app.dialogs.FirstCombatDialog;
import com.thilian.se4x.robot.app.dialogs.FleetBuildResultDialog;
import com.thilian.se4x.robot.app.dialogs.PickerDialog;
import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.EconPhaseResult;
import com.thilian.se4x.robot.game.Fleet;
import com.thilian.se4x.robot.game.FleetBuildResult;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.Group;
import com.thilian.se4x.robot.game.enums.Technology;

import java.util.List;

import static com.thilian.se4x.robot.game.enums.ShipType.GRAV_ARMOR;
import static com.thilian.se4x.robot.game.enums.ShipType.HEAVY_INFANTRY;
import static com.thilian.se4x.robot.game.enums.ShipType.TRANSPORT;

public class SE4XActivity extends Activity {
    final GameSaver gameSaver = new GameSaver();

    private Game game;
    private long gameDate;

    public Game getGame() {
        if(game == null || gameDate != gameSaver.getGameDate(this)){
            game = loadGame();
        }
        return game;
    }

    protected Game loadGame(){
        game = gameSaver.loadGame(this);
        gameDate = gameSaver.getGameDate(this);
        return game;
    }

    protected void saveGame(){
        gameSaver.saveGame(game, this);
        gameDate = gameSaver.getGameDate(this);
    }

    protected void deleteGame(){
        game = null;
    }

    public boolean isShowDetails(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPref.getBoolean("pref_show_details", false);
    }

    public String getFleetDetails(Fleet fleet){
        List<Group> groups = fleet.getGroups();
        if(groups.isEmpty()){
            if(isShowDetails())
                return getResources().getString(R.string.fleet_fleet_cp, fleet.getFleetCP());
            else
                return "";
        }
        else {
            int sid;
            StringBuilder sb = new StringBuilder();
            int lineBreak = -1;
            if(TRANSPORT.equals(groups.get(0).getShipType())){
                if(groups.size() > 4 && GRAV_ARMOR.equals(groups.get(3).getShipType()))
                    lineBreak = 3;
                else if(groups.size() > 3 && HEAVY_INFANTRY.equals(groups.get(2).getShipType()))
                    lineBreak = 2;
                else
                    lineBreak = 1;
            }
            Group group;
            for (int i = 0; i < groups.size(); i++) {
                group = groups.get(i);
                sid = getResources().getIdentifier(group.getShipType().toString(), "string", getPackageName());
                sb.append(getResources().getString(sid, group.getSize()));
                if(i == lineBreak)
                    sb.append("\n");
                else if(i < groups.size() - 1)
                    sb.append(" ");
            }
            return sb.toString();
        }
    }

    public String getFleetName(Fleet fleet){
        int sid = getResources().getIdentifier(fleet.getFleetType().toString(), "string", getPackageName());
        return getResources().getString(sid, fleet.getName());
    }

    protected void showEconPhaseResult(List<EconPhaseResult> results){
        new EconPhaseResultDialog(this).show(results);
    }

    protected void showFleetBuildResult(FleetBuildResult result) {
        new FleetBuildResultDialog(this).show(result);
    }

    protected void showPickerDialog(int labelString, int minValue, int maxValue, int value, final PickerDialog.PickerClickAction action) {
        new PickerDialog(this).show(labelString, minValue, maxValue, value, action);
    }

    protected void showLevelPickerDialog(final AlienPlayer alienPlayer, final Technology technology, PickerDialog.PickerClickAction action){
        new PickerDialog(this).showLevelPickerDialog(alienPlayer, technology, action);
    }

    protected void showFirstCombatDialog(final Fleet fleet, final FleetView fleetView, FirstCombatDialog.FirstCombatListener listener){
        new FirstCombatDialog(this).show(fleet, fleetView, listener);
    }

    protected void loadBackgroundImage() {
        ImageView background = findViewById(R.id.background_image);
        Glide.with(this).load(R.drawable.ngc_7635_2560).into(background);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveGame();
//        Log.i(getClass().getSimpleName(), "ON_PAUSE");
    }

    /*
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(getClass().getSimpleName(), "ON_CREATE");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(getClass().getSimpleName(), "ON_START");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(getClass().getSimpleName(), "ON_RESTART");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(getClass().getSimpleName(), "ON_RESUME");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(getClass().getSimpleName(), "ON_STOP");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(getClass().getSimpleName(), "ON_DESTROY");
    }
    */
}
