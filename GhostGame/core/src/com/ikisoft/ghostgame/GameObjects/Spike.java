package com.ikisoft.ghostgame.GameObjects;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Max on 8.4.2017.
 */

public class Spike {

    private Rectangle hitbox;
    private float positionX;

    public Spike(float x){

        positionX = x;
        hitbox = new Rectangle(positionX, 522, 10, 390);
    }



    public void update(float delta){

        positionX -= 10 * delta;
        hitbox.x = positionX + 35;

        if(positionX < -100)positionX = 1200;

    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public float getPositionX() {
        return positionX;
    }
}
