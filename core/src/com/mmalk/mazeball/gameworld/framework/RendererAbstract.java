package com.mmalk.mazeball.gameworld.framework;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mmalk.mazeball.gameobjects.Coin;
import com.mmalk.mazeball.gameobjects.MainBall;
import com.mmalk.mazeball.gameobjects.MyBlock;
import com.mmalk.mazeball.gameobjects.Portal;
import com.mmalk.mazeball.gameobjects.WinningSquare;
import com.mmalk.mazeball.gameobjects.framework.GameObject;
import com.mmalk.mazeball.gameobjects.framework.GameObjectStationary;
import com.mmalk.mazeball.gameworld.WorldModeEndless;
import com.mmalk.mazeball.helpers.AssetLoader;
import com.mmalk.mazeball.helpers.GameEngine;
import com.mmalk.mazeball.maputil.framework.GameMapSolution;
import com.mmalk.mazeball.maputil.framework.GameMapSolutionInterpreter;
import com.mmalk.mazeball.maputil.framework.GameMapSolutionRenderer;

import java.util.List;

public abstract class RendererAbstract {
    //texture regions count
    private static final int TR_COUNT = 1;
    //indices of elements in array with TextureRegion
    private static final int INDEX_TR_BACKGROUND = 0;

    //animation count
    private static final int ANIM_COUNT = 5;
    //indices of elements in array with Animation
    private static final int INDEX_ANIM_MAIN_BALL = 0;
    private static final int INDEX_ANIM_MY_BLOCK = 1;
    private static final int INDEX_ANIM_WINNING_SQUARE = 2;
    private static final int INDEX_ANIM_PORTAL = 3;
    private static final int INDEX_ANIM_COIN = 4;

    protected WorldAbstract gameWorld;
    protected OrthographicCamera orthographicCamera;
    protected ShapeRenderer shapeRenderer;
    protected SpriteBatch spriteBatch;
    protected float runningTime;
    protected List<GameObjectStationary> gameObjectStationaryList;
    protected MainBall mainBall;
    protected TextureRegion[] textureRegions;
    protected Animation[] animations;

    protected GameMapSolutionRenderer gameMapSolutionRenderer;
    protected GameMapSolutionInterpreter gameMapSolutionInterpreter;

    public RendererAbstract(WorldAbstract gameWorld) {
        this.gameWorld = gameWorld;

        initUtil();
        initGameObjects();
        initAssets();
    }

    public OrthographicCamera getOrthographicCamera() {
        return orthographicCamera;
    }

    public void setOrthographicCamera(OrthographicCamera orthographicCamera) {
        this.orthographicCamera = orthographicCamera;
    }

    protected void initUtil() {
        this.orthographicCamera = new OrthographicCamera();
        this.orthographicCamera.setToOrtho(true, GameEngine.SCREEN_WIDTH / 2, GameEngine.SCREEN_HEIGHT / 2);

        this.spriteBatch = new SpriteBatch();
        this.spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setProjectionMatrix(orthographicCamera.combined);
    }

    protected void initAssets() {
        this.textureRegions = new TextureRegion[TR_COUNT];
        textureRegions[INDEX_TR_BACKGROUND] = AssetLoader.textureRegionBackground;
        this.animations = new Animation[ANIM_COUNT];
        animations[INDEX_ANIM_MAIN_BALL] = AssetLoader.animationMainBall;
        animations[INDEX_ANIM_MY_BLOCK] = AssetLoader.animationMyBlock;
        animations[INDEX_ANIM_WINNING_SQUARE] = AssetLoader.animationWinningSquare;
        animations[INDEX_ANIM_PORTAL] = AssetLoader.animationPortal;
        animations[INDEX_ANIM_COIN] = AssetLoader.animationCoin;
    }

    protected void initGameObjects() {
        this.gameObjectStationaryList = gameWorld.getGameObjectStationaryList();
        this.mainBall = gameWorld.getMainBall();

        this.gameMapSolutionRenderer = gameWorld.getFactory().getGameMapSolutionRenderer(shapeRenderer);
        this.gameMapSolutionInterpreter = gameWorld.getFactory().getGameMapSolutionInterpreter();
    }

    public abstract void render(float runningTime);

    public void render(MainBall mainBall) {
        drawGameObject(mainBall, this.animations[INDEX_ANIM_MAIN_BALL]);
    }

    public void render(MyBlock myBlock) {
        drawGameObject(myBlock, this.animations[INDEX_ANIM_MY_BLOCK]);
    }

    public void render(WinningSquare winningSquare) {
        drawGameObject(winningSquare, this.animations[INDEX_ANIM_WINNING_SQUARE]);
    }

    public void render(Portal portal) {
        drawGameObject(portal, this.animations[INDEX_ANIM_PORTAL]);
    }

    public void render(Coin coin) {
        drawGameObject(coin, this.animations[INDEX_ANIM_COIN]);
    }

    protected void drawGameObject(GameObject gameObject, Animation animation) {
        spriteBatch.draw((TextureRegion) animation.getKeyFrame(this.runningTime),
                gameObject.getPosition().x,
                gameObject.getPosition().y,
                gameObject.getWidth() / 2,
                gameObject.getHeight() / 2,
                gameObject.getWidth(),
                gameObject.getHeight(),
                gameObject.getScale(),
                gameObject.getScale(),
                gameObject.getRotation());
    }

    protected void renderGameObjects() {
        spriteBatch.enableBlending();
        for (GameObjectStationary gameObjectStationary :
                this.gameObjectStationaryList) {
            gameObjectStationary.render(this);
        }
        render(this.mainBall);
    }

    protected void renderBackground() {
        spriteBatch.disableBlending();
        spriteBatch.draw(this.textureRegions[INDEX_TR_BACKGROUND],
                0, 0,
                GameEngine.SCREEN_WIDTH / 2,
                GameEngine.SCREEN_HEIGHT / 2);
    }

    protected void renderNavigationBarBackground() {
        shapeRenderer.setColor(0f, 0f, 0f, 1);
        shapeRenderer.rect(0, 0,
                GameEngine.SCREEN_WIDTH / 2,
                GameEngine.NAVIGATION_BAR_HEIGHT);
    }

    protected void renderGameOverBackground() {
        if (gameWorld.getGameState() == WorldModeEndless.GameState.GAME_OVER) {
            shapeRenderer.setColor(33 / 255f, 56 / 255f, 94 / 255f, 1);
            shapeRenderer.rect(GameEngine.SCREEN_WIDTH / 8, GameEngine.SCREEN_HEIGHT / 4 - GameEngine.SCREEN_HEIGHT / 16,
                    GameEngine.SCREEN_WIDTH / 4,
                    GameEngine.SCREEN_HEIGHT / 8);
        }
    }

    protected void renderSolution() {
        GameMapSolution gameMapSolution = gameWorld.getGameMapSolution();
        if (gameMapSolution != null) {
            gameMapSolutionRenderer.render(
                    gameWorld.getCurrentGameMap(),
                    gameMapSolution,
                    gameMapSolutionInterpreter
            );
        }
    }
}
