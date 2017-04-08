package com.ikisoft.ghostgame.GameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Max on 6.4.2017.
 */

public class NormalMob {

    private Vector2 position;
    private Rectangle hitbox;
    boolean isAlive;

    public NormalMob(float x, float y){

        isAlive = true;
        position = new Vector2(x, y);
        hitbox = new Rectangle(position.x, position.y, 85, 85);

    }

    public void update(float delta){

        //bad code lol
        if(isAlive){
            position.x -= 10 * delta;
            hitbox.x -= 10*delta;
            if(position.x < -90){
                position.x = 1080;
                hitbox.x = 1080;
            }
        }else{
            position.x -= 10 * delta;
            hitbox.x -= 10*delta;
            position.y-=10;
            hitbox.y-=10;
        }
    }

    public void die(){

        isAlive = false;
    }

    public void restart(float x, float y){

        isAlive = true;
        position.x = x;
        position.y = y;
        hitbox.x = x;
        hitbox.y = y;
    }

    public boolean getIsAlive(){
        return isAlive;
    }

    public float getX(){
        return position.x;

    }

    public float getY(){
        return position.y;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
