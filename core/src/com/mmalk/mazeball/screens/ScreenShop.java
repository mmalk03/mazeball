package com.mmalk.mazeball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mmalk.mazeball.helpers.AssetLoader;

public class ScreenShop implements Screen {

    private final Stage stage;
    private final Game game;
    private final float buttonWidth = Gdx.graphics.getWidth() / 8;
    private final float buttonHeight = Gdx.graphics.getWidth() / 8;
    private final float bigButtonWidth = Gdx.graphics.getWidth() / 4;
    private final float bigButtonHeight = Gdx.graphics.getWidth() / 4;

    public ScreenShop(Game game) {
        this.stage = new Stage();
        this.game = game;

        initializeButtons();
        initializeInput();
    }

    private void initializeInput() {
        Gdx.input.setCatchBackKey(true);
        InputProcessor backButtonProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    game.setScreen(new ScreenMainMenu(game));
                }
                return true;
            }
        };

        InputMultiplexer inputMultiplexer = new InputMultiplexer(
                stage,
                backButtonProcessor
        );

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void initializeButtons() {
        Label label = new Label(
                "Shop",
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

    }

    @Override
    public void render(float delta) {
        stage.act(delta);

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
