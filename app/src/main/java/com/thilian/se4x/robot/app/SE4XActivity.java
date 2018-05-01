package com.thilian.se4x.robot.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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

import static com.thilian.se4x.robot.game.enums.ShipType.TRANSPORT;

public class SE4XActivity extends Activity {

    public Game getGame() {
        return ((SE4XApplication)getApplication()).getGame();
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
            Group group;
            StringBuilder sb = new StringBuilder();
            boolean hasFullTransport = TRANSPORT.equals(groups.get(0).getShipType());
            for (int i = 0; i < groups.size(); i++) {
                group = groups.get(i);
                sid = getResources().getIdentifier(group.getShipType().toString(), "string", getPackageName());
                sb.append(getResources().getString(sid, group.getSize()));
                if(hasFullTransport && i == 2)
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

    public void showEconPhaseResult(List<EconPhaseResult> results){
        new EconPhaseResultDialog(this).show(results);
    }

    public void showFleetBuildResult(FleetBuildResult result) {
        new FleetBuildResultDialog(this).show(result);
    }

    public void showPickerDialog(int labelString, int minValue, int maxValue, int value, final PickerDialog.PickerClickAction action) {
        new PickerDialog(this).show(labelString, minValue, maxValue, value, action);
    }

    public void showLevelPickerDialog(final AlienPlayer alienPlayer, final Technology technology, final PlayerView playerView){
        new PickerDialog(this).showLevelPickerDialog(alienPlayer, technology, playerView);
    }

    public void showFirstCombatDialog(final Fleet fleet, final FleetView fleetView, FirstCombatDialog.FirstCombatListener listener){
        new FirstCombatDialog(this).show(fleet, fleetView, listener);
    }
}
