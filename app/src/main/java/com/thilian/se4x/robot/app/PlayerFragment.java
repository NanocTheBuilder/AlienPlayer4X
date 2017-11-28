package com.thilian.se4x.robot.app;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thilian.se4x.robot.game.AlienPlayer;
import com.thilian.se4x.robot.game.Game;
import com.thilian.se4x.robot.game.enums.Technology;

/**
 * TODO: document your custom view class.
 */
public class PlayerFragment extends Fragment {
    private Game game;
    private AlienPlayer alienPlayer;
    private View.OnClickListener onClickListener;

    public void setGame(Game game){
        this.game = game;
    }

    public void setAlienPlayer(AlienPlayer alienPlayer){
        this.alienPlayer = alienPlayer;
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.player_layout, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getView().setBackgroundColor(Color.parseColor(alienPlayer.getColor().toString()));
        if(onClickListener != null)
            getView().setOnClickListener(onClickListener);
        update();
    }

    public void update(){
        TextView textView;
        int id,sid;
        for(Technology technology : Technology.values()){
            id = getResources().getIdentifier(technology.toString().toLowerCase() + "_text", "id", getActivity().getPackageName());
            if(id != 0){
                textView = getView().findViewById(id);
                sid = getResources().getIdentifier(technology.toString(), "string", getActivity().getPackageName());
                int level = alienPlayer.getLevel(technology);
                textView.setText(getResources().getString(sid, level));
                if(level != game.technologyPrices.getStartingLevel(technology)){
                    textView.setTypeface(textView.getTypeface(), 1);
                }
            }
        }

        TextView fleets = getView().findViewById(R.id.fleets_text);
        fleets.setText(String.format("%d fleets.", alienPlayer.getFleets().size()));
    }
}
