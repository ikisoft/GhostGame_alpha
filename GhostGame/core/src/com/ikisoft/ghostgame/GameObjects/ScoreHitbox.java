package com.ikisoft.ghostgame.GameObjects;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Max on 12.4.2017.
 */

public class ScoreHitbox {

    private Rectangle hitbox;
    private Spike spike;


    public ScoreHitbox(Spike spike){

        this.spike = spike;
        hitbox = new Rectangle(spike.getPositionX() + 200, 0,
                15, 1920);
    }

    public void update(){
        hitbox.x = spike.getPositionX() + 200;
    }

    public void reset(){
        hitbox.x = spike.getPositionX() + 200;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

}
