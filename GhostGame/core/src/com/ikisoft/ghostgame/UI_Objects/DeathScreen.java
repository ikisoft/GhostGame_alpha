package com.ikisoft.ghostgame.UI_Objects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Max on 12.4.2017.
 */

public class DeathScreen {

    private Vector2 position;
    private Vector2 target;


    public DeathScreen() {

        position = new Vector2(100, 2500);
        target = new Vector2(100, 500);


    }

    public void update(float delta) {

        position.lerp(target, 0.1f * delta);

    }

    public void reset() {

        position = new Vector2(100, 2500);


    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float position) {
        this.position.x = position;
    }


}


