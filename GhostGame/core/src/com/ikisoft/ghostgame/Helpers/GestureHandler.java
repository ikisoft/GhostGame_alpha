package com.ikisoft.ghostgame.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.ikisoft.ghostgame.GameObjects.Ghost;
import com.ikisoft.ghostgame.Render.GameWorld;

/**
 * Created by Max on 2.4.2017.
 */

public class GestureHandler implements GestureDetector.GestureListener {

    private Ghost ghost;
    private GameWorld gameWorld;
    private float scaleX;
    private float scaleY;

    public GestureHandler(Ghost ghost, GameWorld gameWorld, float scaleX, float scaleY) {

        this.ghost = ghost;
        this.gameWorld = gameWorld;
        this.scaleX = scaleX;
        this.scaleY = scaleY;

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        if (gameWorld.getState() == GameWorld.GameState.RUNNING) {
            ghost.onClick();
            return true;
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {


        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            if (velocityX > 0) {
                if (gameWorld.getState() == GameWorld.GameState.RUNNING) {
                    ghost.onFling();
                } else if (gameWorld.getState() == GameWorld.GameState.MENU) {
                    if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();
                    gameWorld.setState(GameWorld.GameState.OPTIONS);
                    gameWorld.getMenu().reset();
                } else if (gameWorld.getState() == GameWorld.GameState.OPTIONS) {
                    if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();
                    gameWorld.setState(GameWorld.GameState.MENU);
                    gameWorld.getOptions().reset();

                }


                return true;
            }
        }
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    private float scaleX(float screenX) {
        return (screenX / scaleX) * 1080;
    }

    private float scaleY(float screenY) {
        return (screenY / scaleY) * 1920;
    }
}
