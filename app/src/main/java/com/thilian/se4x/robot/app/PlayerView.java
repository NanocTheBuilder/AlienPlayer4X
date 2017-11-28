package com.thilian.se4x.robot.app;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.Technology;

/**
 * TODO: document your custom view class.
 */
public class PlayerView extends LinearLayout {
    private Game game;
    private AlienPlayer alienPlayer;
    private ViewGroup layout;

    public PlayerView(Context context, Game game, AlienPlayer alienPlayer) {
        super(context);
        this.game = game;
        this.alienPlayer = alienPlayer;

        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
        layout = (ViewGroup) li.inflate(R.layout.player_layout, this, true);
        layout.setBackgroundColor(Color.parseColor(alienPlayer.getColor().toString()));
        update();
    }


    public void update(){
        TextView textView;
        int id,sid;
        for(Technology technology : Technology.values()){
            id = getResources().getIdentifier(technology.toString().toLowerCase() + "_text", "id", getContext().getPackageName());
            if(id != 0){
                textView = layout.findViewById(id);
                sid = getResources().getIdentifier(technology.toString(), "string", getContext().getPackageName());
                int level = alienPlayer.getLevel(technology);
                textView.setText(getResources().getString(sid, level));
                if(level != game.technologyPrices.getStartingLevel(technology)){
                    textView.setTypeface(textView.getTypeface(), 1);
                }
            }
        }

        TextView fleets = layout.findViewById(R.id.fleets_text);
        fleets.setText(String.format("%d fleets.", alienPlayer.getFleets().size()));
    }
}
