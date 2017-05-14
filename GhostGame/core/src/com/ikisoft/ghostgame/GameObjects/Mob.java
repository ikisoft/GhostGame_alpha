package com.ikisoft.ghostgame.GameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ikisoft.ghostgame.Helpers.AssetLoader;
import com.ikisoft.ghostgame.Render.GameWorld;

import java.util.Random;

/**
 * Created by Max on 6.4.2017.
 */

public class Mob {

    private Vector2 position;
    private Rectangle hitbox;
    private float gravity, velocityY, speed;
    boolean isAlive;
    private GameWorld gameWorld;
    private Random rand;
    private int minSpawn;
    private int maxSpawn;

    public Mob(float x, float y, GameWorld gameWorld) {

        this.gameWorld = gameWorld;
        isAlive = true;
        position = new Vector2(x, y);
        hitbox = new Rectangle(position.x, position.y, 85, 85);
        gravity = 0.5f;
        rand = new Random();
        minSpawn = 1180;
        maxSpawn = 2080;
        speed = 12;
    }

    public void update(float delta) {

        velocityY -= gravity * delta;


        //lol at myslf
        if (position.x < -100) {

            position.x = rand.nextInt(maxSpawn - minSpawn) + minSpawn;
        }
        //bad code lol
        if (isAlive) {
            position.x -= speed * delta;
            hitbox.x = position.x;

        } else {
            position.x -= 8 * delta;
            position.y += velocityY * delta;
            hitbox.x = position.x;
            hitbox.y = position.y;
            if (position.x < -100 || position.y < -100) {
                reset(rand.nextInt(maxSpawn - minSpawn) + minSpawn, 552);
            }


        }
    }

    public void die() {

        if (isAlive) {
            if (!AssetLoader.soundMuted) AssetLoader.mobhit.play();
            jump();
            gameWorld.setMobKilled();
        }

        isAlive = false;


    }

    public void jump() {

        velocityY = 10;
    }

    public void reset(float x, float y) {

        isAlive = true;
        position.x = x;
        position.y = y;
        hitbox.x = position.x;
        hitbox.y = position.y;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public float getX() {
        return position.x;

    }

    public float getY() {
        return position.y;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
