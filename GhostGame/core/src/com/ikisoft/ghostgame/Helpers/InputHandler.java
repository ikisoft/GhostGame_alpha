package com.ikisoft.ghostgame.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.ikisoft.ghostgame.GameObjects.Ghost;
import com.ikisoft.ghostgame.Render.GameWorld;

/**
 * Created by Max on 13.4.2017.
 */

public class InputHandler implements InputProcessor {

    private GameWorld gameWorld;
    private float scaleX;
    private float scaleY;
    private boolean tutorialShown;
    private Button uiButton, mainMenuButton, skinIconButton;

    public InputHandler(GameWorld gameWorld, float scaleX, float scaleY) {

        this.gameWorld = gameWorld;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        tutorialShown = false;
        uiButton = new Button(186, 186);
        skinIconButton = new Button(132, 132);
        mainMenuButton = new Button(747, 166);


    }


    @Override
    public boolean keyDown(int keycode) {


        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            gameWorld.getGhost().onClick();
            return true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            gameWorld.getGhost().onFling();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.E)
                && Gdx.input.isKeyPressed(Input.Keys.V)) {
            gameWorld.setDev();

        }


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
    public boolean touchDown(int x, int y, int pointer, int button) {
/*
        x = (int) scaleX(x);
        y = (int) scaleY(y);
*/
/*
        System.out.println("x: " + x);
        System.out.println("y: " + y);*/

        if (gameWorld.getState() != GameWorld.GameState.RUNNING) {
            if (!DataHandler.soundMuted) AssetLoader.menuclick1.play();
            return true;
        }
        return false;
    }


    //This code is so bad and im retarded. Forgive me.
    //Use ENUM?
    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {

        x = (int) scaleX(x);
        y = (int) scaleY(y);

 /*       System.out.println("x: " + x);
        System.out.println("y: " + y);*/

        if (gameWorld.getState() == GameWorld.GameState.MAINMENU) {
            mainMenu(x, y);
        } else if (gameWorld.getState() == GameWorld.GameState.MENU) {
            menu(x, y);
        } else if (gameWorld.getState() == GameWorld.GameState.OPTIONS) {
            options(x, y);
        } else if (gameWorld.getState() == GameWorld.GameState.GAMEOVER) {
            gameover(x, y);
        } else if (gameWorld.getState() == GameWorld.GameState.TUTORIAL) {
            tutorial(x, y);
        } else if (gameWorld.getState() == GameWorld.GameState.PAUSE) {
            pause(x, y);
        }

        return false;
    }

    private void pause(int x, int y) {

        if (uiButton.isDown(x, y, 580, 1570)) {
            if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();
            gameWorld.setState(GameWorld.GameState.RUNNING);
        }

    }

    private void mainMenu(int x, int y) {
        //PLAY
        if (mainMenuButton.isDown(x, y, 556, 802)) {
            if (!tutorialShown) {
                if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();
                gameWorld.setState(GameWorld.GameState.TUTORIAL);
                tutorialShown = true;
            } else {
                if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();
                gameWorld.reset();
            }

        } else if (mainMenuButton.isDown(x, y, 556, 976)) {
            if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();
            gameWorld.setState(GameWorld.GameState.MENU);

        } else if (mainMenuButton.isDown(x, y, 556, 1148)) {
            if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();
            gameWorld.setState(GameWorld.GameState.OPTIONS);

        }
    }

    private void menu(int x, int y) {
        //200, 1230
        if (uiButton.isDown(x, y, 406, 1428)) {
            if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();

            gameWorld.reset();

        } else if (uiButton.isDown(x, y, 752, 1428)) {
            if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();

            gameWorld.setState(GameWorld.GameState.OPTIONS);
            gameWorld.getMenu().reset();

        }

        //ghost
        if (skinIconButton.isDown(x, y, 344, 904)) {

            DataHandler.selectedCharacter = 0;
            DataHandler.selectedCharacterPreview = 0;
            gameWorld.getGhost().setGhostState(Ghost.GhostState.GHOST);
            if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();

            //pirate
        } else if (skinIconButton.isDown(x, y, 498, 904)) {

            if (DataHandler.pirateUnlocked) {
                DataHandler.selectedCharacter = 1;
                DataHandler.selectedCharacterPreview = 1;
                gameWorld.getGhost().setGhostState(Ghost.GhostState.PIRATE);
                if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();

            } else {
                DataHandler.selectedCharacterPreview = 1;
            }
            //ninja
        } else if (skinIconButton.isDown(x, y, 654, 904)) {

            if (DataHandler.ninjaUnlocked) {
                DataHandler.selectedCharacter = 2;
                DataHandler.selectedCharacterPreview = 2;

                gameWorld.getGhost().setGhostState(Ghost.GhostState.NINJA);
                if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();

            } else {
                DataHandler.selectedCharacterPreview = 2;
            }
            //king
        } else if (skinIconButton.isDown(x, y, 810, 904)) {

            if (DataHandler.kingUnlocked) {
                DataHandler.selectedCharacter = 3;
                DataHandler.selectedCharacterPreview = 3;

                gameWorld.getGhost().setGhostState(Ghost.GhostState.KING);
                if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();

            } else {
                DataHandler.selectedCharacterPreview = 3;
            }
        }
    }

    private void gameover(int x, int y) {

        if (uiButton.isDown(x, y, 316, 1328)) {
            if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();
            gameWorld.reset();
            //GO TO MENU

        } else if (uiButton.isDown(x, y, 606, 1328)) {
            if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();
            gameWorld.setState(GameWorld.GameState.MENU);
            //GO TO OPTIONS
        } else if (uiButton.isDown(x, y, 850, 1328)) {
            if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();
            gameWorld.setState(GameWorld.GameState.OPTIONS);
        }
    }

    private void options(int x, int y) {

        if (uiButton.isDown(x, y, 402, 1138)) {
            if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();
            gameWorld.reset();

        } else if (uiButton.isDown(x, y, 734, 1138)) {
            if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();
            gameWorld.setState(GameWorld.GameState.MENU);
            gameWorld.getOptions().reset();

        } else if (uiButton.isDown(x, y, 402, 900)) {
            DataHandler.muteSound();
            if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();

        } else if (uiButton.isDown(x, y, 734, 900)) {
            DataHandler.muteMusic();
            if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();

        }
    }

    private void tutorial(int x, int y) {

        if (uiButton.isDown(x, y, 586, 1430)) {
            if (!DataHandler.soundMuted) AssetLoader.menuclick2.play();
            gameWorld.reset();
        }
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private float scaleX(float screenX) {
        return (screenX / scaleX) * 1080;
    }

    private float scaleY(float screenY) {
        return (screenY / scaleY) * 1920;
    }
}
