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
    private GameWorld gameWorld;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font = new BitmapFont();
    private Mob mob;
    private boolean dev = false;
    private String score;



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
        drawGround();
        drawGhost(runTime);

        //Mob
        /*if (gameWorld.getMobSpawned() == true) {
            drawMobShadow(delta);
            batch.disableBlending();
            drawMob();
        }*/

        drawScore();

        batch.end();

        //TEST CODE HERE
        if (dev == true) drawDev();


        //draw font
        //for testing and shit lol

    }

    private void drawScore() {

        //score = gameWorld.getScore() + "";
        batch.enableBlending();
        //AssetLoader.font.draw(batch, "" + gameWorld.getScore(), 540, 1720);
        //String score = gameWorld.getScore() + "";
        AssetLoader.font.draw(batch, "" + gameWorld.getScore(), 495, 1620);


    }

    private void drawGround() {
        batch.disableBlending();
        batch.draw(AssetLoader.ground, 0, 0);
    }

    private void drawDev() {
        //spike hitbox
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rect(gameWorld.getSpike().getHitbox().x,
                gameWorld.getSpike().getHitbox().y,
                gameWorld.getSpike().getHitbox().getWidth(),
                gameWorld.getSpike().getHitbox().getHeight());
        //score hitbox
        shapeRenderer.setColor(0, 1, 0, 1);
        shapeRenderer.rect(gameWorld.getSpike().getScoreHitbox().x,
                gameWorld.getSpike().getScoreHitbox().y,
                gameWorld.getSpike().getScoreHitbox().getWidth(),
                gameWorld.getSpike().getScoreHitbox().getHeight()
        );
        shapeRenderer.end();

        batch.enableBlending();
        batch.begin();
        font.draw(batch, "Score: " + String.valueOf(gameWorld.getScore()), 0, 1910);
        font.draw(batch, "X: " + String.valueOf(gameWorld.getGhost().getX()), 0, 1870);
        font.draw(batch, "Y: " + String.valueOf(gameWorld.getGhost().getY()), 0, 1830);
        font.draw(batch, "Spooking: " + String.valueOf(gameWorld.getGhost().getIsSpooking()), 0, 1790);
        batch.end();
    }

    private void drawSpike(float delta) {
        //spike1
        batch.draw(AssetLoader.longSpike, gameWorld.getSpike().getPositionX(),
                gameWorld.getSpike().getPositionY());
        //spike2
        // batch.draw(AssetLoader.longSpike, gameWorld.getSpike2().getPositionX(),
        // gameWorld.getSpike2().getPositionY());
    }

    private void drawMobShadow(float delta) {
        batch.setColor(1.0f, 1.0f, 1.0f, 0.5f * (560 / gameWorld.getMob().getY()));
        batch.draw(AssetLoader.shadow, gameWorld.getMob().getX(), 527);
    }

    private void drawMob() {
        //draw mob
        batch.draw(AssetLoader.mob1, mob.getX(), mob.getY());
    }

    private void drawGhost(float runTime) {

        //Draw shadow
        batch.enableBlending();

        if (gameWorld.getGhost().getY() > 500){
            batch.setColor(1f, 1f, 1f, 0.5f * (560 / gameWorld.getGhost().getY()));
            batch.draw(AssetLoader.shadow, gameWorld.getGhost().getX(), 527);
        }

        //Ghost death effect, if dead paint red else white
        if(!gameWorld.getGhost().getIsAlive()){
            batch.setColor(1f, 0.5f, 0.5f, 0.5f);
            batch.draw(AssetLoader.ghostDead, gameWorld.getGhost().getX(), gameWorld.getGhost().getY());
        }else{
            if(!gameWorld.getGhost().getIsSpooking()){
                batch.setColor(1.0f, 1.0f, 1.0f, 0.8f);
                batch.draw(AssetLoader.ghostAnimation.getKeyFrame(runTime),
                        gameWorld.getGhost().getX(), gameWorld.getGhost().getY());
            } else {
                batch.setColor(1.0f, 1.0f, 1.0f, 0.8f * (85 / gameWorld.getGhost().getX()));
                batch.draw(AssetLoader.ghostSpooking,
                        gameWorld.getGhost().getX(), gameWorld.getGhost().getY());

            }

        }



        batch.setColor(1.0f, 1.0f, 1.0f, 1f);
        batch.disableBlending();
    }

    private void drawGhostShadow() {
        batch.enableBlending();
        batch.setColor(1.0f, 1.0f, 1.0f, 0.5f * (560 / gameWorld.getGhost().getY()));
        batch.draw(AssetLoader.shadow, gameWorld.getGhost().getX(), 527);
    }

    public void drawBg() {

        batch.draw(AssetLoader.background, 0, 0);
    }

    public void drawBackMountain(float delta) {

        batch.draw(AssetLoader.backMountain, gameWorld.getMountain().getPositionX(), 552);
        batch.draw(AssetLoader.backMountain,
                gameWorld.getMountain().getPositionX() + AssetLoader.backMountain.getRegionWidth() - 1, 552);
        /*bmPos -= 5 * delta;
        if (bmPos < -1080) {
            bmPos = -1;
        }*/
    }

    public void drawFrontMountain(float delta) {

        batch.draw(AssetLoader.frontMountain, gameWorld.getMountain2().getPositionX(), 552);
        batch.draw(AssetLoader.frontMountain,
                gameWorld.getMountain2().getPositionX() + AssetLoader.frontMountain.getRegionWidth() - 1, 552);
        /*fmPos -= 8 * delta;
        if (fmPos < -1080) {
            fmPos = -1;
        }*/
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



