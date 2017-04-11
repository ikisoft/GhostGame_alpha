package com.ikisoft.ghostgame.GameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ikisoft.ghostgame.AssetLoader;
import com.ikisoft.ghostgame.GameWorld;

import java.util.Random;

/**
 * Created by Max on 6.4.2017.
 */

public class Mob {

    private Vector2 position;
    private Rectangle hitbox;
    private float gravity;
    private float velocityY;
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
        minSpawn = 800;
        maxSpawn = 1000;
    }

    public void update(float delta) {

        velocityY -= gravity * delta;

        if(position.x < -100 && gameWorld.getSpike().getPositionY() < 300){
            position.x = + gameWorld.getSpike().getPositionX()
                    + rand.nextInt(maxSpawn - minSpawn) + minSpawn;

        }
        //bad code lol
        if (isAlive) {
            position.x -= 10 * delta;
            hitbox.x = position.x;

        } else {
            position.x -= 10 * delta;
            position.y += velocityY * delta;
            hitbox.x = position.x;
            hitbox.y = position.y;
            if(position.y < - 100 || position.x < - 100){
                reset(1200, 552);
            }


        }
    }

    public void die() {

        if(isAlive){
            AssetLoader.mobhit.play();
            jump();
        }

        isAlive = false;


    }

    private void jump() {

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
