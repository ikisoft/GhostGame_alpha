package com.ikisoft.ghostgame.Helpers;

import com.badlogic.gdx.InputProcessor;
import com.ikisoft.ghostgame.Render.GameWorld;

/**
 * Created by Max on 13.4.2017.
 */

public class InputHandler implements InputProcessor {

    private GameWorld gameWorld;
    private float scaleX;
    private float scaleY;
    private boolean tutorialShown;

    public InputHandler(GameWorld gameWorld, float scaleX, float scaleY) {

        this.gameWorld = gameWorld;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        tutorialShown = false;

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
    public boolean touchDown(int x, int y, int pointer, int button) {
        x = (int) scaleX(x);
        y = (int) scaleY(y);

        System.out.println("x: " + x);
        System.out.println("y: " + y);

        if (gameWorld.getState() != GameWorld.GameState.RUNNING) {
            AssetLoader.menuclick1.play();
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

        System.out.println("x: " + x);
        System.out.println("y: " + y);

        //gamestate MAINMENU
        if (gameWorld.getState() == GameWorld.GameState.MAINMENU) {

            //PLAY
            if (x >= 200 && x <= 940 && y >= 750 && y <= 880) {
                if(!tutorialShown){
                    gameWorld.setState(GameWorld.GameState.TUTORIAL);
                    tutorialShown = true;
                } else {
                    AssetLoader.menuclick2.play();
                    gameWorld.reset();
                }

                //GO TO MENU
            } else if (x >= 200 && x <= 940 && y >= 880 && y <= 1050) {
                AssetLoader.menuclick2.play();
                gameWorld.setState(GameWorld.GameState.MENU);
                //GO TO OPTIONS
            } else if (x >= 200 && x <= 940 && y >= 1050 && y <= 1220) {
                AssetLoader.menuclick2.play();
                gameWorld.setState(GameWorld.GameState.OPTIONS);

            }
            return true;

            //gamestate GAMEOVER
        } else if (gameWorld.getState() == GameWorld.GameState.GAMEOVER) {
            //PLAY
            if (x >= 208 && x <= 456 && y >= 1230 && y <= 1420) {
                AssetLoader.menuclick2.play();
                gameWorld.reset();
                //GO TO MENU
            } else if (x >= 456 && x <= 736 && y >= 1230 && y <= 1420) {
                AssetLoader.menuclick2.play();
                gameWorld.setState(GameWorld.GameState.MENU);
                //GO TO OPTIONS
            } else if (x >= 736 && x <= 955 && y >= 1230 && y <= 1420) {
                AssetLoader.menuclick2.play();
                gameWorld.setState(GameWorld.GameState.OPTIONS);
            }
            return true;

            //gamestate MENU
        } else if (gameWorld.getState() == GameWorld.GameState.MENU) {
            //PLAY
            if (x >= 200 && x <= 580 && y >= 1230 && y <= 1520) {
                AssetLoader.menuclick2.play();
                gameWorld.reset();
                //GO TO OPTIONS
            } else if (x >= 580 && x <= 940 && y >= 1230 && y <= 1520) {
                AssetLoader.menuclick2.play();
                gameWorld.setState(GameWorld.GameState.OPTIONS);
                gameWorld.getMenu().reset();
            }
            return true;

            //gamestate OPTIONS
        } else if (gameWorld.getState() == GameWorld.GameState.OPTIONS) {
            //PLAY
            if (x >= 200 && x <= 580 && y >= 1010 && y <= 1220) {
                AssetLoader.menuclick2.play();
                gameWorld.reset();
                //GO TO MENU
            } else if (x >= 580 && x <= 940 && y >= 1010 && y <= 1220) {
                AssetLoader.menuclick2.play();
                gameWorld.setState(GameWorld.GameState.MENU);
                gameWorld.getOptions().reset();
            }
            return true;

            //gamestate TUTORIAL
        } else if (gameWorld.getState() == GameWorld.GameState.TUTORIAL) {
            //PLAY
            if (x >= 426 && x <= 770 && y >= 1325 && y <= 1520) {
                AssetLoader.menuclick2.play();
                gameWorld.reset();
            }

            return true;

        }
        return false;
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
