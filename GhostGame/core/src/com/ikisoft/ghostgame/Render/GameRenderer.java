package com.ikisoft.ghostgame.Render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.ikisoft.ghostgame.GameObjects.Mob;
import com.ikisoft.ghostgame.Helpers.AssetLoader;

import sun.security.provider.SHA;

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
    private String score;
    private float colorvar = 0;
    private float expHeight = 552;
    private int length;
    private Vector2 logoPos, logoTarget, scorePos, scoreTarget;


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
        logoPos = new Vector2(130, 2500);
        logoTarget = new Vector2(130, 1820);
        scorePos = new Vector2(495 - (30 * length), 1620);
        scoreTarget = new Vector2(510 - (30 * length), 1150);


    }

    public void render(float delta) {

        cam.update();
        runTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //begin drawing
        batch.begin();
        batch.disableBlending();
        drawBg();
        drawSun();
        drawBackMountain(delta);
        drawFrontMountain(delta);
        drawSpike(delta);
        drawGround();
        drawGhost(runTime);
        //Mob
        batch.disableBlending();
        drawMob();
        //score and exp

        if (gameWorld.getState() != GameWorld.GameState.GAMEOVER) {
            scorePos.x = 495 - (30 * length);
            scorePos.y = 1620;
        }
        if (gameWorld.getState() == GameWorld.GameState.MAINMENU) drawMainMenu();
        if (gameWorld.getState() == GameWorld.GameState.OPTIONS) drawOptions();
        if (gameWorld.getState() == GameWorld.GameState.RUNNING) drawScore();
        if (gameWorld.getState() == GameWorld.GameState.MENU) drawMenu();
        if (gameWorld.getState() == GameWorld.GameState.TUTORIAL) drawTutorial();

        if (gameWorld.getGhost().getIsAlive()) drawExp();
        if (gameWorld.getState() == GameWorld.GameState.GAMEOVER) drawGravestone(delta);

        batch.end();

        //TEST CODE HERE
        if (gameWorld.getDev() == true) drawDev();


        //draw font
        //for testing and shit lol

    }

    private void drawTutorial() {
        batch.enableBlending();
        batch.draw(AssetLoader.tutorial, gameWorld.getTutorialScreen().getPosition().x,
                gameWorld.getTutorialScreen().getPosition().y);

    }

    private void drawOptions() {
        batch.enableBlending();
        batch.draw(AssetLoader.options, gameWorld.getOptions().getPosition().x,
                gameWorld.getOptions().getPosition().y);

    }

    private void drawMenu() {

        batch.enableBlending();
        batch.draw(AssetLoader.menu, gameWorld.getMenu().getPosition().x,
                gameWorld.getMenu().getPosition().y);

        AssetLoader.font3.draw(batch, "Highscore: " + AssetLoader.prefs.getInteger("highscore"), 240, 1160);
        AssetLoader.font3.draw(batch, "Total spooks: " + AssetLoader.prefs.getInteger("spookedmobs"), 240, 1100);
        AssetLoader.font3.draw(batch, "LVL: " + gameWorld.getLvl(), 240, 1040);
        AssetLoader.font3.draw(batch, "Next lvl: " + gameWorld.getExptolvl() + "/1", 240, 980);
        AssetLoader.font3.draw(batch, "Total EXP: " + AssetLoader.prefs.getInteger("exp"), 240, 920);


    }

    private void drawMainMenu() {
        batch.enableBlending();
        logoPos.lerp(logoTarget, 0.1f);
        AssetLoader.font.draw(batch, "Spooky\n Ghost", logoPos.x, logoPos.y);
        batch.draw(AssetLoader.mainmenu, gameWorld.getMainMenu().getPosition().x,
                gameWorld.getMainMenu().getPosition().y);
    }

    private void drawGravestone(float delta) {
        batch.enableBlending();
        batch.draw(AssetLoader.gravestone, gameWorld.getDeathScreen().getPosition().x,
                gameWorld.getDeathScreen().getPosition().y);
        scorePos.lerp(scoreTarget, 0.1f * delta);
        AssetLoader.font.draw(batch, "" + gameWorld.getScore(), scorePos.x, scorePos.y);
        AssetLoader.font.draw(batch, "" + AssetLoader.prefs.getInteger("highscore"), scorePos.x, scorePos.y - 250);
        if(gameWorld.getScoreSaved()){
            colorvar += 0.1;
            if (colorvar > 1) colorvar = 0;
            AssetLoader.font2.setColor(1 * colorvar * 0.5f, 1 * colorvar * 1f, 1 * colorvar * 0.5f, 1);
            AssetLoader.font2.getData().setScale(2, 2);
            AssetLoader.font2.draw(batch, "NEW HIGHSCORE!", 150, 400);
        }
        if(gameWorld.getLvlUp()){
            colorvar += 0.1;
            if (colorvar > 1) colorvar = 0;
            AssetLoader.font2.setColor(1 * colorvar * 0.5f, 1 * colorvar * 1f, 1 * colorvar * 0.5f, 1);
            AssetLoader.font2.getData().setScale(2, 2);
            AssetLoader.font2.draw(batch, "LEVEL UP!", 300, 270);

        }


    }

    private void drawSun() {
        batch.enableBlending();
        batch.setColor(1, 1, 1, 1);
        batch.draw(AssetLoader.sun, gameWorld.getSun().getPosition().x,
                gameWorld.getSun().getPosition().y);
        batch.setColor(1, 1, 1, 1);
    }

    private void drawExp() {

        colorvar += 0.1;

        if (colorvar > 1) colorvar = 0;
        if (gameWorld.getMob().getIsAlive()) expHeight = 552;
        AssetLoader.font2.setColor(1 * colorvar * 0.5f, 1 * colorvar * 1f, 1 * colorvar * 0.5f,
                1 * (552 / expHeight) / 2);
        if (!gameWorld.getMob().getIsAlive()) {
            AssetLoader.font2.draw(batch, "10 EXP", 500, expHeight);
            expHeight += 2;

        }

    }

    private void drawScore() {

        //score = gameWorld.getScore() + "";
        batch.enableBlending();
        //AssetLoader.font.draw(batch, "" + gameWorld.getScore(), 540, 1720);

        length = ("" + gameWorld.getScore()).length();
        AssetLoader.font.draw(batch, "" + gameWorld.getScore(), 495 - (30 * length), 1620);


    }

    private void drawGround() {
        batch.disableBlending();
        batch.draw(AssetLoader.ground, 0, 0);
    }

    private void drawSpike(float delta) {
        //spike1
        batch.draw(AssetLoader.longSpike, gameWorld.getSpike().getPositionX(),
                gameWorld.getSpike().getPositionY());
    }

    private void drawMob() {
        //draw mob shadow
        if (gameWorld.getMob().getY() >= 552) {
            batch.enableBlending();
            batch.setColor(1.0f, 1.0f, 1.0f, 0.5f * (560 / gameWorld.getMob().getY()));
            batch.draw(AssetLoader.shadow, gameWorld.getMob().getX(), 527);
        }
        batch.disableBlending();
        //draw mob and dead mob
        if (gameWorld.getMob().getIsAlive()) {
            batch.draw(AssetLoader.mob1, mob.getX(), mob.getY());
        } else {
            batch.draw(AssetLoader.mobDead, mob.getX(), mob.getY());
        }
        batch.setColor(1f, 1f, 1f, 1f);
    }

    private void drawGhost(float runTime) {

        //Draw shadow
        batch.enableBlending();

        if (gameWorld.getGhost().getY() > 500) {
            batch.setColor(1f, 1f, 1f, 0.5f * (560 / gameWorld.getGhost().getY()));
            batch.draw(AssetLoader.shadow, gameWorld.getGhost().getX(), 527);
        }

        //Ghost death effect, if dead paint red else white
        if (!gameWorld.getGhost().getIsAlive()) {
            batch.setColor(1f, 0.5f, 0.5f, 0.5f);
            batch.draw(AssetLoader.ghostDead, gameWorld.getGhost().getX(), gameWorld.getGhost().getY());
        } else {
            if (!gameWorld.getGhost().getIsSpooking()) {
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

        batch.setColor(1, 1, 1, 1);
        batch.draw(AssetLoader.frontMountain, gameWorld.getMountain2().getPositionX(), 552);
        batch.draw(AssetLoader.frontMountain,
                gameWorld.getMountain2().getPositionX() + AssetLoader.frontMountain.getRegionWidth() - 1, 552);
        /*fmPos -= 8 * delta;
        if (fmPos < -1080) {
            fmPos = -1;
        }*/
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
        shapeRenderer.rect(gameWorld.getScoreHitbox().getHitbox().x,
                gameWorld.getScoreHitbox().getHitbox().y,
                gameWorld.getScoreHitbox().getHitbox().getWidth(),
                gameWorld.getScoreHitbox().getHitbox().getHeight()
        );
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, 1630, (gameWorld.getExptolvl()) * 200, 10);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rect(0, 1630, 200, 10);
        shapeRenderer.end();
        batch.enableBlending();
        batch.begin();
        font.getData().setScale(2, 2);
        font.draw(batch, "Score: " + String.valueOf(gameWorld.getScore()), 0, 1910);
        font.draw(batch, "X: " + String.valueOf(gameWorld.getGhost().getX()), 0, 1870);
        font.draw(batch, "Y: " + String.valueOf(gameWorld.getGhost().getY()), 0, 1830);
        font.draw(batch, "Spooking: " + String.valueOf(gameWorld.getGhost().getIsSpooking()), 0, 1790);
        font.draw(batch, "Distance: " + String.valueOf(gameWorld.getDistance()), 0, 1750);
        font.draw(batch, "Version: beta1.0", 0, 1710);
        //not valid in deathscreen, too irrelevant to be fixed, data is correct (?)
        font.draw(batch, "EXP:" + String.valueOf(AssetLoader.prefs.getInteger("exp")), 0, 1670);
        batch.end();
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



