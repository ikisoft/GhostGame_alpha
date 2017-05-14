package com.ikisoft.ghostgame.UI_Objects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Max on 13.4.2017.
 */

public class Menu {

    private Vector2 position;
    private Vector2 target;

    public Menu() {

        position = new Vector2(-500, 400);
        target = new Vector2(150, 400);
    }

    public void update(float delta) {

        position.lerp(target, 0.1f * delta);
    }

    public void reset() {

        position = new Vector2(-500, 400);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float position) {
        this.position.x = position;
    }


}