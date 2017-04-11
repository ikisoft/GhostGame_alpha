package com.ikisoft.ghostgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Max on 2.4.2017.
 */

public class AssetLoader {


    public static Animation<TextureRegion> ghostAnimation;
    public static TextureRegion background, ground, backMountain, frontMountain, ghost1, ghost2,
            ghost3, ghostDead, ghostSpooking,
            shadow, mob1, mobDead, spike, longSpike;
    public static Texture texture;
    public static Sound dead, spook, jump, score, mobhit;
    public static Music hum;
    public static BitmapFont font;
    public static BitmapFont font2;

    public static void load(){


        texture = new Texture(Gdx.files.internal("textures.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        background = new TextureRegion(texture, 0, 0, 1080, 1920);
        ground = new TextureRegion(texture, 0, 1368, 1080, 552);
        backMountain = new TextureRegion(texture, 1422, 0, 1080, 393);
        frontMountain = new TextureRegion(texture, 2503, 0, 1080, 393);
        ghost1 = new TextureRegion(texture, 1080, 0, 85, 119);
        ghost2 = new TextureRegion(texture, 1166, 0, 85, 119);
        ghost3 = new TextureRegion(texture, 1252, 0, 85, 119);
        ghostDead = new TextureRegion(texture, 1080, 128, 85, 110);
        ghostSpooking = new TextureRegion(texture, 1166, 128, 85, 110);
        shadow = new TextureRegion(texture, 1338, 0, 83, 25);
        mob1 = new TextureRegion(texture, 1080, 239, 85, 85);
        mobDead = new TextureRegion(texture, 1080, 325, 85, 85);
        spike = new TextureRegion(texture, 1422, 394, 85, 390);
        longSpike = new TextureRegion(texture, 1508, 394, 85, 770);
        TextureRegion[] ghosts = {ghost1, ghost2, ghost3};
        ghostAnimation = new Animation(0.3f, ghosts);
        ghostAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        dead = Gdx.audio.newSound(Gdx.files.internal("sounds/dead.wav"));
        jump = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));
        spook = Gdx.audio.newSound(Gdx.files.internal("sounds/spook.wav"));
        score = Gdx.audio.newSound(Gdx.files.internal("sounds/score.wav"));
        mobhit = Gdx.audio.newSound(Gdx.files.internal("sounds/mobhit.wav"));
        /*hum = Gdx.audio.newMusic(Gdx.files.internal("sounds/hum.wav"));
        hum.setVolume(0.5f);
        hum.setLooping(true);
        hum.play();*/
        font = new BitmapFont(Gdx.files.internal("data/font5.fnt"));
        font.getData().setScale(2, 2);
        font2 = new BitmapFont(Gdx.files.internal("data/pixel.fnt"));
        font2.getData().setScale(1f, 1f);
        /*FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("data/5squared-pixel.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        generator.dispose();

        font2 = generator.generateFont(parameter);
        //font.setColor(1, 1, 1, 1);*/


    }

    public static void dispose(){
        texture.dispose();
        dead.dispose();
        jump.dispose();
        spook.dispose();
        score.dispose();
        mobhit.dispose();
        font.dispose();
        font2.dispose();

    }

}
