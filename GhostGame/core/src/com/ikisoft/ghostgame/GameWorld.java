package com.ikisoft.ghostgame;

import com.badlogic.gdx.math.Rectangle;
import com.ikisoft.ghostgame.GameObjects.Ghost;

/**
 * Created by Max on 2.4.2017.
 */

public class GameWorld {

    public Ghost ghost;

        public GameWorld(){

            ghost = new Ghost();
        }

        public void update(float delta){
            ghost.update(delta);

        }

        public Rectangle getGhostRect(){
            return ghost.getGhostRect();
        }


}
