package com.ikisoft.ghostgame.Render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.ikisoft.ghostgame.GameObjects.JumpingMob;
import com.ikisoft.ghostgame.GameObjects.Mob;
import com.ikisoft.ghostgame.Helpers.AssetLoader;

import sun.security.provider.SHA;

import static com.ikisoft.ghostgame.Helpers.AssetLoader.jumpingMob;
import static com.ikisoft.ghostgame.Helpers.AssetLoader.prefs;
import static com.ikisoft.ghostgame.Helpers.AssetLoader.texture;

/**
 * Created by Max on 2.4.2017.
 */

public class GameRenderer {

    private static final float VIRTUAL_WIDTH = 1080;
    private static final float VIRTUAL_HEIGHT = 1920;
    private static OrthographicCamera cam;
    private float runTime;
    private static SpriteBatch batch;
    private GameWorld gameWorld;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font = new BitmapFont();
    private float colorvar, colorvar2 = 0;
    private float expHeight, expHeight2;
    private int length;
    private Vector2 logoPos, logoTarget, scorePos, scoreTarget, unlSplashPos, unlSplashTarget;

    public GameRenderer(GameWorld world) {

        gameWorld = world;
        runTime = 0;
        cam = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(cam.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
        logoPos = new Vector2(75, 2500);
        logoTarget = new Vector2(75, 1400);
        scorePos = new Vector2(495 - (30 * length), 1620);
        scoreTarget = new Vector2(510 - (30 * length), 1125);
        unlSplashPos = new Vector2(1400, 1600);
        unlSplashTarget = new Vector2(412, 1600);
        expHeight = gameWorld.getMob().getY();
        expHeight2 = gameWorld.getJumpingMob().getY();
    }

    public void render(float delta) {

        //cam.update();
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
        drawJumpingMob();


        //score and exp
        if (gameWorld.getState() != GameWorld.GameState.GAMEOVER) {
            scorePos.x = 495 - (30 * length);
            scorePos.y = 1620;
            unlSplashPos.x = 1400;
            unlSplashPos.y = 1600;

        }
        if (gameWorld.getState() == GameWorld.GameState.MAINMENU) drawMainMenu();
        if (gameWorld.getState() == GameWorld.GameState.OPTIONS) drawOptions();
        if (gameWorld.getState() == GameWorld.GameState.RUNNING) drawScore();
        if (gameWorld.getState() == GameWorld.GameState.MENU) drawMenu();
        if (gameWorld.getState() == GameWorld.GameState.PAUSE) drawPause();
        if (gameWorld.getState() == GameWorld.GameState.TUTORIAL) drawTutorial();
        if (gameWorld.getGhost().getIsAlive()) {
            drawExp();
            drawExp2();
        }
        if (gameWorld.getState() == GameWorld.GameState.GAMEOVER) drawGravestone(delta);
        batch.end();

        //TEST CODE HERE
        if (gameWorld.getDev()) drawDev();
        //cam.update();
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
        if (AssetLoader.soundMuted) {
            batch.draw(AssetLoader.toggledOff, 250, 900);
        }
        if (AssetLoader.musicMuted) {
            batch.draw(AssetLoader.toggledOff, 600, 900);
        }
    }

    private void drawMenu() {

        batch.enableBlending();
        //draw bg
        batch.draw(AssetLoader.menu, gameWorld.getMenu().getPosition().x,
                gameWorld.getMenu().getPosition().y);
        //draw exp
        AssetLoader.font3.draw(batch, AssetLoader.prefs.getInteger("exp") + " exp", gameWorld.getMenu().getPosition().x + 225,
                gameWorld.getMenu().getPosition().y + 1075);

        //draw highscore
        length = ("" + AssetLoader.prefs.getInteger("highscore")).length();
        AssetLoader.font4.draw(batch, "" + AssetLoader.prefs.getInteger("highscore"),
                gameWorld.getMenu().getPosition().x + 250 - (30 * length)
                , gameWorld.getMenu().getPosition().y + 825);
        //draw spooked mobs
        length = ("" + AssetLoader.prefs.getInteger("spookedmobs")).length();
        AssetLoader.font4.draw(batch, "" + AssetLoader.prefs.getInteger("spookedmobs"),
                gameWorld.getMenu().getPosition().x + 585 - (30 * length)
                , gameWorld.getMenu().getPosition().y + 825);
        //Draw lvl
        AssetLoader.font3.draw(batch, "" + gameWorld.getLvl(), gameWorld.getMenu().getPosition().x + 375
                , gameWorld.getMenu().getPosition().y + 1176);

        drawRanks();
        drawSkinIcons();

        //Draw EXP BAR
        for (int i = 0; i < gameWorld.getExptolvl() * 30; i++) {

            batch.draw(AssetLoader.expCell, gameWorld.getMenu().getExpPosition().x + i * 10,
                    gameWorld.getMenu().getExpPosition().y);
        }
    }

    private void drawMainMenu() {
        batch.enableBlending();
        logoPos.lerp(logoTarget, 0.1f);
        batch.draw(AssetLoader.logo, logoPos.x, logoPos.y);
        batch.draw(AssetLoader.mainmenu, gameWorld.getMainMenu().getPosition().x,
                gameWorld.getMainMenu().getPosition().y);
    }

    private void drawPause() {
        batch.enableBlending();
        batch.draw(AssetLoader.pause, 150, 200);
        AssetLoader.font.draw(batch, "  game\npaused", 150, 1500);

        AssetLoader.font3.draw(batch, "" + "tap the play button\n        to continue", 150, 1100);
    }

    private void drawGravestone(float delta) {
        batch.enableBlending();
        batch.draw(AssetLoader.gravestone, gameWorld.getDeathScreen().getPosition().x,
                gameWorld.getDeathScreen().getPosition().y);
        scorePos.lerp(scoreTarget, 0.1f * delta);
        AssetLoader.font.draw(batch, "" + gameWorld.getScore(), scorePos.x, scorePos.y);
        AssetLoader.font.draw(batch, "" + AssetLoader.prefs.getInteger("highscore"), scorePos.x, scorePos.y - 250);
        if (gameWorld.getScoreSaved()) {
            /*colorvar += 0.1;
            if (colorvar > 1) colorvar = 0;
            AssetLoader.font2.setColor(1 * colorvar * 0.5f, 1 * colorvar * 1f, 1 * colorvar * 0.5f, 1);
            AssetLoader.font2.getData().setScale(2, 2);*/
            AssetLoader.font4.draw(batch, "NEW HIGHSCORE", unlSplashPos.x - 310, 400);
        }
        if (gameWorld.getLvlUp()) {
            /*colorvar += 0.1;
            if (colorvar > 1) colorvar = 0;
            AssetLoader.font2.setColor(1 * colorvar * 0.5f, 1 * colorvar * 1f, 1 * colorvar * 0.5f, 1);
            AssetLoader.font2.getData().setScale(2, 2);*/
            AssetLoader.font4.draw(batch, "LEVEL UP", unlSplashPos.x - 125, 300);
        }

        unlSplashPos.lerp(unlSplashTarget, 0.1f);

        if(gameWorld.getPirateUnl()){
            batch.draw(AssetLoader.pirateUnlSplash, unlSplashPos.x, 1500);
            AssetLoader.font3.draw(batch, "CPT. SPOOK UNLOCKED", unlSplashPos.x - 290, 200);
        }
        if(gameWorld.getNinjaUnl()){
            batch.draw(AssetLoader.ninjaUnlSplash, unlSplashPos.x, 1500);
            AssetLoader.font3.draw(batch, "GHOST NINJA UNLOCKED", unlSplashPos.x - 315, 200);
        }
        if(gameWorld.getKingUnl()){
            batch.draw(AssetLoader.kingUnlSplash, unlSplashPos.x, 1500);
            AssetLoader.font3.draw(batch, "KING GHOST\n  UNLOCKED", unlSplashPos.x - 295, 200);

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
        if (gameWorld.getMob().getIsAlive()) expHeight = gameWorld.getMob().getY();
        AssetLoader.font2.setColor(1 * colorvar * 0.5f, 1 * colorvar * 1f, 1 * colorvar * 0.5f,
                1 * (gameWorld.getMob().getY() / expHeight) / 2);
        if (!gameWorld.getMob().getIsAlive()) {
            if (AssetLoader.selectedTexture == 4) {
                AssetLoader.font2.draw(batch, "15 EXP", gameWorld.getMob().getX(), expHeight);
            } else {
                AssetLoader.font2.draw(batch, "10 EXP", gameWorld.getMob().getX(), expHeight);
            }

            expHeight += 2;
        }
    }

    private void drawExp2() {
        colorvar2 += 0.1;

        if (colorvar2 > 1) colorvar2 = 0;
        if (gameWorld.getJumpingMob().getIsAlive()) expHeight2 = gameWorld.getJumpingMob().getY();
        AssetLoader.font2.setColor(1 * colorvar2 * 0.5f, 1 * colorvar2 * 1f, 1 * colorvar2 * 0.5f,
                1 * (gameWorld.getJumpingMob().getY() / expHeight2) / 2);
        if (!gameWorld.getJumpingMob().getIsAlive()) {
            if (AssetLoader.selectedTexture == 4) {
                AssetLoader.font2.draw(batch, "30 EXP", gameWorld.getMob().getX(), expHeight);
            } else {
                AssetLoader.font2.draw(batch, "20 EXP", gameWorld.getJumpingMob().getX(), expHeight2);
            }

            expHeight2 += 2;
        }
    }

    private void drawScore() {

        batch.enableBlending();
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
            //batch.setColor(1.0f, 1.0f, 1.0f, 0.5f * (560 / gameWorld.getMob().getY()));
            batch.draw(AssetLoader.shadow, gameWorld.getMob().getX(), 527);
        }
        batch.disableBlending();
        //draw mob and dead mob
        if (gameWorld.getMob().getIsAlive()) {
            batch.draw(AssetLoader.mob1, gameWorld.getMob().getX(), gameWorld.getMob().getY());
        } else {
            batch.draw(AssetLoader.mobDead, gameWorld.getMob().getX(), gameWorld.getMob().getY());
        }
        batch.setColor(1f, 1f, 1f, 1f);
    }

    private void drawJumpingMob() {
        //JUMPING MOB
        //shadow
        if (gameWorld.getJumpingMob().getY() >= 552) {
            batch.enableBlending();
            batch.setColor(1.0f, 1.0f, 1.0f, 0.5f * (560 / gameWorld.getJumpingMob().getY()));
            batch.draw(AssetLoader.shadow, gameWorld.getJumpingMob().getX(), 527);
        }
        batch.setColor(1f, 1f, 1f, 1f);
        //Draw JUMPING MOB
        if (gameWorld.getJumpingMob().getIsAlive()) {
            batch.draw(jumpingMob, gameWorld.getJumpingMob().getX(),
                    gameWorld.getJumpingMob().getY());
        } else {
            batch.draw(AssetLoader.jumpingMobDead, gameWorld.getJumpingMob().getX(),
                    gameWorld.getJumpingMob().getY());
        }

        batch.disableBlending();
    }


    private void drawGhost(float runTime) {
        //TEXTURE OFFSET 33 pixels
        //Draw shadow
        batch.enableBlending();

        if (gameWorld.getGhost().getY() > 500) {
            batch.setColor(1f, 1f, 1f, 0.5f * (560 / gameWorld.getGhost().getY()));
            batch.draw(AssetLoader.shadow, gameWorld.getGhost().getX(), 527);
        }

        //Ghost death effect, if dead paint red else white
        if (!gameWorld.getGhost().getIsAlive()) {
            batch.setColor(1f, 0.5f, 0.5f, 0.5f);
            batch.draw(AssetLoader.ghostDead,
                    gameWorld.getGhost().getX() - 33, gameWorld.getGhost().getY());
        } else {
            if (!gameWorld.getGhost().getIsSpooking()) {
                batch.setColor(1.0f, 1.0f, 1.0f, 0.8f);
                batch.draw(AssetLoader.ghostAnimation.getKeyFrame(runTime),
                        gameWorld.getGhost().getX() - 33, gameWorld.getGhost().getY());
            } else {
                batch.setColor(1.0f, 1.0f, 1.0f, 0.8f * (85 / gameWorld.getGhost().getX()));
                batch.draw(AssetLoader.ghostSpooking,
                        gameWorld.getGhost().getX() - 33, gameWorld.getGhost().getY());

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
    }

    public void drawFrontMountain(float delta) {

        batch.setColor(1, 1, 1, 1);
        batch.draw(AssetLoader.frontMountain, gameWorld.getMountain2().getPositionX(), 552);
        batch.draw(AssetLoader.frontMountain,
                gameWorld.getMountain2().getPositionX() + AssetLoader.frontMountain.getRegionWidth() - 1, 552);
    }

    private void drawRanks() {
        //draw ranks
        if (AssetLoader.prefs.getInteger("spookedmobs") < 10) {
            batch.draw(AssetLoader.rankStone, gameWorld.getMenu().getPosition().x + 65,
                    gameWorld.getMenu().getPosition().y + 1000);
            AssetLoader.font3.draw(batch, "noob", gameWorld.getMenu().getPosition().x + 225,
                    gameWorld.getMenu().getPosition().y + 1030);
        } else if (AssetLoader.prefs.getInteger("spookedmobs") >= 10
                && AssetLoader.prefs.getInteger("spookedmobs") < 100) {
            batch.draw(AssetLoader.bronzeAnimation.getKeyFrame(runTime), gameWorld.getMenu().getPosition().x + 65,
                    gameWorld.getMenu().getPosition().y + 1000);
            AssetLoader.font3.draw(batch, "bronze", gameWorld.getMenu().getPosition().x + 225,
                    gameWorld.getMenu().getPosition().y + 1030);

        } else if (AssetLoader.prefs.getInteger("spookedmobs") >= 100
                && AssetLoader.prefs.getInteger("spookedmobs") < 500) {
            batch.draw(AssetLoader.silverAnimation.getKeyFrame(runTime), gameWorld.getMenu().getPosition().x + 65,
                    gameWorld.getMenu().getPosition().y + 1000);
            AssetLoader.font3.draw(batch, "silver", gameWorld.getMenu().getPosition().x + 225,
                    gameWorld.getMenu().getPosition().y + 1030);

        } else if (AssetLoader.prefs.getInteger("spookedmobs") >= 500
                && AssetLoader.prefs.getInteger("spookedmobs") < 2500) {
            batch.draw(AssetLoader.goldAnimation.getKeyFrame(runTime), gameWorld.getMenu().getPosition().x + 65,
                    gameWorld.getMenu().getPosition().y + 1000);
            AssetLoader.font3.draw(batch, "gold", gameWorld.getMenu().getPosition().x + 225,
                    gameWorld.getMenu().getPosition().y + 1030);

        } else if (AssetLoader.prefs.getInteger("spookedmobs") >= 2500) {
            batch.draw(AssetLoader.diamondAnimation.getKeyFrame(runTime), gameWorld.getMenu().getPosition().x + 65,
                    gameWorld.getMenu().getPosition().y + 1000);
            AssetLoader.font3.draw(batch, "diamond", gameWorld.getMenu().getPosition().x + 225,
                    gameWorld.getMenu().getPosition().y + 1030);

        }
    }

    private void drawSkinIcons() {

        if (AssetLoader.selectedTexture == 1) {
            batch.draw(AssetLoader.selectedIcon, gameWorld.getMenu().getPosition().x + 127, 826);
            //info text
            AssetLoader.font4.draw(batch, "Spooky", gameWorld.getMenu().getPosition().x + 225,
                    905);
            AssetLoader.font3.draw(batch, "-double jump\n-very scary",
                    gameWorld.getMenu().getPosition().x + 75, 775);
        }

        if (AssetLoader.selectedTexture == 4 && !prefs.getBoolean("pirateUnlocked")) {
            batch.draw(AssetLoader.selectedIcon, gameWorld.getMenu().getPosition().x + 283, 826);
            AssetLoader.font4.draw(batch, "Cpt. Spook", gameWorld.getMenu().getPosition().x + 115,
                    905);
            AssetLoader.font3.draw(batch, "-reach lvl 5\n" +
                            "to unlock",
                    gameWorld.getMenu().getPosition().x + 75, 775);


        } else if (prefs.getBoolean("pirateUnlocked")) {
            //batch.draw(AssetLoader.pirateIcon, 433, 826);
            batch.draw(AssetLoader.pirateIcon, gameWorld.getMenu().getPosition().x + 283, 826);

            if (AssetLoader.selectedTexture == 4) {
                batch.draw(AssetLoader.selectedIcon, gameWorld.getMenu().getPosition().x + 283, 826);
                //info text
                AssetLoader.font4.draw(batch, "Cpt. Spook", gameWorld.getMenu().getPosition().x + 115,
                        905);
                AssetLoader.font3.draw(batch, "-1.5x exp\n-yarr!",
                        gameWorld.getMenu().getPosition().x + 75, 775);
            }

        }

        if (AssetLoader.selectedTexture == 3 && !prefs.getBoolean("ninjaUnlocked")) {
            batch.draw(AssetLoader.selectedIcon, gameWorld.getMenu().getPosition().x + 436, 826);
            AssetLoader.font4.draw(batch, "Ghost Ninja", gameWorld.getMenu().getPosition().x + 67,
                    905);
            AssetLoader.font3.draw(batch, "-reach 'silver'\n" +
                            "rank to unlock",
                    gameWorld.getMenu().getPosition().x + 75, 775);


        } else if (prefs.getBoolean("ninjaUnlocked")) {
            //batch.draw(AssetLoader.ninjaIcon, 586, 826);
            batch.draw(AssetLoader.ninjaIcon, gameWorld.getMenu().getPosition().x + 436, 826);

            if (AssetLoader.selectedTexture == 3) {
                batch.draw(AssetLoader.selectedIcon, gameWorld.getMenu().getPosition().x + 436, 826);
                //info text
                AssetLoader.font4.draw(batch, "Ghost Ninja", gameWorld.getMenu().getPosition().x + 67,
                        905);
                AssetLoader.font3.draw(batch, "-Ninja moves\n-Ghost or ninja?",
                        gameWorld.getMenu().getPosition().x + 75, 775);
            }

        }

        if (AssetLoader.selectedTexture == 2 && !prefs.getBoolean("kingUnlocked")) {
            batch.draw(AssetLoader.selectedIcon, gameWorld.getMenu().getPosition().x + 592, 826);
            AssetLoader.font4.draw(batch, "King Ghost", gameWorld.getMenu().getPosition().x + 105,
                    905);
            AssetLoader.font3.draw(batch, "-reach 'gold'\n" +
                            "rank and lvl 10\n" +
                            "to unlock",
                    gameWorld.getMenu().getPosition().x + 75, 775);

        } else if (prefs.getBoolean("kingUnlocked")) {
            //batch.draw(AssetLoader.kingIcon, 742, 826);
            batch.draw(AssetLoader.kingIcon, gameWorld.getMenu().getPosition().x + 592, 826);

            if (AssetLoader.selectedTexture == 2) {
                batch.draw(AssetLoader.selectedIcon, gameWorld.getMenu().getPosition().x + 592, 826);
                //info text
                AssetLoader.font4.draw(batch, "King Ghost", gameWorld.getMenu().getPosition().x + 105,
                        905);
                AssetLoader.font3.draw(batch, "-triple jump!\n-A royal ghost",
                        gameWorld.getMenu().getPosition().x + 75, 775);
            }

        }

    }

    private void drawDev() {
        //spike hitbox
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rect(gameWorld.getSpike().getHitbox().x,
                gameWorld.getSpike().getHitbox().y,
                gameWorld.getSpike().getHitbox().getWidth(),
                gameWorld.getSpike().getHitbox().getHeight());
        //ghost hitbox
        shapeRenderer.rect(gameWorld.getGhost().getHitbox().x,
                gameWorld.getGhost().getHitbox().y,
                gameWorld.getGhost().getHitbox().getWidth(),
                gameWorld.getGhost().getHitbox().getHeight());
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
        font.draw(batch, "Version: 2.0", 0, 1710);
        //not valid in death screen, too irrelevant to be fixed, data is correct (?)
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

    public void setUnlSplashReset(){

    }


}



