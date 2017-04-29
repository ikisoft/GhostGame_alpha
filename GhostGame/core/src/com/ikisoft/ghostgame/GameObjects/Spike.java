package com.ikisoft.ghostgame.GameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ikisoft.ghostgame.Helpers.AssetLoader;
import com.ikisoft.ghostgame.Render.GameWorld;

import java.util.Random;

/**
 * Created by Max on 8.4.2017.
 */

public class Spike {

    private Rectangle hitbox;
    private Vector2 position;
    private Random rand;
    private GameWorld gameWorld;
    private float difficultyScale, speed;
    private boolean scored;



    public Spike(float x, float y, GameWorld gameWorld) {

        this.gameWorld = gameWorld;
        rand = new Random();
        position = new Vector2();
        position.x = x;
        position.y = y;
        hitbox = new Rectangle(position.x, position.y, 15, AssetLoader.longSpike.getRegionHeight() - 5);
        speed = 12;
        scored = false;
    }


    public void update(float delta) {

        //increases difficulty over time, cap at 1000 (unit unknown)
        difficultyScale = gameWorld.getDistance();
        if(difficultyScale > 2000)difficultyScale = 2000;
        //
        position.x -= speed * delta;
        hitbox.x = position.x + 35;
        hitbox.y = position.y - 5;
        if (position.x < -100) spawn();

    }

    public void spawn() {
        System.out.println(difficultyScale);
        scored = false;
        if(difficultyScale < 1500){
            position.x = 1400 + rand.nextInt(300 + 300) - 300;
        }else{
            position.x = 1500 + rand.nextInt(450 + 300) - 300;
        }

        if(position.y > 490){
            position.y = rand.nextInt((int) (450 * difficultyScale / 2000));

        }else{
            position.y = rand.nextInt((int) (510 * difficultyScale / 2000));

        }
        //lol what the fuck :D
        //position.y = 200;


    }

    public void reset(float x, float y){

        position.x = x;
        position.y = y;
        hitbox.x = position.x;
        hitbox.y = position.y;
        scored = false;



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

    public boolean getScored(){
        return scored;
    }

    public void setScored(boolean scored) {
        this.scored = scored;
    }

}
