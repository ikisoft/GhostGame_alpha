package com.ikisoft.ghostgame.GameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ikisoft.ghostgame.Helpers.AssetLoader;

/**
 * Created by Max on 2.4.2017.
 */

public class Ghost {

    private float velocityX;
    private float velocityY;
    private float gravity;
    private Rectangle hitbox;
    private Vector2 position;
    private int jumpCount;
    private boolean canJump, isSpooking, isAlive;

    public Ghost(float x, float y) {

        position = new Vector2(x, y);
        hitbox = new Rectangle(position.x, position.y, 85, 119);
        jumpCount = 0;
        canJump = true;
        isSpooking = false;
        isAlive = true;
        gravity = 0.5f;

    }

    public void update(float delta) {

        isSpooking = false;
        //Y pos, jump etc.
        velocityY -= gravity * delta;
        //if (velocityY < -20) velocityY = -20;
        position.y += velocityY * delta;
        hitbox.y = position.y;

        if (position.y < 560) {
            position.y = 560;
            hitbox.y = position.y;
        }

        if (position.y == 560) {
            canJump = true;
            jumpCount = 0;
        }

        //x pos - Spook

        velocityX -= 2 * delta;
        if (velocityX < -20) velocityX = -20;

        position.x += velocityX * delta;
        hitbox.x = position.x;

        if (position.x > 85) {
            isSpooking = true;

        }

        if (position.x < 85) {
            position.x = 85;
            hitbox.x = position.x;
        }
    }

    public void updateDead(float delta){

        canJump = false;
        velocityY -= gravity * delta;
        position.y += velocityY * delta;
        hitbox.y = position.y;
        if (position.y < -500){
            position.y = -500;
        }
    }


    public void onClick() {
        if(!isSpooking){
            if (jumpCount <= 1 && canJump) {
                jump();
                jumpCount++;

            } else {
                jumpCount = 0;
                canJump = false;
            }
        }

    }

    public void onFling() {

        if (position.x == 85 && isAlive) {
            spook();
        }
    }

    public void jump() {

        if(!AssetLoader.soundMuted)AssetLoader.jump.play();
        velocityY = 20;
        velocityX = 0;
    }

    public void spook() {

        if(!AssetLoader.soundMuted)AssetLoader.spook.play();
        velocityX = 30;
        velocityY = 4;
    }

    public void die(){
        //velocityY = 0;
        System.out.println("u died");
        isAlive = false;
        velocityY = 10;


    }

    public void reset(float x, float y){
        velocityX = 0;
        jumpCount = 0;
        canJump = true;
        isSpooking = false;
        isAlive = true;
        position.x = x;
        position.y = y;
        hitbox.x = x;
        hitbox.y = y;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public boolean getIsSpooking() {
        return isSpooking;
    }

    public boolean getIsAlive(){
        return isAlive;
    }

}