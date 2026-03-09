package com.z.loa;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.z.loa.MyGdxGame;

/* Launches the Android application. */
public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* You can adjust this configuration to fit your needs */
		AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
		configuration.renderUnderCutout = true;
		initialize(new MyGdxGame(), configuration);
        CrashHandler.getInstance().init(this);
    }
    
}