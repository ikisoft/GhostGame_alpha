package com.ikisoft.ghostgame.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Max on 2.4.2017.
 */

public class Ghost {

    private float velocityY;
    private float gravity;
    private static Rectangle ghostRect;


    public Ghost() {

        ghostRect = new Rectangle(85, 560, 85, 119);

    }

    public void update(float delta) {

        gravity = -0.5f * delta;
        //System.out.println(gravity);
        velocityY += gravity * delta;
        ghostRect.y += velocityY;
        if (ghostRect.y < 560) {
            ghostRect.y = 560;
        }


    }

    public void onClick() {
        jump();


    }

    public void jump() {
        velocityY = 15 * Gdx.graphics.getDeltaTime() * 60;
        System.out.println(velocityY);



    }

    public Rectangle getGhostRect() {
        return ghostRect;
    }

}
