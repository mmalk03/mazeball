package com.mmalk.mazeball.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mmalk.mazeball.gameobjects.Coin;
import com.mmalk.mazeball.maputil.framework.GameMap;
import com.mmalk.mazeball.gameobjects.MainBall;
import com.mmalk.mazeball.gameobjects.MyBlock;
import com.mmalk.mazeball.gameobjects.Portal;
import com.mmalk.mazeball.gameobjects.WinningSquare;
import com.mmalk.mazeball.gameobjects.framework.GameObject;
import com.mmalk.mazeball.gameobjects.framework.GameObjectStationary;
import com.mmalk.mazeball.gameworld.framework.WorldAbstract;
import com.mmalk.mazeball.helpers.AssetLoader;
import com.mmalk.mazeball.helpers.GameEngine;
import com.mmalk.mazeball.maputil.ArrayPosition;
import com.mmalk.mazeball.maputil.gamemaplevel.GameMapLevelFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//TODO: make WorldModeLevel working
//TODO: refactor the onCollision thing

public class WorldModeLevel extends WorldAbstract {

    public static final String TAG = "WorldModeLevel";
    private final int currentLevel;
    private GameMap gameMap;
    private Vector2 mainBallPosition;
    private ArrayPosition mainBallArrayPosition;
    private Portal portal1 = null;
    private Portal portal2 = null;
    private boolean victory = false;
    private float gameHeight = Gdx.graphics.getHeight();
    private int moveCounter = 0;

    public WorldModeLevel(int currentLevel) {
        this.currentLevel = currentLevel;
        this.gameObjectStationaryListToRemove = new LinkedList<GameObjectStationary>();
        this.gameObjectStationaryList = new LinkedList<GameObjectStationary>();

        this.factory = new GameMapLevelFactory(currentLevel);
        this.gameMap = factory.getGameMapProvider().provideMap();

        addMapToGameObjectStationaryList(
                gameMap
        );
        gameState = GameState.READY;
    }

    private void checkForGameOver() {
        if (gameState.equals(GameState.RUNNING)) {
            Vector2 position = this.mainBall.getPosition();

            //leaving horizontal axis
            if (position.x > GameEngine.SCREEN_WIDTH / 2 - GameEngine.MAIN_BALL_WIDTH) {
                this.score = 0;
                handleGameOver();
                return;
            } else if (position.x < 0) {
                this.score = 0;
                handleGameOver();
                return;
            }

            //leaving vertical axis
            if (position.y > GameEngine.SCREEN_HEIGHT / 2 - GameEngine.MAIN_BALL_HEIGHT) {
                this.score = 0;
                handleGameOver();
            } else if (position.y < GameEngine.NAVIGATION_BAR_HEIGHT) {
                this.score = 0;
                handleGameOver();
            }
        }
    }

    private void checkForCollision() {
        for (GameObjectStationary gameObjectStationary :
                this.gameObjectStationaryList) {
            if (gameObjectStationary.collidesWith(this.mainBall)) {
                gameObjectStationary.onCollision(this);
            }
        }
    }

    //private void addMapToGameObjectStationaryList(int[][] map, int rows, int columns) {
    private void addMapToGameObjectStationaryList(GameMap gameMap) {
        GameMap.GameMapObject[][] map = gameMap.getMap();
        int rows = gameMap.getRows();
        int columns = gameMap.getColumns();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                switch (map[i][j]) {
                    case EMPTY_CELL:
                        continue;
                    case MAIN_BALL:
                        mainBallArrayPosition = new ArrayPosition(i, j);
                        this.mainBall = new MainBall(
                                j * GameEngine.MAIN_BALL_WIDTH,
                                i * GameEngine.MAIN_BALL_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT,
                                GameEngine.MAIN_BALL_WIDTH,
                                GameEngine.MAIN_BALL_HEIGHT,
                                GameEngine.MAIN_BALL_VELOCITY_DEFAULT,
                                GameEngine.MAIN_BALL_VELOCITY_DEFAULT,
                                GameEngine.MAIN_BALL_SCALE);
                        this.mainBallPosition = new Vector2(
                                j * GameEngine.MAIN_BALL_WIDTH,
                                i * GameEngine.MAIN_BALL_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT
                        );
                        break;
                    case MY_BLOCK:
                        this.gameObjectStationaryList.add(new MyBlock(
                                j * GameEngine.MY_BLOCK_WIDTH,
                                i * GameEngine.MY_BLOCK_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT,
                                GameEngine.MY_BLOCK_WIDTH,
                                GameEngine.MY_BLOCK_HEIGHT,
                                GameEngine.MY_BLOCK_SCALE));
                        break;
                    case PORTAL:
                        if (portal1 == null) {
                            portal1 = new Portal(
                                    j * GameEngine.MY_BLOCK_WIDTH,
                                    i * GameEngine.MY_BLOCK_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT,
                                    GameEngine.MY_BLOCK_WIDTH,
                                    GameEngine.MY_BLOCK_HEIGHT,
                                    GameEngine.PORTAL_SCALE);
                        } else {
                            portal2 = new Portal(
                                    j * GameEngine.MY_BLOCK_WIDTH,
                                    i * GameEngine.MY_BLOCK_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT,
                                    GameEngine.MY_BLOCK_WIDTH,
                                    GameEngine.MY_BLOCK_HEIGHT,
                                    GameEngine.PORTAL_SCALE);
                            this.portal1.setPortalSibling(portal2);
                            this.portal2.setPortalSibling(portal1);
                            this.gameObjectStationaryList.add(portal1);
                            this.gameObjectStationaryList.add(portal2);
                            this.portal1 = null;
                            this.portal2 = null;
                        }
                        break;
                    case WS_ALL:
                        this.gameObjectStationaryList.add(new WinningSquare(
                                j * GameEngine.WINNING_SQUARE_WIDTH,
                                i * GameEngine.WINNING_SQUARE_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT,
                                GameEngine.WINNING_SQUARE_WIDTH,
                                GameEngine.WINNING_SQUARE_HEIGHT,
                                GameEngine.WINNING_SQUARE_SCALE,
                                WinningSquare.Side.ALL));
                        break;
                    case WS_LEFT:
                        this.gameObjectStationaryList.add(new WinningSquare(
                                j * GameEngine.WINNING_SQUARE_WIDTH,
                                i * GameEngine.WINNING_SQUARE_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT,
                                GameEngine.WINNING_SQUARE_WIDTH,
                                GameEngine.WINNING_SQUARE_HEIGHT,
                                GameEngine.WINNING_SQUARE_SCALE,
                                WinningSquare.Side.LEFT));
                        break;
                    case WS_TOP:
                        this.gameObjectStationaryList.add(new WinningSquare(
                                j * GameEngine.WINNING_SQUARE_WIDTH,
                                i * GameEngine.WINNING_SQUARE_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT,
                                GameEngine.WINNING_SQUARE_WIDTH,
                                GameEngine.WINNING_SQUARE_HEIGHT,
                                GameEngine.WINNING_SQUARE_SCALE,
                                WinningSquare.Side.TOP));
                        break;
                    case WS_RIGHT:
                        this.gameObjectStationaryList.add(new WinningSquare(
                                j * GameEngine.WINNING_SQUARE_WIDTH,
                                i * GameEngine.WINNING_SQUARE_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT,
                                GameEngine.WINNING_SQUARE_WIDTH,
                                GameEngine.WINNING_SQUARE_HEIGHT,
                                GameEngine.WINNING_SQUARE_SCALE,
                                WinningSquare.Side.RIGHT));
                        break;
                    case WS_BOTTOM:
                        this.gameObjectStationaryList.add(new WinningSquare(
                                j * GameEngine.WINNING_SQUARE_WIDTH,
                                i * GameEngine.WINNING_SQUARE_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT,
                                GameEngine.WINNING_SQUARE_WIDTH,
                                GameEngine.WINNING_SQUARE_HEIGHT,
                                GameEngine.WINNING_SQUARE_SCALE,
                                WinningSquare.Side.BOTTOM));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void handleVictory() {
        gameState = GameState.VICTORY;
        mainBall.setVelocity(new Vector2(0, 0));
        mainBall.setPosition(new Vector2(0, -mainBall.getHeight()));
        checkForHighScore();
    }

    private void handleGameOver() {
        gameState = GameState.GAME_OVER;
        moveCounter = 0;
    }

    @Override
    public void onCollision(Coin coin) {
    }

    @Override
    public void onCollision(MyBlock myBlock) {
        moveCounter++;
        int sideOfCollision = getSideOfCollisionWithMainBall(myBlock);

        //"stick" mainBall to the correct side of myBlock
        mainBall.setVelocity(new Vector2(0, 0));
        switch (sideOfCollision) {
            case 0:
                mainBall.setPosition(
                        new Vector2(
                                myBlock.getPosition().x - mainBall.getWidth(),
                                myBlock.getPosition().y));
                break;
            case 1:
                mainBall.setPosition(
                        new Vector2(
                                myBlock.getPosition().x,
                                myBlock.getPosition().y - mainBall.getHeight()));
                break;
            case 2:
                mainBall.setPosition(
                        new Vector2(
                                myBlock.getPosition().x + myBlock.getWidth(),
                                myBlock.getPosition().y));
                break;
            case 3:
                mainBall.setPosition(
                        new Vector2(
                                myBlock.getPosition().x,
                                myBlock.getPosition().y + myBlock.getHeight()));
                break;
            default:
                break;
        }
    }

    @Override
    public void onCollision(Portal portal) {
        int sideOfCollision = getSideOfCollisionWithMainBall(portal);

        switch (sideOfCollision) {
            case 0:
                mainBall.setPosition(
                        new Vector2(
                                portal.getPortalSibling().getPosition().x + portal.getWidth(),
                                portal.getPortalSibling().getPosition().y));
                break;
            case 1:
                mainBall.setPosition(
                        new Vector2(
                                portal.getPortalSibling().getPosition().x,
                                portal.getPortalSibling().getPosition().y + portal.getHeight()));
                break;
            case 2:
                mainBall.setPosition(
                        new Vector2(
                                portal.getPortalSibling().getPosition().x - portal.getWidth(),
                                portal.getPortalSibling().getPosition().y));
                break;
            case 3:
                mainBall.setPosition(
                        new Vector2(
                                portal.getPortalSibling().getPosition().x,
                                portal.getPortalSibling().getPosition().y - portal.getHeight()));
                break;
            default:
                break;
        }
    }

    @Override
    public void onCollision(WinningSquare winningSquare) {
        moveCounter++;
        if (winningSquare.getSide() == WinningSquare.Side.ALL) {
            handleVictory();
            return;
        }

        int sideOfCollision = getSideOfCollisionWithMainBall(winningSquare);

        switch (sideOfCollision) {
            case 0:
                if (winningSquare.getSide() == WinningSquare.Side.LEFT) {
                    handleVictory();
                    return;
                } else {
                    mainBall.setPosition(
                            new Vector2(
                                    winningSquare.getPosition().x - mainBall.getWidth(),
                                    winningSquare.getPosition().y));
                }
                break;
            case 1:
                if (winningSquare.getSide() == WinningSquare.Side.TOP) {
                    handleVictory();
                    return;
                } else {
                    mainBall.setPosition(
                            new Vector2(
                                    winningSquare.getPosition().x,
                                    winningSquare.getPosition().y - mainBall.getHeight()));
                }
                break;
            case 2:
                if (winningSquare.getSide() == WinningSquare.Side.RIGHT) {
                    handleVictory();
                    return;
                } else {
                    mainBall.setPosition(
                            new Vector2(
                                    winningSquare.getPosition().x + winningSquare.getWidth(),
                                    winningSquare.getPosition().y));
                }
                break;
            case 3:
                if (winningSquare.getSide() == WinningSquare.Side.BOTTOM) {
                    handleVictory();
                    return;
                } else {
                    mainBall.setPosition(
                            new Vector2(
                                    winningSquare.getPosition().x,
                                    winningSquare.getPosition().y + winningSquare.getHeight()));
                }
                break;
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        switch (gameState) {
            case READY:
                updateReady(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case HIGHSCORE:
                updateHighscore(delta);
                break;
            case VICTORY:
                updateVictory(delta);
                break;
            case GAME_OVER:
                updateGameOver(delta);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackButtonClick() {

    }

    @Override
    public void onResetButtonClick() {
        if (gameState.equals(GameState.RUNNING)) {
            this.mainBall.setPosition(new Vector2(this.mainBallPosition));
            this.mainBall.setVelocity(new Vector2(0f, 0f));
            moveCounter = 0;
        }
    }

    @Override
    public void onSolveButtonClick() {
    }

    @Override
    public void onNewGameButtonClick() {
        this.setGameState(WorldAbstract.GameState.READY);
        this.mainBall.setPosition(new Vector2(this.mainBallPosition));
        this.mainBall.setVelocity(new Vector2(0f, 0f));
        moveCounter = 0;
    }

    @Override
    public void onCleanup() {
    }

    private void updateGameOver(float delta) {
    }

    private void checkForHighScore() {
        if (currentLevel < GameEngine.LEVELS_MODE_LEVEL - 1) {
            AssetLoader.setUnlocked(currentLevel + 1, true);
        }
        int stars;
        int shortestSolution = 10;//gameMap.getMovesToSolve();
        if (moveCounter <= shortestSolution) {
            stars = 3;
        } else if (moveCounter <= shortestSolution * 3 / 2) {
            stars = 2;
        } else {
            stars = 1;
        }
        Gdx.app.log(TAG, "stars: " + stars + " moveCounter: " + moveCounter);
        if (stars > AssetLoader.getStars(currentLevel)) {
            gameState = GameState.HIGHSCORE;
            AssetLoader.setStars(currentLevel, stars);
            Gdx.app.log(TAG, "HIGHSCORE! " + moveCounter + " stars: " + stars);
        }
    }

    private void updateHighscore(float delta) {

    }

    private void updateVictory(float delta) {

    }

    private void updateReady(float delta) {
        gameState = GameState.RUNNING;
    }

    private void updateRunning(float delta) {
        checkForCollision();
        checkForGameOver();
    }

    private int getSideOfCollisionWithMainBall(GameObject gameObject) {
        List distances = new ArrayList(4);
        //left collision
        distances.add(0, Math.abs(
                (this.mainBall.getPosition().x +
                        this.mainBall.getWidth()) -
                        gameObject.getPosition().x
        ));
        //top collision
        distances.add(1, Math.abs(
                (this.mainBall.getPosition().y +
                        this.mainBall.getHeight()) -
                        gameObject.getPosition().y
        ));
        //right collision
        distances.add(2, Math.abs(
                this.mainBall.getPosition().x -
                        (gameObject.getPosition().x +
                                gameObject.getWidth())
        ));
        //bottom collision
        distances.add(3, Math.abs(
                this.mainBall.getPosition().y -
                        (gameObject.getPosition().y +
                                gameObject.getHeight())
        ));

        //find from which side (of mainBall) the mainBall hit the gameObject
        return distances.indexOf(Collections.min(distances));
    }

    @Override
    public void onSwipeLeft() {
        if (gameState.equals(GameState.RUNNING)) {
            mainBall.onSwipeLeft();
        }
    }

    @Override
    public void onSwipeRight() {
        if (gameState.equals(GameState.RUNNING)) {
            mainBall.onSwipeRight();
        }
    }

    @Override
    public void onSwipeUp() {
        if (gameState.equals(GameState.RUNNING)) {
            mainBall.onSwipeUp();
        }
    }

    @Override
    public void onSwipeDown() {
        if (gameState.equals(GameState.RUNNING)) {
            mainBall.onSwipeDown();
        }
    }
}
