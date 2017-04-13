package com.ikisoft.ghostgame.UI_Objects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Max on 13.4.2017.
 */

public class Options {

    private Vector2 position;
    private Vector2 target;

    public Options() {

        position = new Vector2(-500, 700);
        target = new Vector2(150, 700);
    }

    public void update(float delta) {

        position.lerp(target, 0.1f * delta);
    }

    public void reset(){

        position = new Vector2(-500, 700);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float position) {
        this.position.x = position;
    }


}

