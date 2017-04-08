package com.ikisoft.ghostgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.ikisoft.ghostgame.GameObjects.Ghost;

/**
 * Created by Max on 2.4.2017.
 */

public class InputHandler implements InputProcessor {

    private Ghost ghost;
    private boolean dragged;
    private boolean touchedDown;

        public InputHandler(Ghost ghost){

            this.ghost = ghost;
            dragged = false;
            touchedDown = false;

        }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

            touchedDown = true;
            if(!dragged && touchedDown){
                Gdx.app.log("TouchDown: ", "Touched");

                ghost.onClick();


            }


            return false;



    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if(!dragged){
            Gdx.app.log("TouchDown: ", "Touched");

            ghost.onClick();

        }
        dragged = false;


        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        dragged = true;
        System.out.println("Dragged");

        return true;

    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
