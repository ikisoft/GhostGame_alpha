package com.ikisoft.ghostgame.Render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.ikisoft.ghostgame.GameObjects.Ghost;
import com.ikisoft.ghostgame.GameObjects.JumpingMob;
import com.ikisoft.ghostgame.GameObjects.Mob;
import com.ikisoft.ghostgame.GameObjects.ScoreHitbox;
import com.ikisoft.ghostgame.GameObjects.ScrollingBg;
import com.ikisoft.ghostgame.GameObjects.Spike;
import com.ikisoft.ghostgame.GameObjects.Sun;
import com.ikisoft.ghostgame.Helpers.AssetLoader;
import com.ikisoft.ghostgame.UI_Objects.DeathScreen;
import com.ikisoft.ghostgame.UI_Objects.MainMenu;
import com.ikisoft.ghostgame.UI_Objects.Menu;
import com.ikisoft.ghostgame.UI_Objects.Options;
import com.ikisoft.ghostgame.UI_Objects.TutorialScreen;

/**
 * Created by Max on 2.4.2017.
 */

public class GameWorld {


    private Ghost ghost;
    private Mob mob;
    private JumpingMob jumpingMob;
    private Spike spike;
    private ScoreHitbox scoreHitbox;
    private ScrollingBg mountain, mountain2;
    private Sun sun;
    private DeathScreen deathScreen;
    private MainMenu mainMenu;
    private Menu menu;
    private Options options;
    private TutorialScreen tutorialScreen;
    private int score, exp, lvl, spookedMobs;
    private float exptolvl, thislvlexp, nextlvlexp;
    private boolean deathsoundPlayed, gameover, scoreSaved, dataSaved, dev, lvlUp,
    pirateUnlocked, ninjaUnlocked, kingUnlocked;
    private int distance;

    private GameState state;


    public enum GameState {
        MAINMENU, RUNNING, PAUSE, MENU, OPTIONS, GAMEOVER, TUTORIAL;
    }

    public GameWorld() {

        state = GameState.MAINMENU;
        ghost = new Ghost(85, 560);
        spike = new Spike(1080 + 85, 0, this);
        scoreHitbox = new ScoreHitbox(spike);
        mountain = new ScrollingBg(5);
        mountain2 = new ScrollingBg(8);
        sun = new Sun();
        deathScreen = new DeathScreen();
        mainMenu = new MainMenu();
        menu = new Menu();
        options = new Options();
        tutorialScreen = new TutorialScreen();
        mob = new Mob(-100, 552, this);
        jumpingMob = new JumpingMob(1200, 552, this);
        score = 0;
        deathsoundPlayed = false;
        gameover = false;
        dataSaved = false;
        pirateUnlocked = false;
        ninjaUnlocked = false;
        kingUnlocked = false;
        dev = false;
        distance = 0;
        spookedMobs = Gdx.app.getPreferences("SG_prefs").getInteger("spookedmobs");
        //test

        lvl = (int) (0.1 * Math.sqrt(Gdx.app.getPreferences("SG_prefs").getInteger("exp")));
        //formula for exp to level
        //exp-this / next - this
        thislvlexp = (float) Math.pow(lvl, 2) * 100;
        nextlvlexp = (float) Math.pow(lvl + 1, 2) * 100;
        exptolvl = (Gdx.app.getPreferences("SG_prefs").getInteger("exp") - thislvlexp) / (nextlvlexp - thislvlexp);
    }

    public void update(float delta) {

        switch (state) {
            case MAINMENU:
                updateMain(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case PAUSE:
                updatePause(delta);
                break;
            case MENU:
                updateMenu(delta);
                break;
            case TUTORIAL:
                updateTutorial(delta);
                break;
            case OPTIONS:
                updateOptions(delta);
                break;
            case GAMEOVER:

                if (!gameover) {
                    ghost.die();
                    gameover = true;
                }

                updateGameOver(delta);
                break;
            default:
                break;
        }
    }

    private void updatePause(float delta) {


        sun.update(delta);
    }

    private void updateTutorial(float delta) {

        tutorialScreen.update(delta);
        mountain.update(delta);
        mountain2.update(delta);
        ghost.update(delta);
        sun.update(delta);
    }

    private void updateOptions(float delta) {
        if (ghost.getIsAlive()) {
            ghost.update(delta);
            mountain.update(delta);
            mountain2.update(delta);
        } else {
            ghost.updateDead(delta);
        }
        options.update(delta);
        sun.update(delta);

    }

    private void updateMenu(float delta) {

        if (ghost.getIsAlive()) {
            ghost.update(delta);
            mountain.update(delta);
            mountain2.update(delta);
        } else {
            ghost.updateDead(delta);
        }
        menu.update(delta);
        sun.update(delta);
    }

    private void updateMain(float delta) {

        mainMenu.update(delta);
        mountain.update(delta);
        mountain2.update(delta);
        ghost.update(delta);
        sun.update(delta);


    }

    private void updateRunning(float delta) {

        distance += 1;
        mountain.update(delta);
        mountain2.update(delta);
        sun.update(delta);
        collision();
        ghost.update(delta);
        spike.update(delta);
        scoreHitbox.update();
        mob.update(delta);
        if(distance > 1000) jumpingMob.update(delta);

    }

    private void updateGameOver(float delta) {

        if (!scoreSaved) {
            if (score > Gdx.app.getPreferences("SG_prefs").getInteger("highscore")) {
                Gdx.app.getPreferences("SG_prefs").putInteger("highscore", score);
                scoreSaved = true;
                Gdx.app.getPreferences("SG_prefs").flush();
            }
        }
        if (!dataSaved) {

            if(AssetLoader.selectedTexture == 4){
                Gdx.app.getPreferences("SG_prefs").putInteger("exp", (int) (Gdx.app.getPreferences("SG_prefs").getInteger("exp") + (exp + (score * 10)) * 1.5));
            } else {
                AssetLoader.prefs.putInteger("exp", Gdx.app.getPreferences("SG_prefs").getInteger("exp") + exp + (score * 10));
            }
            //level formula sqrt exp * 0.1
            lvl = (int) (0.1 * Math.sqrt(Gdx.app.getPreferences("SG_prefs").getInteger("exp")));
            if (lvl > Gdx.app.getPreferences("SG_prefs").getInteger("lvl")) {
                Gdx.app.getPreferences("SG_prefs").putInteger("lvl", lvl);
                lvlUp = true;
            }
            //formula for exp to level
            //exp-this / next - this
            thislvlexp = (float) Math.pow(lvl, 2) * 100;
            nextlvlexp = (float) Math.pow(lvl + 1, 2) * 100;
            exptolvl = (Gdx.app.getPreferences("SG_prefs").getInteger("exp") - thislvlexp) / (nextlvlexp - thislvlexp);
            Gdx.app.getPreferences("SG_prefs").putInteger("spookedmobs", spookedMobs);

            if(!Gdx.app.getPreferences("SG_prefs").getBoolean("pirateUnlocked"))unlockPirate();
            if(!Gdx.app.getPreferences("SG_prefs").getBoolean("ninjaUnlocked"))unlockNinja();
            if(!Gdx.app.getPreferences("SG_prefs").getBoolean("kingUnlocked"))unlockKing();

            Gdx.app.getPreferences("SG_prefs").flush();
            dataSaved = true;
        }
        deathScreen.update(delta);
        ghost.updateDead(delta);
        sun.update(delta);
    }

    private void unlockPirate() {
        if(Gdx.app.getPreferences("SG_prefs").getInteger("lvl") >= 5){
            Gdx.app.getPreferences("SG_prefs").putBoolean("pirateUnlocked", true);
            pirateUnlocked = true;
        }

    }

    private void unlockNinja(){
        if(Gdx.app.getPreferences("SG_prefs").getInteger("spookedmobs") >= 100){
            Gdx.app.getPreferences("SG_prefs").putBoolean("ninjaUnlocked", true);
            ninjaUnlocked = true;
        }

    }

    private void unlockKing(){
        if(Gdx.app.getPreferences("SG_prefs").getInteger("spookedmobs") >= 500
                && Gdx.app.getPreferences("SG_prefs").getInteger("lvl") > 10){
            Gdx.app.getPreferences("SG_prefs").putBoolean("kingUnlocked", true);
            kingUnlocked = true;
        }

    }



    public void playDeadSound() {
        AssetLoader.dead.play();
    }

    public void reset() {
        score = 0;
        exp = 0;
        distance = 0;
        deathsoundPlayed = false;
        gameover = false;
        scoreSaved = false;
        dataSaved = false;
        lvlUp = false;
        ghost.reset(85, 560);
        spike.reset(1080 + 85, 0);
        scoreHitbox.reset();
        mob.reset(-100, 552);
        jumpingMob.reset(1200, 552);
        deathScreen.reset();
        menu.reset();
        options.reset();
        AssetLoader.font2.getData().setScale(1, 1);
        state = GameState.RUNNING;
        pirateUnlocked = false;
        ninjaUnlocked = false;
        kingUnlocked = false;
    }

    public void collision() {

        //Spike and ghost
        if (Intersector.overlaps(ghost.getHitbox(), spike.getHitbox())) {
            //just for testing
            if (!deathsoundPlayed) {
                if (!AssetLoader.soundMuted) playDeadSound();
                deathsoundPlayed = true;
            }
            state = GameState.GAMEOVER;
            //Ghost and score hitbox
        }
        if (Intersector.overlaps(ghost.getHitbox(), (scoreHitbox.getHitbox()))) {
            if (!spike.getScored()) {
                if (!AssetLoader.soundMuted) AssetLoader.score.play();
                score++;
                spike.setScored(true);
            }
        }
        //ghost and mob
        if (Intersector.overlaps(ghost.getHitbox(), mob.getHitbox())) {

            if (ghost.getIsSpooking()) {
                mob.die();
            } else if (mob.getIsAlive()) {
                state = GameState.GAMEOVER;
                if (!deathsoundPlayed) {
                    if (!AssetLoader.soundMuted) playDeadSound();
                    deathsoundPlayed = true;
                }
            }
        }
        //ghost and jumping mob
        if (Intersector.overlaps(ghost.getHitbox(), jumpingMob.getHitbox())) {

            if (ghost.getIsSpooking()) {
                jumpingMob.die();
            } else if (jumpingMob.getIsAlive()) {
                state = GameState.GAMEOVER;
                if (!deathsoundPlayed) {
                    if (!AssetLoader.soundMuted) playDeadSound();
                    deathsoundPlayed = true;
                }
            }
        }
    }

    public Ghost getGhost() {
        return ghost;
    }

    public Mob getMob() {
        return mob;
    }

    public JumpingMob getJumpingMob(){
        return jumpingMob;
    }

    public Spike getSpike() {
        return spike;
    }

    public ScoreHitbox getScoreHitbox() {
        return scoreHitbox;
    }

    public ScrollingBg getMountain() {
        return mountain;
    }

    public ScrollingBg getMountain2() {
        return mountain2;
    }

    public Sun getSun() {
        return sun;
    }

    public DeathScreen getDeathScreen() {
        return deathScreen;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public Menu getMenu() {
        return menu;
    }

    public Options getOptions() {
        return options;
    }

    public TutorialScreen getTutorialScreen() {
        return tutorialScreen;
    }

    public int getScore() {
        return score;
    }

    public boolean getScoreSaved() {
        return scoreSaved;
    }

    public int getExp() {
        return AssetLoader.prefs.getInteger("exp");
    }

    public int getLvl() {
        return lvl;
    }

    public boolean getLvlUp() {
        return lvlUp;
    }

    public boolean getPirateUnl(){
        return pirateUnlocked;
    }

    public boolean getNinjaUnl(){
        return ninjaUnlocked;
    }

    public boolean getKingUnl(){
        return kingUnlocked;
    }

    public float getExptolvl() {
        //(next-this) / (exp-this)
        return exptolvl;
    }

    public boolean getDev() {
        return dev;
    }

    public void setDev() {

        if (dev) {
            dev = false;
        } else {
            dev = true;
        }

    }

    public int getDistance() {
        return distance;
    }

    public Enum getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void setMobKilled() {
        spookedMobs++;
    }

    public int getSpookedMobs() {
        return spookedMobs;
    }

    public void setExp() {
        exp += 10;
    }
}
