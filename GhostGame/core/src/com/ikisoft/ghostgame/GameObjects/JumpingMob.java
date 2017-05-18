package com.ikisoft.ghostgame.GameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ikisoft.ghostgame.Helpers.AssetLoader;
import com.ikisoft.ghostgame.Render.GameWorld;

import java.util.Random;

/**
 * Created by Max on 17.5.2017.
 */

public class JumpingMob{

    private Vector2 position;
    private Rectangle hitbox;
    private float gravity, velocityY, speed;
    private boolean isAlive, soundPlayed;
    private GameWorld gameWorld;
    private Random rand;
    private int minSpawn;
    private int maxSpawn;
    private int distance;

    public JumpingMob(float x, float y, GameWorld gameWorld) {

        this.gameWorld = gameWorld;
        isAlive = true;
        soundPlayed = false;
        position = new Vector2(x, y);
        hitbox = new Rectangle(position.x, position.y, 85, 85);
        gravity = 0.3f;
        rand = new Random();
        minSpawn = 1480;
        maxSpawn = 5000;
        speed = 8;
        distance = gameWorld.getDistance();
    }
    public void update(float delta) {

        if(position.y > 563)soundPlayed = false;
        if(distance > 10000){
            distance = 10000;
            maxSpawn = 1800;
        }
        if(distance > 5000)maxSpawn = 2500;
        if(distance > 2500)maxSpawn = 4000;

        velocityY -= gravity * delta;

        if (position.x < -200) {

            position.x = (rand.nextInt(maxSpawn - minSpawn) + minSpawn);
        }
        if (isAlive) {
            position.x -= speed * delta;
            hitbox.x = position.x;
            position.y += velocityY * delta;
            hitbox.y = position.y;

            if(position.y < 556) {
                jump();
            }

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

        if(!soundPlayed){
            if (!AssetLoader.soundMuted && position.x < 1080 && position.x > 0){
                AssetLoader.mobjump.play();
            }
        }
        soundPlayed = true;
        velocityY = rand.nextInt(17 + 8) - 8;
    }

    public void reset(float x, float y) {

        isAlive = true;
        position.x = x;
        position.y = y;
        hitbox.x = position.x;
        hitbox.y = position.y;
        soundPlayed = false;
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


