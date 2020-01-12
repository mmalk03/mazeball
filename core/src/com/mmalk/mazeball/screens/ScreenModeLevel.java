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
import com.mmalk.mazeball.gameworld.RendererModeLevel;
import com.mmalk.mazeball.gameworld.WorldModeLevel;
import com.mmalk.mazeball.helpers.AssetLoader;
import com.mmalk.mazeball.helpers.DirectionGestureDetector;
import com.mmalk.mazeball.helpers.GameEngine;
import com.mmalk.mazeball.maputil.framework.GameMapProvider;
import com.mmalk.mazeball.maputil.gamemaplevel.GameMapLevelProvider;
import com.mmalk.mazeball.screens.framework.ScreenAbstract;

public class ScreenModeLevel extends ScreenAbstract {

    public static final String TAG = "ScreenModeLevel";

    private final int currentLevel;
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

    private Label levelLabel;

    private ImageButton gameOverResetButton;
    private Label gameOverLabel;

    private ImageButton victoryResetButton;
    private ImageButton victoryNextButton;
    private Label victoryLabel;

    private Label highscoreLabel;

    public ScreenModeLevel(final Game game, final int currentLevel) {
        this.currentLevel = currentLevel;
        this.game = game;
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.viewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        this.viewport.apply();
        this.camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        this.camera.update();
        this.stage = new Stage(viewport, batch);

        this.runningTime = 0;
        this.worldAbstract = new WorldModeLevel(currentLevel);
        this.rendererAbstract = new RendererModeLevel(worldAbstract);

        initializeNavigationBar();
        initializeInput();
        initializeGameOverDialog();
        initializeVictoryDialog();
        initializeHighscoreDialog();
    }

    private void initializeInput() {
        Gdx.input.setCatchBackKey(true);
        InputProcessor backButtonProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    worldAbstract.onCleanup();
                    game.setScreen(new ScreenMenuModeLevel(game));
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
        initLevelLabel();
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
        solveButton.setPosition(500, 25);
        solveButton.setSize(buttonWidth, buttonHeight);
        solveButton.setPosition(gameWidth - buttonGap - buttonWidth, gameHeight - buttonHeight - buttonGap);
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
        stage.addActor(replayButton);
    }

    private void initLevelLabel() {
        this.levelLabel = new Label(
                "Level " + currentLevel,
                AssetLoader.skin,
                "default"
        );
        this.levelLabel.setSize(buttonWidth, buttonHeight);
        this.levelLabel.setPosition(gameWidth / 2 - buttonWidth / 2, gameHeight - buttonHeight - buttonGap);
        stage.addActor(levelLabel);
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
                game.setScreen(new ScreenMenuModeLevel(game));
            }
        });
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition(buttonGap, gameHeight - buttonHeight - buttonGap);
        stage.addActor(exitButton);
    }

    private void initializeGameOverDialog() {
        initGameOverLabel();
        initGameOverResetButton();
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

    private void initGameOverLabel() {
        gameOverLabel = new Label(
                "GAME OVER",
                AssetLoader.skin,
                "default"
        );
        gameOverLabel.setPosition(gameWidth / 2 - gameOverLabel.getWidth() / 2, (gameHeight + buttonHeight) / 2 + buttonHeight);
        stage.addActor(gameOverLabel);
    }

    private void initializeVictoryDialog() {
        initVictoryLabel();
        initVictoryResetButton();
        initVictoryNextButton();
    }

    private void initVictoryNextButton() {
        victoryNextButton = new ImageButton(
                AssetLoader.skin,
                "play"
        );
        victoryNextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (currentLevel < GameEngine.LEVELS_MODE_LEVEL - 1) {
                    worldAbstract.onCleanup();
                    game.setScreen(new ScreenModeLevel(game, currentLevel + 1));
                } else {
                    worldAbstract.onCleanup();
                    game.setScreen(new ScreenMenuModeLevel(game));
                }
            }
        });
        victoryNextButton.setSize(bigButtonWidth, bigButtonHeight);
        victoryNextButton.setPosition(gameWidth / 2 + bigButtonGap / 2, (gameHeight - bigButtonHeight) / 2);
        stage.addActor(victoryNextButton);
    }

    private void initVictoryResetButton() {
        victoryResetButton = new ImageButton(
                AssetLoader.skin,
                "replay"
        );
        victoryResetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                worldAbstract.onNewGameButtonClick();
            }
        });
        victoryResetButton.setSize(bigButtonWidth, bigButtonHeight);
        victoryResetButton.setPosition(gameWidth / 2 - bigButtonGap / 2 - bigButtonWidth, (gameHeight - bigButtonHeight) / 2);
        stage.addActor(victoryResetButton);
    }

    private void initVictoryLabel() {
        victoryLabel = new Label(
                "VICTORY",
                AssetLoader.skin,
                "default"
        );
        victoryLabel.setPosition(gameWidth / 2 - victoryLabel.getWidth() / 2, (gameHeight + buttonHeight) / 2 + buttonHeight);
        stage.addActor(victoryLabel);
    }

    private void initializeHighscoreDialog() {
        initHighscoreLabel();
    }

    private void initHighscoreLabel() {
        highscoreLabel = new Label(
                "HIGHSCORE",
                AssetLoader.skin,
                "default"
        );
        highscoreLabel.setPosition(gameWidth / 2 - highscoreLabel.getWidth() / 2, (gameHeight + buttonHeight) / 2 + buttonHeight);
        stage.addActor(highscoreLabel);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        runningTime += delta;
        worldAbstract.update(delta);
        switch (worldAbstract.getGameState()) {
            case GAME_OVER:
                renderGameOver();
                break;
            case VICTORY:
                renderVictory();
                break;
            case HIGHSCORE:
                renderHighscore();
                break;
            default:
                renderRunning();
                break;
        }

        stage.act(delta);
        stage.draw();
    }

    private void renderRunning() {
        rendererAbstract.render(runningTime);
        gameOverResetButton.setVisible(false);
        gameOverLabel.setVisible(false);
        victoryResetButton.setVisible(false);
        victoryNextButton.setVisible(false);
        victoryLabel.setVisible(false);
        highscoreLabel.setVisible(false);
    }

    private void renderHighscore() {
        victoryResetButton.setVisible(true);
        victoryNextButton.setVisible(true);
        highscoreLabel.setVisible(true);
    }

    private void renderVictory() {
        victoryResetButton.setVisible(true);
        victoryNextButton.setVisible(true);
        victoryLabel.setVisible(true);
    }

    private void renderGameOver() {
        gameOverResetButton.setVisible(true);
        gameOverLabel.setVisible(true);
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
