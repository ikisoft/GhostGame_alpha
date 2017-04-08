package com.ikisoft.ghostgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ikisoft.ghostgame.GameObjects.Mob;

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
    private  GameWorld gameWorld;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font = new BitmapFont();
    private Mob mob;




    public GameRenderer(GameWorld world) {

        gameWorld = world;
        mob = gameWorld.getMob();
        runTime = 0;
        cam = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(cam.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
        font.getData().setScale(2, 2);


    }

    public void render(float delta) {


        cam.update();
        runTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //begin drawing
        batch.begin();
        batch.disableBlending();
        drawBg();
        batch.enableBlending();
        drawBackMountain(delta);
        drawFrontMountain(delta);
        drawSpike(delta);
        drawGhostShadow(delta);
        drawMobShadow(delta);
        drawGhost(runTime);
        batch.disableBlending();
        drawMob();
        batch.end();

        //TEST CODE HERE
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rect(gameWorld.getSpike().getHitbox().x,
                gameWorld.getSpike().getHitbox().y,
                gameWorld.getSpike().getHitbox().getWidth(),
                gameWorld.getSpike().getHitbox().getHeight());
        shapeRenderer.end();


        //draw font
        //for testing and shit lol
        batch.enableBlending();
        batch.begin();
        font.draw(batch, "Score: " + String.valueOf(gameWorld.getScore()), 0, 1910);
        font.draw(batch, "X: " + String.valueOf(gameWorld.getGhost().getX()), 0, 1870);
        font.draw(batch, "Y: " + String.valueOf(gameWorld.getGhost().getY()), 0, 1830);
        font.draw(batch, "Spooking: " + String.valueOf(gameWorld.getGhost().getIsSpooking()), 0, 1790);
        batch.end();
    }

    private void drawSpike(float delta) {
        batch.draw(AssetLoader.spike, gameWorld.getSpike().getPositionX(), 527);
    }

    private void drawMobShadow(float delta) {
        batch.setColor(1.0f, 1.0f, 1.0f, 0.5f * (560 / gameWorld.getMob().getY()));
        batch.draw(AssetLoader.shadow, gameWorld.getMob().getX(), 527);
    }

    private void drawMob() {
        //draw mob
        batch.setColor(1.0f, 1.0f, 1.0f, 1f);
        batch.draw(AssetLoader.mob1, mob.getX(), mob.getY());
    }

    private void drawGhost(float runTime) {

        batch.setColor(
                1.0f,
                1.0f * (85 / gameWorld.getGhost().getX()),
                1.0f * (85 / gameWorld.getGhost().getX()),
                0.8f * (85 / gameWorld.getGhost().getX())
        );
        batch.draw(AssetLoader.ghostAnimation.getKeyFrame(runTime),
                gameWorld.getGhost().getX(), gameWorld.getGhost().getY());
    }

    private void drawGhostShadow(float delta) {
        batch.setColor(1.0f, 1.0f, 1.0f, 0.5f * (560 / gameWorld.getGhost().getY()));
        batch.draw(AssetLoader.shadow, gameWorld.getGhost().getX(), 527);
    }

    public void drawBg() {

        batch.draw(AssetLoader.background, 0, 0);
    }

    public void drawBackMountain(float delta) {

        batch.draw(AssetLoader.backMountain, bmPos, 552);
        batch.draw(AssetLoader.backMountain, bmPos + AssetLoader.backMountain.getRegionWidth() - 1, 552);
        bmPos -= 5 * delta;
        if (bmPos < -1080) {
            bmPos = -1;
        }
    }

    public void drawFrontMountain(float delta) {

        batch.draw(AssetLoader.frontMountain, fmPos, 552);
        batch.draw(AssetLoader.frontMountain, fmPos + AssetLoader.frontMountain.getRegionWidth() -1, 552);
        fmPos -= 8 * delta;
        if (fmPos < -1080) {
            fmPos = -1;
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



