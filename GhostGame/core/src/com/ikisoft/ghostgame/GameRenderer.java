package com.ikisoft.ghostgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.ikisoft.ghostgame.GameObjects.Ghost;

/**
 * Created by Max on 2.4.2017.
 */

public class GameRenderer {

    private static final float VIRTUAL_WIDTH = 1080;
    private static final float VIRTUAL_HEIGHT = 1920;
    private static OrthographicCamera cam;
    private static float bmPos = 0;
    private static float fmPos = 0;
    private static FPSLogger drawFPS;
    private float runTime;
    private static SpriteBatch batch;
    private static GameWorld gameWorld;


    public GameRenderer() {

        gameWorld = new GameWorld();
        runTime = 0;
        cam = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(cam.combined);
        drawFPS = new FPSLogger();

    }

    public void render(float delta) {

        drawFPS.log();
        cam.update();
        runTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.disableBlending();
        drawBg();
        batch.enableBlending();
        drawBackMountain(delta);
        drawFrontMountain(delta);
        batch.draw(AssetLoader.shadow, 85, 527);
        batch.draw(AssetLoader.ghostAnimation.getKeyFrame(runTime),
                gameWorld.getGhostRect().x, gameWorld.getGhostRect().y);
        batch.end();

    }

    public void drawBg() {

        batch.draw(AssetLoader.background, 0, 0);
    }

    public void drawBackMountain(float delta) {

        batch.draw(AssetLoader.backMountain, bmPos, 552);
        batch.draw(AssetLoader.backMountain, bmPos + AssetLoader.backMountain.getRegionWidth() - 1, 552);
        bmPos -= 2 * delta;
        if (bmPos <= -1080) {
            bmPos = 0;
        }


    }

    public void drawFrontMountain(float delta) {

        batch.draw(AssetLoader.frontMountain, fmPos, 552);
        batch.draw(AssetLoader.frontMountain, fmPos + AssetLoader.frontMountain.getRegionWidth() - 1, 552);
        fmPos -= 5 * delta;
        if (fmPos <= -1080) {
            fmPos = 0;
        }
    }

    public void resize(int width, int height) {

        cam.viewportWidth = 1080f;
        cam.viewportHeight = 1920f;
        cam.update();
    }

    public void dispose() {
        batch.dispose();


    }


}



