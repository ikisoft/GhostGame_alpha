package com.ikisoft.ghostgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.ikisoft.ghostgame.Helpers.AssetLoader;
import com.ikisoft.ghostgame.Helpers.DataHandler;
import com.ikisoft.ghostgame.Helpers.GestureHandler;
import com.ikisoft.ghostgame.Helpers.InputHandler;
import com.ikisoft.ghostgame.Render.GameRenderer;
import com.ikisoft.ghostgame.Render.GameWorld;

public class SpookyGhost extends Game {


    private GameRenderer renderer;
    private GameWorld world;
    private float delta, screenWidth, screenHeight;
    private InputMultiplexer multiplexer;

    @Override
    public void create() {

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        DataHandler.load();
        AssetLoader.load();
        world = new GameWorld();
        renderer = new GameRenderer(world);
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GestureDetector(new GestureHandler(world.getGhost(), world,
                screenWidth, screenHeight)));
        multiplexer.addProcessor(new InputHandler(world,
                screenWidth, screenHeight));
        Gdx.input.setInputProcessor(multiplexer);
    }


    @Override
    public void render() {

        delta = round(Gdx.graphics.getDeltaTime() * 60, 1);
        if (delta < 1) delta = 1;
        renderer.render(delta);
        world.update(delta);

    }

    public static float round(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / pow;
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        super.dispose();
        renderer.dispose();
        AssetLoader.dispose();
        DataHandler.save();

    }

    @Override
    public void pause() {
        if(world.getState() == GameWorld.GameState.RUNNING){
            world.setState(GameWorld.GameState.PAUSE);
        }
    }

}