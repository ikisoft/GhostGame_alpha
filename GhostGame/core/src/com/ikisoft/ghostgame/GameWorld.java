package com.ikisoft.ghostgame;

import com.badlogic.gdx.math.Intersector;
import com.ikisoft.ghostgame.GameObjects.Ghost;
import com.ikisoft.ghostgame.GameObjects.Mob;
import com.ikisoft.ghostgame.GameObjects.Spike;

/**
 * Created by Max on 2.4.2017.
 */

public class GameWorld {


    private Ghost ghost;
    private Mob mob;
    private Spike spike;
    private int score;
    private boolean soundPlayed;

    private GameState state;

    public enum GameState {
        RUNNING, GAMEOVER;
    }


    public GameWorld() {

        state = GameState.RUNNING;
        ghost = new Ghost(85, 560);
        mob = new Mob(1080, 552);
        spike = new Spike(1200);
        score = 0;
        soundPlayed = false;

    }

    public void update(float delta) {


        switch (state) {
            case RUNNING:
                updateRunning(delta);
                break;
            case GAMEOVER:
                restart();
                //updateGameOver(delta);
                break;
            default:
                break;
        }

    }

    private void updateRunning(float delta) {

        ghost.update(delta);
        mob.update(delta);
        spike.update(delta);

        if (Intersector.overlaps(ghost.getHitbox(), mob.getHitbox())
                && ghost.getIsSpooking() == true) {
            mob.die();
            score++;
            //mob.restart(1080, 552);
        } else if (Intersector.overlaps(ghost.getHitbox(), mob.getHitbox())
                || Intersector.overlaps(ghost.getHitbox(), spike.getHitbox())
                && mob.getIsAlive() == true) {

            if(!soundPlayed){
                playDeadSound();
                soundPlayed = true;
            }

            score = 0;
        }
        if (Intersector.overlaps(ghost.getHitbox(), spike.getHitbox())) {

            //just for testing
            if(!soundPlayed){
                playDeadSound();
                soundPlayed = true;
            }
            score = 0;
        }

        //just for testing
        if(spike.getPositionX() < 0) soundPlayed = false;

    }

    private void updateGameOver(float delta) {

        ghost.die();
    }

    public void playDeadSound() {

        AssetLoader.dead.play();

    }

    public void restart() {
        ghost.restart(85, 560);
        mob.restart(1080, 552);
        state = GameState.RUNNING;

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

    public int getScore() {

        return score;
    }


}
