package com.mmalk.mazeball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mmalk.mazeball.helpers.AssetLoader;

//TODO: create some nice logo
//TODO: animate mainBall, portals, winningSquare, coins

public class ScreenMainMenu implements Screen {

    private final Stage stage;
    private final Game game;
    private final float buttonWidth = Gdx.graphics.getWidth() / 8;
    private final float buttonHeight = Gdx.graphics.getWidth() / 8;
    private final float bigButtonWidth = Gdx.graphics.getWidth() / 4;
    private final float bigButtonHeight = Gdx.graphics.getWidth() / 4;

    public ScreenMainMenu(final Game game) {
        this.stage = new Stage(new ScreenViewport());
        this.game = game;

        initializeButtons();
        initializeInput();
    }

    private void initializeInput() {
        Gdx.input.setCatchBackKey(true);
        InputProcessor backButtonProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK)
                    //Gdx.app.log(TAG, "onBackButtonClick");
                    Gdx.app.exit();
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
        //initializeMenu();
        initButtonEndless();
        initButtonLevel();
        initLogo();
    }

    private void initLogo() {
        ImageButton buttonLogo = new ImageButton(
                AssetLoader.skin,
                "logo"
        );
        buttonLogo.setPosition(
                Gdx.graphics.getWidth() / 2 - buttonLogo.getWidth() / 2,
                Gdx.graphics.getHeight() - buttonLogo.getHeight());
        stage.addActor(buttonLogo);
    }

    private void initButtonLevel() {
        ImageButton buttonLevel = new ImageButton(
                AssetLoader.skin,
                "list");
        buttonLevel.getImageCell().expand().fill();
        buttonLevel.setWidth(bigButtonWidth);
        buttonLevel.setHeight(bigButtonHeight);
        buttonLevel.setPosition(
                Gdx.graphics.getWidth() / 2 - bigButtonWidth / 2,
                Gdx.graphics.getHeight() / 4 - bigButtonHeight / 2);
        buttonLevel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScreenMenuModeLevel(game));
            }
        });
        stage.addActor(buttonLevel);
    }

    private void initButtonEndless() {
        ImageButton buttonEndless = new ImageButton(
                AssetLoader.skin,
                "play");
        buttonEndless.getImageCell().expand().fill();
        buttonEndless.setWidth(bigButtonWidth);
        buttonEndless.setHeight(bigButtonHeight);
        buttonEndless.setPosition(
                Gdx.graphics.getWidth() / 2 - bigButtonWidth / 2,
                Gdx.graphics.getHeight() / 4 + bigButtonHeight);
        buttonEndless.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScreenModeEndless(game));
            }
        });
        stage.addActor(buttonEndless);
    }

    /**
     * menu with settings, about etc. in the top right corner
     */
    private void initializeMenu() {
        ImageButton buttonAbout = new ImageButton(
                AssetLoader.skin,
                "about"
        );
        buttonAbout.getImageCell().expand().fill();
        buttonAbout.setWidth(buttonWidth);
        buttonAbout.setHeight(buttonHeight);
        buttonAbout.setPosition(
                7 * Gdx.graphics.getWidth() / 8 - buttonWidth / 2,
                7 * Gdx.graphics.getHeight() / 8);
        buttonAbout.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScreenAbout(game));
            }
        });
        stage.addActor(buttonAbout);

        ImageButton buttonSettings = new ImageButton(
                AssetLoader.skin,
                "settings"
        );
        buttonSettings.getImageCell().expand().fill();
        buttonSettings.setWidth(buttonWidth);
        buttonSettings.setHeight(buttonHeight);
        buttonSettings.setPosition(
                7 * Gdx.graphics.getWidth() / 8 - buttonWidth / 2,
                6 * Gdx.graphics.getHeight() / 8);
        buttonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScreenSettings(game));
            }
        });
        stage.addActor(buttonSettings);

        ImageButton buttonShop = new ImageButton(
                AssetLoader.skin,
                "shop"
        );
        buttonShop.getImageCell().expand().fill();
        buttonShop.setWidth(buttonWidth);
        buttonShop.setHeight(buttonHeight);
        buttonShop.setPosition(
                7 * Gdx.graphics.getWidth() / 8 - buttonWidth / 2,
                5 * Gdx.graphics.getHeight() / 8);
        buttonShop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScreenShop(game));
            }
        });
        stage.addActor(buttonShop);
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
