package org.mrMinepPist.towerdefence;

import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        
        initialize(new DefenceGame(), cfg);
    }
}
