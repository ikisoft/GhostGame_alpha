package com.ikisoft.ghostgame.GameObjects;

/**
 * Created by Max on 10.4.2017.
 */

public class ScrollingBg {

    private float positionX;
    private float speed;

    public ScrollingBg(float speed) {

        positionX = 0;
        this.speed = speed;
    }

    public void update(float delta) {
        positionX -= speed * delta;
        if (positionX <= -1078) {
            positionX = 0;
        }
    }

    public float getPositionX() {
        return positionX;
    }
}
