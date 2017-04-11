package com.ikisoft.ghostgame;

import com.badlogic.gdx.math.Intersector;
import com.ikisoft.ghostgame.GameObjects.Ghost;
import com.ikisoft.ghostgame.GameObjects.Mob;
import com.ikisoft.ghostgame.GameObjects.ScrollingBg;
import com.ikisoft.ghostgame.GameObjects.Spike;

/**
 * Created by Max on 2.4.2017.
 */

public class GameWorld {


    private Ghost ghost;
    private Mob mob;
    private Spike spike, spike2;
    private ScrollingBg mountain, mountain2;
    private int score;
    private boolean deathsoundPlayed, mobSpawned, scored, gameover;

    private GameState state;


    public enum GameState {
        RUNNING, GAMEOVER;
    }


    public GameWorld() {

        state = GameState.RUNNING;
        ghost = new Ghost(85, 560);
        spike = new Spike(1080 + 85, 100);
        mountain = new ScrollingBg(5);
        mountain2 = new ScrollingBg(8);
        mob = new Mob(-100, 552, this);
        //spike2 = new Spike(2160+85, 0);
        score = 0;
        deathsoundPlayed = false;
        scored = false;
        gameover = false;
    }

    public void update(float delta) {

        switch (state) {
            case RUNNING:
                updateRunning(delta);
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

    private void updateRunning(float delta) {

        mountain.update(delta);
        mountain2.update(delta);
        collision();
        ghost.update(delta);
        spike.update(delta);
        mob.update(delta);

        if (spike.getPositionX() < 0) {
            deathsoundPlayed = false;
            scored = false;
        }

    }

    private void updateGameOver(float delta) {

        ghost.updateDead(delta);


    }

    public void playDeadSound() {
        AssetLoader.dead.play();

    }

    public void reset() {
        score = 0;
        scored = false;
        deathsoundPlayed = false;
        gameover = false;
        ghost.reset(85, 560);
        spike.reset(1080 + 85, 100);
        mob.reset(-100, 552);

        state = GameState.RUNNING;

    }

    public void spawnMob() {
        mobSpawned = true;

    }

    public void collision() {

        if (Intersector.overlaps(ghost.getHitbox(), spike.getHitbox())) {
            //just for testing
            if (!deathsoundPlayed) {
                playDeadSound();
                deathsoundPlayed = true;
            }
            state = GameState.GAMEOVER;
            score = 0;

        } else if (Intersector.overlaps(ghost.getHitbox(), spike.getScoreHitbox())) {
            if (!scored) {
                score++;
                scored = true;
                AssetLoader.score.play();

            }
        }

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

    public ScrollingBg getMountain() {
        return mountain;
    }

    public ScrollingBg getMountain2() {
        return mountain2;
    }

    public int getScore() {
        return score;
    }

    public boolean getMobSpawned() {
        return mobSpawned;
    }


}
