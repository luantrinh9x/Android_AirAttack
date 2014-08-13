package com.me.Main;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.gdx.main.AirAttack;
import com.parse.Parse;
import com.parse.ParseAnalytics;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        cfg.useAccelerometer = true;
        cfg.useCompass = true;
        //
        Parse.initialize(this, "zigHJHXbcLJYyrHuFtTnuR5xokjk3dy4n5QSsGQ8", "zKLzzmL3EBKf511aESbXGRC0w4kzZpQ0Bl04QtlP");
        ParseAnalytics.trackAppOpened(getIntent());
        //
        initialize(new AirAttack(), cfg);
    }
}