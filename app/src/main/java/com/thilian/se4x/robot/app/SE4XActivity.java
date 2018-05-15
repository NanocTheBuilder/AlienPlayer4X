package com.thilian.se4x.robot.app;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public abstract class SE4XActivity extends Activity {
    protected void loadBackgroundImage() {
        ImageView background = findViewById(R.id.background_image);
        Glide.with(this).load(R.drawable.smc_wing_full_2560).into(background);
    }
}
