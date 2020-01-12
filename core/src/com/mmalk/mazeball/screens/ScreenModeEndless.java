package com.mmalk.mazeball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mmalk.mazeball.gameworld.RendererModeEndless;
import com.mmalk.mazeball.gameworld.WorldModeEndless;
import com.mmalk.mazeball.gameworld.framework.WorldAbstract;
import com.mmalk.mazeball.helpers.AssetLoader;
import com.mmalk.mazeball.helpers.DirectionGestureDetector;
import com.mmalk.mazeball.helpers.GameEngine;
import com.mmalk.mazeball.screens.framework.ScreenAbstract;

public class ScreenModeEndless extends ScreenAbstract {

    public static final String TAG = "ScreenModeEndless";

    private final Stage stage;
    private final Game game;
    private final float gameHeight = Gdx.graphics.getHeight();
    private final float gameWidth = Gdx.graphics.getWidth();
    private final float buttonHeight = GameEngine.NAVIGATION_BAR_HEIGHT_PERCENT * gameHeight / 2;
    private final float buttonWidth = GameEngine.NAVIGATION_BAR_HEIGHT_PERCENT * gameHeight / 2;
    private final float buttonGap = GameEngine.NAVIGATION_BAR_HEIGHT_PERCENT * gameHeight / 4;

    private final float bigButtonWidth = GameEngine.DEFAULT_GAME_OBJECT_SIZE * 5f;
    private final float bigButtonHeight = GameEngine.DEFAULT_GAME_OBJECT_SIZE * 5f;
    private final float bigButtonGap = GameEngine.DEFAULT_GAME_OBJECT_SIZE * 2.5f;

    private SpriteBatch batch;
    private Viewport viewport;
    private OrthographicCamera camera;

    private Label scoreLabel;

    private ImageButton gameOverResetButton;
    private Label gameOverLabel;

    public ScreenModeEndless(final Game game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.viewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        this.viewport.apply();
        this.camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        this.camera.update();
        this.stage = new Stage(viewport, batch);

        initializeNavigationBar();
        initializeInput();
        initializeGameOverDialog();

        this.runningTime = 0;
        this.worldAbstract = new WorldModeEndless();
        this.rendererAbstract = new RendererModeEndless(worldAbstract);
    }

    private void initializeInput() {
        Gdx.input.setCatchBackKey(true);
        InputProcessor backButtonProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    worldAbstract.onCleanup();
                    game.setScreen(new ScreenMainMenu(game));
                }
                return true;
            }
        };

        GestureDetector gestureDetector = new DirectionGestureDetector(new DirectionGestureDetector.DirectionListener() {
            @Override
            public void onLeft() {
                worldAbstract.onSwipeLeft();
            }

            @Override
            public void onRight() {
                worldAbstract.onSwipeRight();
            }

            @Override
            public void onUp() {
                worldAbstract.onSwipeUp();
            }

            @Override
            public void onDown() {
                worldAbstract.onSwipeDown();
            }

            @Override
            public void onClick() {

            }
        });

        InputMultiplexer inputMultiplexer = new InputMultiplexer(
                backButtonProcessor,
                gestureDetector,
                stage
        );

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void initializeNavigationBar() {
        initExitButton();
        initScoreLabel();
        initBackButton();
        initReplayButton();
        initSolveButton();
    }

    private void initSolveButton() {
        ImageButton solveButton = new ImageButton(
                AssetLoader.skin,
                "solve"
        );
        solveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                worldAbstract.onSolveButtonClick();
            }
        });
        //solveButton.setPosition(500, 25);
        solveButton.setSize(buttonWidth, buttonHeight);
        solveButton.setPosition(gameWidth - buttonGap - buttonWidth, gameHeight - buttonHeight - buttonGap);
        solveButton.getImageCell().expand().fill();
        stage.addActor(solveButton);
    }

    private void initReplayButton() {
        ImageButton replayButton = new ImageButton(
                AssetLoader.skin,
                "replay"
        );
        replayButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                worldAbstract.onResetButtonClick();
            }
        });
        replayButton.setSize(buttonWidth, buttonHeight);
        replayButton.setPosition(gameWidth - buttonWidth - 2 * buttonGap - buttonWidth, gameHeight - buttonHeight - buttonGap);
        replayButton.getImageCell().expand().fill();
        stage.addActor(replayButton);
    }

    private void initBackButton() {
        ImageButton backButton = new ImageButton(
                AssetLoader.skin,
                "back"
        );
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                worldAbstract.onBackButtonClick();
            }
        });
        backButton.setSize(buttonWidth, buttonHeight);
        backButton.setPosition(gameWidth - 3 * buttonWidth - 3 * buttonGap, gameHeight - buttonHeight - buttonGap);
        //backButton.setPosition(buttonGap + buttonWidth + buttonGap, gameHeight - buttonHeight - buttonGap);
        backButton.getImageCell().expand().fill();
        stage.addActor(backButton);
    }

    private void initScoreLabel() {
        this.scoreLabel = new Label(
                "0",
                AssetLoader.skin,
                "default"
        );
        this.scoreLabel.setSize(buttonWidth, buttonHeight);
        this.scoreLabel.setPosition(gameWidth / 2 - this.scoreLabel.getWidth() / 2, gameHeight - buttonHeight - buttonGap);
        stage.addActor(scoreLabel);
    }

    private void initExitButton() {
        ImageButton exitButton = new ImageButton(
                AssetLoader.skin,
                "exit"
        );
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                worldAbstract.onCleanup();
                game.setScreen(new ScreenMainMenu(game));
            }
        });
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition(buttonGap, gameHeight - buttonHeight - buttonGap);
        exitButton.getImageCell().expand().fill();
        stage.addActor(exitButton);
    }

    private void initializeGameOverDialog() {
        //initGameOverExitButton();
        initGameOverLabel();
        initGameOverResetButton();
    }

    private void initGameOverLabel() {
        gameOverLabel = new Label(
                "GAME OVER",
                AssetLoader.skin,
                "default"
        );
        //gameOverLabel.setSize(buttonWidth, buttonHeight);
        gameOverLabel.setPosition(gameWidth / 2 - gameOverLabel.getWidth() / 2, (gameHeight + buttonHeight) / 2 + buttonHeight);
        stage.addActor(gameOverLabel);
    }

    private void initGameOverResetButton() {
        gameOverResetButton = new ImageButton(
                AssetLoader.skin,
                "replay"
        );
        gameOverResetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //worldAbstract.onResetButtonClick();
                worldAbstract.onNewGameButtonClick();
            }
        });
        gameOverResetButton.setSize(bigButtonWidth, bigButtonHeight);
        gameOverResetButton.setPosition(gameWidth / 2 - bigButtonWidth / 2, (gameHeight - bigButtonHeight) / 2);
        stage.addActor(gameOverResetButton);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        runningTime += delta;
        worldAbstract.update(delta);
        this.scoreLabel.setText(String.valueOf(this.worldAbstract.getScore()));
        if (worldAbstract.getGameState().equals(WorldAbstract.GameState.GAME_OVER)) {
            gameOverResetButton.setVisible(true);
            gameOverLabel.setVisible(true);
        } else {
            rendererAbstract.render(runningTime);
            gameOverResetButton.setVisible(false);
            gameOverLabel.setVisible(false);
        }
        stage.act(delta);
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

    }
}
