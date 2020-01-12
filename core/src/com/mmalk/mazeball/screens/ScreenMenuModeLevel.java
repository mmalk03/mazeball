package com.mmalk.mazeball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mmalk.mazeball.helpers.AssetLoader;
import com.mmalk.mazeball.helpers.GameEngine;

//TODO: add stars to buttons instead of numbers
//TODO: create some entertaining levels
//TODO: show number of stars after victory

public class ScreenMenuModeLevel implements Screen {

    public static final String TAG = "ScreenMenuModeLevel";

    private final Stage stage;
    private final Game game;
    private final float gameWidth = Gdx.graphics.getWidth();
    private final float gameHeight = Gdx.graphics.getHeight();
    private final float buttonWidth = gameWidth / 6;
    private final float buttonHeight = gameWidth / 6;
    private final float buttonGap = (gameWidth - buttonWidth * GameEngine.BUTTONS_IN_ROW) / 5;

    private final float navBarButtonHeight = GameEngine.NAVIGATION_BAR_HEIGHT_PERCENT * gameHeight / 2;
    private final float navBarButtonWidth = GameEngine.NAVIGATION_BAR_HEIGHT_PERCENT * gameHeight / 2;
    private final float navBarButtonGap = GameEngine.NAVIGATION_BAR_HEIGHT_PERCENT * gameHeight / 4;

    private ScrollPane scrollPane;
    private Table table;
    private Table mainTable;

    private SpriteBatch batch;
    private Viewport viewport;
    private OrthographicCamera camera;

    public ScreenMenuModeLevel(final Game game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.viewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        this.viewport.apply();
        this.camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        this.camera.update();
        this.stage = new Stage(viewport, batch);

        initializeNavigationBar();
        initializeStars();
        initializeButtons();
        initializeInput();
    }

    private void initializeNavigationBar() {
        initExitButton();
    }

    private void initExitButton() {
        ImageButton exitButton = new ImageButton(
                AssetLoader.skin,
                "exit"
        );
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScreenMainMenu(game));
            }
        });
        exitButton.setSize(navBarButtonWidth, navBarButtonHeight);
        exitButton.setPosition(navBarButtonGap, gameHeight - navBarButtonHeight - navBarButtonGap);
        stage.addActor(exitButton);
    }

    private void initializeStars() {
    }

    private void initializeInput() {
        Gdx.input.setCatchBackKey(true);
        InputProcessor backButtonProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK)
                    game.setScreen(new ScreenMainMenu(game));
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
        mainTable = new Table();
        table = new Table();
        table.top().left();
        TextButton button;

        for (int i = 0; i < GameEngine.LEVELS_MODE_LEVEL; i++) {
            final int j = i;
            int stars = AssetLoader.getStars(i);
            button = new TextButton(
                    i + " [" + stars + "]",
                    AssetLoader.skin,
                    "default"
            );
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    try {
                        if (AssetLoader.isUnlocked(j)) {
                            game.setScreen(new ScreenModeLevel(game, j + 1));
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                }
            });

            if (i < GameEngine.BUTTONS_IN_ROW) {
                table.add(button).width(buttonWidth).height(buttonHeight).pad(buttonGap, buttonGap, buttonGap, 0f);
            } else {
                table.add(button).width(buttonWidth).height(buttonHeight).pad(0f, buttonGap, buttonGap, 0f);
            }
            if (i > 0 && i % GameEngine.BUTTONS_IN_ROW == GameEngine.BUTTONS_IN_ROW - 1) {
                table.row();
            }
        }

        scrollPane = new ScrollPane(table);

        mainTable.setFillParent(true);
        mainTable.add(scrollPane).fill().expand();
        mainTable.setY(mainTable.getY() - navBarButtonHeight);
        stage.addActor(mainTable);
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
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
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
        //skin.dispose();
        //atlas.dispose();
    }
}
