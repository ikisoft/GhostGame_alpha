package com.ikisoft.ghostgame.GameObjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Max on 12.4.2017.
 */

public class Sun {

    private Vector2 position;

    public Sun(){

        position = new Vector2();
        position.x = -300;
        position.y = 500;

    }

    public void update(float delta){

        position.x += 0.15 * delta;
        if(position.x < 320){
            position.y += 0.07 * delta;
        }else{
            position.y -= 0.1 * delta;
        }
        if(position.x > 1380){
            reset();
        }

    }

    public Vector2 getPosition() {
        return position;
    }

    private void reset() {
        position.x = - 300;
        position.y = 500;

    }
}
