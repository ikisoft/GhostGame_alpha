package com.ikisoft.ghostgame.GameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ikisoft.ghostgame.AssetLoader;

import java.util.Random;

/**
 * Created by Max on 8.4.2017.
 */

public class Spike {

    private Rectangle hitbox, scoreHitbox;
    private Vector2 position;
    private Random rand;


    public Spike(float x, float y) {

        rand = new Random();
        position = new Vector2();
        position.x = x;
        position.y = y;
        hitbox = new Rectangle(position.x, position.y, 15, AssetLoader.longSpike.getRegionHeight() - 5);
        scoreHitbox = new Rectangle(position.x, position.y + AssetLoader.longSpike.getRegionHeight(),
                15, 1200);
    }


    public void update(float delta) {

        position.x -= 10 * delta;
        hitbox.x = position.x + 35;
        hitbox.y = position.y -5;
        scoreHitbox.x = position.x + 35;
        scoreHitbox.y = position.y + AssetLoader.longSpike.getRegionHeight();

        if (position.x < -100) spawn();

    }

    public void spawn() {
        position.x = 1200;
        position.y = rand.nextInt(500);
        //position.y = 200;

    }

    public void reset(float x, float y){

        position.x = x;
        position.y = y;
        hitbox.x = position.x;
        hitbox.y = position.y;
        scoreHitbox.x = position.x;
        scoreHitbox.y = position.y;


    }

    public Rectangle getScoreHitbox() {
        return scoreHitbox;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public float getPositionX() {
        return position.x;
    }

    public float getPositionY() {
        return position.y;
    }
}
