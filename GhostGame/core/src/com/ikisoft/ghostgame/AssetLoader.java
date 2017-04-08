package com.ikisoft.ghostgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Max on 2.4.2017.
 */

public class AssetLoader {


    public static Animation<TextureRegion> ghostAnimation;
    public static TextureRegion background, backMountain, frontMountain, ghost1, ghost2, ghost3,
            shadow, mob1, spike;
    public static Texture texture;
    public static Sound dead, spook, jump;

    public static void load(){


        texture = new Texture(Gdx.files.internal("textures.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        background = new TextureRegion(texture, 0, 0, 1080, 1920);
        backMountain = new TextureRegion(texture, 1422, 0, 1080, 393);
        frontMountain = new TextureRegion(texture, 2503, 0, 1080, 393);
        ghost1 = new TextureRegion(texture, 1080, 0, 85, 119);
        ghost2 = new TextureRegion(texture, 1166, 0, 85, 119);
        ghost3 = new TextureRegion(texture, 1252, 0, 85, 119);
        shadow = new TextureRegion(texture, 1338, 0, 83, 25);
        mob1 = new TextureRegion(texture, 1080, 239, 85, 85);
        spike = new TextureRegion(texture, 1422, 394, 85, 390);
        TextureRegion[] ghosts = {ghost1, ghost2, ghost3};
        ghostAnimation = new Animation(0.3f, ghosts);
        ghostAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        dead = Gdx.audio.newSound(Gdx.files.internal("sounds/dead.wav"));
        jump = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));
        spook = Gdx.audio.newSound(Gdx.files.internal("sounds/spook.wav"));


    }

    public static void dispose(){
        texture.dispose();
        dead.dispose();
        jump.dispose();
        spook.dispose();
    }

}
