package com.mmalk.mazeball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.TimeUtils;
import com.mmalk.mazeball.helpers.AssetLoader;

//TODO: create some nice splash screen

public class SplashScreen implements Screen {

    private Game game;
    private long showTime;
    private final Stage stage;

    public SplashScreen(Game game){
        this.game = game;
        this.stage = new Stage();

        initSplashScreen();
    }

    private void initSplashScreen() {
        Label label = new Label(
                "Splash screen",
                AssetLoader.skin,
                "title-text"
        );
        label.setPosition(
                2 * Gdx.graphics.getWidth() / 4 - label.getWidth() / 2,
                2 * Gdx.graphics.getHeight() / 4 - label.getHeight() / 2);
        stage.addActor(label);
    }

    @Override
    public void show() {
        showTime = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {
        stage.act(delta);

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (TimeUtils.timeSinceMillis(showTime) > 2000) {
            game.setScreen(new ScreenMainMenu(game));
        }

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
