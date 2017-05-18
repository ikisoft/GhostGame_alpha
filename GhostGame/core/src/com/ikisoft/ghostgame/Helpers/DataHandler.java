package com.ikisoft.ghostgame.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Max on 17.5.2017.
 */

public class DataHandler {

    public static Preferences prefs;
    public static int highscore;
    public static int exp;
    public static int lvl;
    public static int spookedMobs;
    public static int selectedCharacter;
    public static int selectedCharacterPreview;
    public static boolean pirateUnlocked;
    public static boolean ninjaUnlocked;
    public static boolean kingUnlocked;
    public static boolean soundMuted;
    public static boolean musicMuted;


    public static void load(){

        prefs = Gdx.app.getPreferences("SG_prefs");
        highscore = prefs.getInteger("highscore");
        exp = prefs.getInteger("exp");
        lvl = prefs.getInteger("lvl");
        spookedMobs = prefs.getInteger("spookedMobs");
        selectedCharacter = prefs.getInteger("selectedCharacter");
        selectedCharacterPreview = 0;
        pirateUnlocked = prefs.getBoolean("pirateUnlocked");
        ninjaUnlocked = prefs.getBoolean("ninjaUnlocked");
        kingUnlocked = prefs.getBoolean("kingUnlocked");
        soundMuted = prefs.getBoolean("soundMuted");
        musicMuted = prefs.getBoolean("musicMuted");

    }

    public static void save(){

        prefs.putInteger("highscore", highscore);
        prefs.putInteger("exp", exp);
        prefs.putInteger("lvl", lvl);
        prefs.putInteger("spookedMobs", spookedMobs);
        prefs.putInteger("selectedCharacter", selectedCharacter);
        prefs.putBoolean("pirateUnlocked", pirateUnlocked);
        prefs.putBoolean("ninjaUnlocked", ninjaUnlocked);
        prefs.putBoolean("kingUnlocked", kingUnlocked);
        prefs.putBoolean("soundMuted", soundMuted);
        prefs.putBoolean("musicMuted", musicMuted);
        prefs.flush();

    }



    public static void muteSound() {

        if (!soundMuted) {
            soundMuted = true;
        } else {
            soundMuted = false;
        }
    }

    public static void muteMusic() {

        if (!musicMuted) {
            musicMuted = true;
            AssetLoader.theme.setVolume(0);

        } else {
            musicMuted = false;
            AssetLoader.theme.setVolume(0.5f);
            AssetLoader.theme.play();

        }
    }


}
