package com.ikisoft.ghostgame.UI_Objects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Max on 13.4.2017.
 */

public class Menu {

    private Vector2 position, expPosition;
    private Vector2 target, expTarget;

    public Menu() {

        position = new Vector2(-500, 400);
        target = new Vector2(150, 400);
        expPosition = new Vector2(-500, 1498);
        expTarget = new Vector2(382, 1498);

    }

    public void update(float delta) {
        position.lerp(target, 0.1f * delta);
        expPosition.lerp(expTarget, 0.1f * delta);

    }

    public void reset() {

        //position = new Vector2(-500, 400);
        position.x = -500;
        position.y = 400;
        expPosition.x = -500;
        expPosition.y = 1498;

    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getExpPosition(){
        return expPosition;
    }

    public void setPosition(float position) {
        this.position.x = position;
    }


}