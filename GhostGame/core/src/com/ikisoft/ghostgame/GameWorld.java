package com.ikisoft.ghostgame;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.ikisoft.ghostgame.GameObjects.Ghost;
import com.ikisoft.ghostgame.GameObjects.NormalMob;

/**
 * Created by Max on 2.4.2017.
 */

public class GameWorld {


    public Ghost ghost;
    public NormalMob normalMob;
    private int score;

    private GameState state;

    public enum GameState {
        RUNNING, GAMEOVER;
    }


    public GameWorld() {

        state = GameState.RUNNING;
        ghost = new Ghost(85, 560);
        normalMob = new NormalMob(1080, 552);
        score = 0;

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
        normalMob.update(delta);

        if (Intersector.overlaps(ghost.getHitbox(), normalMob.getHitbox())
                && ghost.getIsSpooking() == true) {
            score++;
            normalMob.die();
            normalMob.restart(1080, 552);
        } else if (Intersector.overlaps(ghost.getHitbox(), normalMob.getHitbox())
                && normalMob.getIsAlive() == true) {
            //state = GameState.GAMEOVER;
            score = 0;

        }

    }

    private void updateGameOver(float delta) {

        ghost.die();
    }

    public void restart(){
        ghost.restart(85, 560);
        normalMob.restart(1080, 552);
        state = GameState.RUNNING;

    }

    public Ghost getGhost() {

        return ghost;
    }

    public NormalMob getNormalMob() {

        return normalMob;
    }

    public int getScore() {

        return score;
    }

}
