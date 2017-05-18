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

    private GhostState ghostState;

    public enum GhostState {
        GHOST, KING, NINJA, PIRATE;
    }

    public Ghost(float x, float y) {

        if (AssetLoader.selectedTexture == 1) {
            ghostState = GhostState.GHOST;
        } else if (AssetLoader.selectedTexture == 2) {
            ghostState = GhostState.KING;
        } else if (AssetLoader.selectedTexture == 3) {
            ghostState = GhostState.NINJA;
        } else if (AssetLoader.selectedTexture == 4) {
            ghostState = GhostState.PIRATE;
        }

        position = new Vector2(x, y);
        hitbox = new Rectangle(position.x, position.y, 85, 119);
        jumpCount = 0;
        canJump = true;
        isSpooking = false;
        isAlive = true;
        //ORIGINAL 0.5f
        gravity = 0.7f;

    }

    public void update(float delta) {

        isSpooking = false;

        //Y pos, jump etc.
        velocityY -= gravity * delta;
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
        switch (ghostState){
            case GHOST:
                velocityX -= 2 * delta;
                if (velocityX < -20) velocityX = -20;
                break;
            case KING:
                velocityX -= 2 * delta;
                if (velocityX < -20) velocityX = -20;
                break;
            case NINJA:
                velocityX -= 6 * delta;
                if (velocityX < -60) velocityX = -60;
                break;
            case PIRATE:
                velocityX -= 2 * delta;
                if (velocityX < -20) velocityX = -20;
                break;
            default:
                break;
        }


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

    public void updateDead(float delta) {

        canJump = false;
        velocityY -= gravity * delta;
        position.y += velocityY * delta;
        hitbox.y = position.y;
        if (position.y < -500) {
            position.y = -500;
        }
    }

    //what the fuck am I coding??????????????????
    public void onClick() {

        switch (ghostState) {
            case GHOST:
                if (!isSpooking) {
                    if (jumpCount <= 1 && canJump) {
                        jump();
                        jumpCount++;

                    } else {
                        jumpCount = 0;
                        canJump = false;
                    }
                }
                break;
            case KING:
                if (!isSpooking) {
                    if (jumpCount <= 2 && canJump) {
                        jump();
                        jumpCount++;

                    } else {
                        jumpCount = 0;
                        canJump = false;
                    }
                }
                break;
            case NINJA:
                if (!isSpooking) {
                    if (jumpCount <= 1 && canJump) {
                        jump();
                        jumpCount++;

                    } else {
                        jumpCount = 0;
                        canJump = false;
                    }
                }
                break;
            case PIRATE:
                if (!isSpooking) {
                    if (jumpCount <= 1 && canJump) {
                        jump();
                        jumpCount++;

                    } else {
                        jumpCount = 0;
                        canJump = false;
                    }
                }
                break;
            default:
                if (!isSpooking) {
                    if (jumpCount <= 1 && canJump) {
                        jump();
                        jumpCount++;

                    } else {
                        jumpCount = 0;
                        canJump = false;
                    }
                }
                break;
        }
    }

    public void onFling() {
        if (position.x == 85 && isAlive) {
            spook();
        }
    }

    public void jump() {

        switch (ghostState) {
            case GHOST:
                if (!AssetLoader.soundMuted) AssetLoader.jump.play();
                velocityY = 24;
                velocityX = 0;
                break;
            case KING:
                if (!AssetLoader.soundMuted) AssetLoader.jump.play();
                velocityY = 24;
                velocityX = 0;
                break;
            case NINJA:
                if (!AssetLoader.soundMuted) AssetLoader.ninjajump.play();
                velocityY = 26;
                velocityX = 0;
                break;
            case PIRATE:
                if (!AssetLoader.soundMuted) AssetLoader.jump.play();
                velocityY = 24;
                velocityX = 0;
                break;

            default:
                break;
        }


    }

    public void spook() {

        switch (ghostState) {
            case GHOST:
                if (!AssetLoader.soundMuted) AssetLoader.spook.play();
                velocityX = 30;
                velocityY = 4;

                break;
            case KING:
                if (!AssetLoader.soundMuted) AssetLoader.spook.play();
                velocityX = 30;
                velocityY = 4;

                break;
            case NINJA:
                if (!AssetLoader.soundMuted) AssetLoader.ninjaspook.play();
                velocityX = 80;
                velocityY = 8;

                break;
            case PIRATE:
                if (!AssetLoader.soundMuted) AssetLoader.spook.play();
                velocityX = 30;
                velocityY = 4;
                break;

            default:
                break;
        }
    }

    public void die() {
        isAlive = false;
        velocityY = 10;
    }

    public void reset(float x, float y) {
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

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setGhostState(GhostState ghostState) {
        this.ghostState = ghostState;

    }

}