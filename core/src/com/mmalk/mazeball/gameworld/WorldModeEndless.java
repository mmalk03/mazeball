package com.mmalk.mazeball.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mmalk.mazeball.gameobjects.Coin;
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
import com.mmalk.mazeball.maputil.GameMapGeneratorThread;
import com.mmalk.mazeball.maputil.framework.GameMap;
import com.mmalk.mazeball.maputil.framework.GameMap.GameMapObject;
import com.mmalk.mazeball.maputil.gamemap1pop.GameMap1POPEndlessFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

public class WorldModeEndless extends WorldAbstract {

    public static final String TAG = "WorldModeEndless";
    private Portal portal1 = null;
    private Portal portal2 = null;
    private boolean victory = false;
    private WinningSquare winningSquareToRemove;
    private int counter = 0;
    private int andrzej = 0;
    private List<GameObject> gameObjectListToRemove = new LinkedList<GameObject>();
    private ArrayBlockingQueue<GameMap> gameMapQueue;
    private ArrayPosition startMainBallPosition;
    private Stack<Vector2> mainBallPositions = new Stack<Vector2>();
    private GameMapGeneratorThread gameMapGeneratorThread;

    public WorldModeEndless() {
        this.factory = new GameMap1POPEndlessFactory();
        this.gameObjectStationaryListToRemove = new LinkedList<GameObjectStationary>();
        this.gameObjectStationaryList = new LinkedList<GameObjectStationary>();
        this.mainBall = new MainBall(
                0f, 0f,
                GameEngine.MAIN_BALL_WIDTH,
                GameEngine.MAIN_BALL_HEIGHT,
                GameEngine.MAIN_BALL_VELOCITY_DEFAULT,
                GameEngine.MAIN_BALL_VELOCITY_DEFAULT,
                GameEngine.MAIN_BALL_SCALE
        );
        gameState = GameState.READY;
        gameMapGeneratorThread = GameMapGeneratorThread.getInstance(
                factory.getGameMapProvider()
        );
        gameMapQueue = gameMapGeneratorThread.getGameMapQueue();
    }

    private int getSideOfCollisionWithMainBall(GameObject gameObject) {
        ArrayList<Float> distances = new ArrayList<Float>(4);
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
    public void onCollision(Coin coin) {
        Gdx.app.log(TAG, "point!");
        this.gameObjectStationaryListToRemove.add(coin);
        score++;
    }

    @Override
    public void onCollision(MyBlock myBlock) {
        int sideOfCollision = getSideOfCollisionWithMainBall(myBlock);

        //"stick" mainBall to the correct side of myBlock
        //add to mainBallPositions previous mainBall position
        mainBall.setVelocity(new Vector2(0, 0));
        switch (sideOfCollision) {
            case 0:
                onCollisionLeft(myBlock);
                break;
            case 1:
                onCollisionTop(myBlock);
                break;
            case 2:
                onCollisionRight(myBlock);
                break;
            case 3:
                onCollisionBottom(myBlock);
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
                onCollisionLeft(portal);
                break;
            case 1:
                onCollisionTop(portal);
                break;
            case 2:
                onCollisionRight(portal);
                break;
            case 3:
                onCollisionBottom(portal);
                break;
            default:
                break;
        }
    }

    @Override
    public void onCollision(WinningSquare winningSquare) {
        //trying to call resetGame() from here results in ConcurrentModificationException, hence this boolean was introduced
        victory = true;
        this.winningSquareToRemove = winningSquare;
        mainBall.setPosition(
                new Vector2(
                        mainBall.getPosition().x,
                        winningSquare.getPosition().y - mainBall.getHeight()
                )
        );
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
        if (gameState.equals(GameState.RUNNING)) {
            if (mainBallPositions.size() > 1 && !mainBall.isMoving()) {
                mainBallPositions.pop();
                mainBall.setPosition(new Vector2(
                        mainBallPositions.peek().x,
                        mainBallPositions.peek().y));
            }
        }
    }

    @Override
    public void onResetButtonClick() {
        if (gameState.equals(GameState.RUNNING)) {
            if (!mainBall.isMoving()) {
                Vector2 vector2 = new Vector2(
                        startMainBallPosition.getColumn() * mainBall.getWidth(),
                        startMainBallPosition.getRow() * mainBall.getHeight() + GameEngine.NAVIGATION_BAR_HEIGHT);
                mainBall.setPosition(vector2);
                mainBallPositions.clear();
                mainBallPositions.push(new Vector2(
                        vector2.x,
                        vector2.y
                ));
            }
        }
    }

    @Override
    public void onSolveButtonClick() {
        if (gameState.equals(GameState.RUNNING)) {
            gameMapSolution = currentGameMap.getGameMapSolution();
        }
    }

    @Override
    public void onNewGameButtonClick() {
        this.setGameState(WorldAbstract.GameState.READY);
    }

    @Override
    public void onCleanup() {
    }

    private void onCollisionLeft(MyBlock myBlock) {
        Vector2 vector2 = new Vector2(
                myBlock.getPosition().x - mainBall.getWidth(),
                myBlock.getPosition().y);
        mainBall.setPosition(
                vector2);
        if (!vector2.equals(mainBallPositions.peek())) {
            mainBallPositions.push(new Vector2(
                    vector2.x,
                    vector2.y));
        }
    }

    private void onCollisionTop(MyBlock myBlock) {
        Vector2 vector2 = new Vector2(
                myBlock.getPosition().x,
                myBlock.getPosition().y - mainBall.getHeight());
        mainBall.setPosition(
                vector2);
        if (!vector2.equals(mainBallPositions.peek())) {
            mainBallPositions.push(new Vector2(
                    vector2.x,
                    vector2.y));
        }
    }

    private void onCollisionRight(MyBlock myBlock) {
        Vector2 vector2 = new Vector2(
                myBlock.getPosition().x + myBlock.getWidth(),
                myBlock.getPosition().y);
        mainBall.setPosition(
                vector2);
        if (!vector2.equals(mainBallPositions.peek())) {
            mainBallPositions.push(new Vector2(
                    vector2.x,
                    vector2.y));
        }
    }

    private void onCollisionBottom(MyBlock myBlock) {
        Vector2 vector2 = new Vector2(
                myBlock.getPosition().x,
                myBlock.getPosition().y + myBlock.getHeight());
        mainBall.setPosition(
                vector2);
        if (!vector2.equals(mainBallPositions.peek())) {
            mainBallPositions.push(new Vector2(
                    vector2.x,
                    vector2.y));
        }
    }

    private void onCollisionLeft(Portal portal) {
        mainBall.setPosition(
                new Vector2(
                        portal.getPortalSibling().getPosition().x + portal.getWidth(),
                        portal.getPortalSibling().getPosition().y));
    }

    private void onCollisionTop(Portal portal) {
        mainBall.setPosition(
                new Vector2(
                        portal.getPortalSibling().getPosition().x,
                        portal.getPortalSibling().getPosition().y + mainBall.getHeight()));
    }

    private void onCollisionRight(Portal portal) {
        mainBall.setPosition(
                new Vector2(
                        portal.getPortalSibling().getPosition().x - portal.getWidth(),
                        portal.getPortalSibling().getPosition().y));
    }

    private void onCollisionBottom(Portal portal) {
        mainBall.setPosition(
                new Vector2(
                        portal.getPortalSibling().getPosition().x,
                        portal.getPortalSibling().getPosition().y - portal.getHeight()));
    }

    private void updateGameOver(float delta) {
    }

    private void updateHighscore(float delta) {
    }

    private void updateVictory(float delta) {
        //make the scrolling slower
        if (++andrzej % 2 != 0) {
            return;
        }
        counter++;
        if (counter < GameEngine.BIG_MAP_SIZE_ROWS + 1) {
            moveAllUp(this.mainBall.getHeight());
            if (counter >= GameEngine.BIG_MAP_SIZE_ROWS - 1) {
                this.mainBall.setPosition(
                        new Vector2(
                                this.mainBall.getPosition().x,
                                this.mainBall.getPosition().y + this.mainBall.getHeight()));
            }
        } else {
            counter = 0;
            gameState = GameState.RUNNING;
            mainBall.setPosition(
                    new Vector2(
                            mainBall.getPosition().x,
                            GameEngine.NAVIGATION_BAR_HEIGHT
                    )
            );
            gameObjectListToRemove.clear();
            for (GameObject gameObject :
                    gameObjectStationaryList) {
                if (gameObject.getPosition().y < GameEngine.NAVIGATION_BAR_HEIGHT) {
                    gameObjectListToRemove.add(gameObject);
                }
            }
            gameObjectStationaryList.removeAll(gameObjectListToRemove);
        }
    }

    private void updateReady(float delta) {
        score = 0;
        mainBallPositions.clear();
        this.gameObjectStationaryList.clear();
        gameMapSolution = null;
        try {
            addMapToGameObjectStationaryList(currentGameMap = gameMapQueue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateRunning(float delta) {
        checkForCollision();
        checkForGameOver();
    }

    @Override
    public void onSwipeLeft() {
        mainBall.onSwipeLeft();
    }

    @Override
    public void onSwipeRight() {
        mainBall.onSwipeRight();
    }

    @Override
    public void onSwipeUp() {
        mainBall.onSwipeUp();
    }

    @Override
    public void onSwipeDown() {
        mainBall.onSwipeDown();
    }

    private void checkForGameOver() {
        Vector2 position = this.mainBall.getPosition();

        if (position.x > GameEngine.SCREEN_WIDTH / 2 ||
                position.x < -GameEngine.MAIN_BALL_WIDTH ||
                position.y > GameEngine.SCREEN_HEIGHT / 2 ||
                position.y < GameEngine.NAVIGATION_BAR_HEIGHT / 2 - GameEngine.MAIN_BALL_HEIGHT) {
            handleGameOver();
        }
    }

    private void handleGameOver() {
        gameState = GameState.GAME_OVER;
        checkForHighscore();
    }

    private void checkForHighscore() {
        if (score > AssetLoader.getHighScore()) {
            AssetLoader.setHighScore(score);
            gameState = GameState.HIGHSCORE;
        }
    }

    private void checkForCollision() {
        for (GameObjectStationary gameObjectStationary :
                this.gameObjectStationaryList) {
            if (gameObjectStationary.collidesWith(this.mainBall)) {
                gameObjectStationary.onCollision(this);
            }
        }
        removeCoins();
        if (victory) {
            victory = false;
            handleVictory();
        }
    }

    private void handleVictory() {
        gameState = GameState.VICTORY;
        gameMapSolution = null;
        startMainBallPosition = new ArrayPosition(0, (int) (winningSquareToRemove.getPosition().x / winningSquareToRemove.getWidth()));
        mainBallPositions.clear();
        mainBallPositions.push(new Vector2(winningSquareToRemove.getPosition().x, GameEngine.NAVIGATION_BAR_HEIGHT));
        this.gameObjectStationaryList.remove(winningSquareToRemove);
        this.mainBall.setVelocity(new Vector2(0f, 0f));
        try {
            addNewMapToGameObjectStationaryList(currentGameMap = gameMapQueue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void removeCoins() {
        this.gameObjectStationaryList.removeAll(this.gameObjectStationaryListToRemove);
    }

    private void addMapToGameObjectStationaryList(GameMap gameMap) {
        GameMapObject[][] map = gameMap.getMap();
        int rows = gameMap.getRows();
        int columns = gameMap.getColumns();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                switch (map[i][j]) {
                    case EMPTY_CELL:
                        continue;
                    case MAIN_BALL:
                        Vector2 vector2 = new Vector2(
                                j * GameEngine.MAIN_BALL_WIDTH,
                                i * GameEngine.MAIN_BALL_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT);
                        this.mainBall.setPosition(vector2);
                        this.mainBall.setVelocity(new Vector2(0, 0));
                        startMainBallPosition = new ArrayPosition(i, j);
                        mainBallPositions.push(new Vector2(
                                vector2.x,
                                vector2.y));
                        gameState = GameState.RUNNING;
                        break;
                    case MY_BLOCK:
                        this.gameObjectStationaryList.add(new MyBlock(
                                j * GameEngine.MY_BLOCK_WIDTH,
                                i * GameEngine.MY_BLOCK_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT,
                                GameEngine.MY_BLOCK_WIDTH,
                                GameEngine.MY_BLOCK_HEIGHT,
                                GameEngine.MAIN_BALL_SCALE));
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
                    case COIN:
                        this.gameObjectStationaryList.add(new Coin(
                                j * GameEngine.COIN_WIDTH,
                                i * GameEngine.COIN_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT,
                                GameEngine.COIN_WIDTH,
                                GameEngine.COIN_HEIGHT,
                                GameEngine.COIN_SCALE));
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

    private void addNewMapToGameObjectStationaryList(GameMap gameMap) {
        GameMapObject[][] map = gameMap.getMap();
        int rows = gameMap.getRows();
        int columns = gameMap.getColumns();

        float offset = rows * GameEngine.MY_BLOCK_HEIGHT;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                switch (map[i][j]) {
                    case EMPTY_CELL:
                        continue;
                    case MAIN_BALL:
                        break;
                    case MY_BLOCK:
                        this.gameObjectStationaryList.add(new MyBlock(
                                j * GameEngine.MY_BLOCK_WIDTH,
                                i * GameEngine.MY_BLOCK_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT + offset,
                                GameEngine.MY_BLOCK_WIDTH,
                                GameEngine.MY_BLOCK_HEIGHT,
                                GameEngine.MAIN_BALL_SCALE));
                        break;
                    case PORTAL:
                        if (portal1 == null) {
                            portal1 = new Portal(
                                    j * GameEngine.MY_BLOCK_WIDTH,
                                    i * GameEngine.MY_BLOCK_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT + offset,
                                    GameEngine.MY_BLOCK_WIDTH,
                                    GameEngine.MY_BLOCK_HEIGHT,
                                    GameEngine.PORTAL_SCALE);
                        } else {
                            portal2 = new Portal(
                                    j * GameEngine.MY_BLOCK_WIDTH,
                                    i * GameEngine.MY_BLOCK_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT + offset,
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
                    case COIN:
                        this.gameObjectStationaryList.add(new Coin(
                                j * GameEngine.COIN_WIDTH,
                                i * GameEngine.COIN_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT + offset,
                                GameEngine.COIN_WIDTH,
                                GameEngine.COIN_HEIGHT,
                                GameEngine.COIN_SCALE));
                        break;
                    case WS_ALL:
                        this.gameObjectStationaryList.add(new WinningSquare(
                                j * GameEngine.WINNING_SQUARE_WIDTH,
                                i * GameEngine.WINNING_SQUARE_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT + offset,
                                GameEngine.WINNING_SQUARE_WIDTH,
                                GameEngine.WINNING_SQUARE_HEIGHT,
                                GameEngine.WINNING_SQUARE_SCALE,
                                WinningSquare.Side.ALL));
                        break;
                    case WS_LEFT:
                        this.gameObjectStationaryList.add(new WinningSquare(
                                j * GameEngine.WINNING_SQUARE_WIDTH,
                                i * GameEngine.WINNING_SQUARE_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT + offset,
                                GameEngine.WINNING_SQUARE_WIDTH,
                                GameEngine.WINNING_SQUARE_HEIGHT,
                                GameEngine.WINNING_SQUARE_SCALE,
                                WinningSquare.Side.LEFT));
                        break;
                    case WS_TOP:
                        this.gameObjectStationaryList.add(new WinningSquare(
                                j * GameEngine.WINNING_SQUARE_WIDTH,
                                i * GameEngine.WINNING_SQUARE_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT + offset,
                                GameEngine.WINNING_SQUARE_WIDTH,
                                GameEngine.WINNING_SQUARE_HEIGHT,
                                GameEngine.WINNING_SQUARE_SCALE,
                                WinningSquare.Side.TOP));
                        break;
                    case WS_RIGHT:
                        this.gameObjectStationaryList.add(new WinningSquare(
                                j * GameEngine.WINNING_SQUARE_WIDTH,
                                i * GameEngine.WINNING_SQUARE_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT + offset,
                                GameEngine.WINNING_SQUARE_WIDTH,
                                GameEngine.WINNING_SQUARE_HEIGHT,
                                GameEngine.WINNING_SQUARE_SCALE,
                                WinningSquare.Side.RIGHT));
                        break;
                    case WS_BOTTOM:
                        this.gameObjectStationaryList.add(new WinningSquare(
                                j * GameEngine.WINNING_SQUARE_WIDTH,
                                i * GameEngine.WINNING_SQUARE_HEIGHT + GameEngine.NAVIGATION_BAR_HEIGHT + offset,
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

    private void moveAllUp(float howFarUp) {
        for (GameObject gameObject :
                this.gameObjectStationaryList) {
            gameObject.setPosition(new Vector2(
                    gameObject.getPosition().x,
                    gameObject.getPosition().y - howFarUp
            ));
        }
        this.mainBall.setVelocity(new Vector2(0, 0));
        this.mainBall.setPosition(
                new Vector2(
                        this.mainBall.getPosition().x,
                        this.mainBall.getPosition().y - howFarUp
                )
        );
    }

    private void log(String message) {
        Gdx.app.log(TAG, message);
    }
}
