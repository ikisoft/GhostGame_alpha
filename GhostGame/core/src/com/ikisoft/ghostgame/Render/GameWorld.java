package com.ikisoft.ghostgame.Render;

import com.badlogic.gdx.math.Intersector;
import com.ikisoft.ghostgame.GameObjects.Ghost;
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
    private Spike spike;
    private ScoreHitbox scoreHitbox;
    private ScrollingBg mountain, mountain2;
    private Sun sun;
    private DeathScreen deathScreen;
    private MainMenu mainMenu;
    private Menu menu;
    private Options options;
    private TutorialScreen tutorialScreen;
    private int score;
    private boolean deathsoundPlayed, gameover, saved;
    private float distance;

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
        score = 0;
        deathsoundPlayed = false;
        gameover = false;
        distance = 0;
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

    private void updateTutorial(float delta) {

        tutorialScreen.update(delta);
        mountain.update(delta);
        mountain2.update(delta);
        ghost.update(delta);
        sun.update(delta);
    }

    private void updateOptions(float delta) {
        if(ghost.getIsAlive()){
            ghost.update(delta);
            mountain.update(delta);
            mountain2.update(delta);
        }else{
            ghost.updateDead(delta);
        }
        options.update(delta);
        sun.update(delta);

    }

    private void updateMenu(float delta) {

        if(ghost.getIsAlive()){
            ghost.update(delta);
            mountain.update(delta);
            mountain2.update(delta);
        }else{
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

        distance += 0.5;
        mountain.update(delta);
        mountain2.update(delta);
        sun.update(delta);
        collision();
        ghost.update(delta);
        spike.update(delta);
        scoreHitbox.update();
        mob.update(delta);

    }

    private void updateGameOver(float delta) {

        if(!saved){

            if(score > AssetLoader.prefs.getInteger("highscore")){
                AssetLoader.prefs.putInteger("highscore", score);
                saved = true;
                AssetLoader.prefs.flush();
            }

        }
        deathScreen.update(delta);
        ghost.updateDead(delta);
        sun.update(delta);


    }

    public void playDeadSound() {
        com.ikisoft.ghostgame.Helpers.AssetLoader.dead.play();

    }

    public void reset() {
        score = 0;
        distance = 0;
        deathsoundPlayed = false;
        gameover = false;
        saved = false;
        ghost.reset(85, 560);
        spike.reset(1080 + 85, 0);
        scoreHitbox.reset();
        mob.reset(-100, 552);
        deathScreen.reset();
        menu.reset();
        options.reset();
        AssetLoader.font.setColor(1f, 1f, 1f, 1);


        state = GameState.RUNNING;

    }


    public void collision() {

        //Spike and ghost
        if (Intersector.overlaps(ghost.getHitbox(), spike.getHitbox())) {
            //just for testing
            if (!deathsoundPlayed) {
                playDeadSound();
                deathsoundPlayed = true;
            }
            state = GameState.GAMEOVER;

        //Ghost and score hitbox
        }
        if (Intersector.overlaps(ghost.getHitbox(), (scoreHitbox.getHitbox()))) {
            if (!spike.getScored()) {
                com.ikisoft.ghostgame.Helpers.AssetLoader.score.play();
                score++;
                spike.setScored(true);
            }
        }
        //ghost and mob
        if (Intersector.overlaps(ghost.getHitbox(), mob.getHitbox())) {

            if (ghost.getIsSpooking()) {
                    mob.die();
            }else if(mob.getIsAlive()){
                state = GameState.GAMEOVER;
                if (!deathsoundPlayed) {
                    playDeadSound();
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

    public Sun getSun(){
        return sun;
    }

    public DeathScreen getDeathScreen(){
        return deathScreen;
    }

    public MainMenu getMainMenu(){
        return mainMenu;
    }

    public Menu getMenu(){
        return menu;
    }

    public Options getOptions(){
        return options;
    }

    public TutorialScreen getTutorialScreen(){
        return tutorialScreen;
    }

    public int getScore() {
        return score;
    }

    public float getDistance(){
        return distance;
    }

    public Enum getState(){
        return state;
    }

    public void setState(GameState state){
        this.state = state;

    }
}
