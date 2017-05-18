package com.ikisoft.ghostgame.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Max on 17.5.2017.
 */

public class DataHandler {
    public static Preferences prefs = Gdx.app.getPreferences("SG_prefs");

    if(prefs.getBoolean("updated")==false){
        prefs.putInteger("exp", 2490);
        prefs.putBoolean("pirateUnlocked", false);
        prefs.putBoolean("ninjaUnlocked", false);
        prefs.putBoolean("kingUnlocked", false);
        prefs.putInteger("texture", 1);
        prefs.putBoolean("updated", true);
        prefs.flush();
    }

}
