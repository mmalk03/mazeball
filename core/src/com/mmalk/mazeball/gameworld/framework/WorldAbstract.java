package com.mmalk.mazeball.gameworld.framework;


import com.mmalk.mazeball.gameobjects.Coin;
import com.mmalk.mazeball.gameobjects.MainBall;
import com.mmalk.mazeball.gameobjects.MyBlock;
import com.mmalk.mazeball.gameobjects.Portal;
import com.mmalk.mazeball.gameobjects.WinningSquare;
import com.mmalk.mazeball.gameobjects.framework.GameObject;
import com.mmalk.mazeball.gameobjects.framework.GameObjectStationary;
import com.mmalk.mazeball.gameobjects.framework.Swipeable;
import com.mmalk.mazeball.maputil.framework.GameMap;
import com.mmalk.mazeball.maputil.framework.GameMapSolution;
import com.mmalk.mazeball.maputil.framework.SolvableGameMapFactory;
import com.mmalk.mazeball.maputil.gamemap1pop.GameMap1POPFactory;

import java.util.List;

public abstract class WorldAbstract implements Swipeable {

    protected List<GameObjectStationary> gameObjectStationaryListToRemove;
    protected List<GameObjectStationary> gameObjectStationaryList;
    protected MainBall mainBall;
    protected int score;
    protected GameState gameState;
    protected SolvableGameMapFactory factory;
    protected GameMap currentGameMap;
    protected GameMapSolution gameMapSolution = null;

    public GameMap getCurrentGameMap() {
        return currentGameMap;
    }

    public void setCurrentGameMap(GameMap currentGameMap) {
        this.currentGameMap = currentGameMap;
    }

    public GameMapSolution getGameMapSolution() {
        return gameMapSolution;
    }

    public void setGameMapSolution(GameMapSolution gameMapSolution) {
        this.gameMapSolution = gameMapSolution;
    }

    public SolvableGameMapFactory getFactory() {
        return factory;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getScore() {
        return score;
    }

    public abstract void onCollision(Coin coin);

    public abstract void onCollision(MyBlock myBlock);

    public abstract void onCollision(Portal portal);

    public abstract void onCollision(WinningSquare winningSquare);

    public MainBall getMainBall() {
        return mainBall;
    }

    public void setMainBall(MainBall mainBall) {
        this.mainBall = mainBall;
    }

    public void update(float delta) {
        for (GameObject gameObject : gameObjectStationaryList) {
            gameObject.update(delta);
        }
        if (mainBall != null) {
            mainBall.update(delta);
        }
    }

    public List<GameObjectStationary> getGameObjectStationaryList() {
        return gameObjectStationaryList;
    }

    public void setGameObjectStationaryList(List<GameObjectStationary> gameObjectStationaryList) {
        this.gameObjectStationaryList = gameObjectStationaryList;
    }

    public abstract void onBackButtonClick();

    public abstract void onResetButtonClick();

    public abstract void onSolveButtonClick();

    public abstract void onNewGameButtonClick();

    public abstract void onCleanup();

    public enum GameState {
        RUNNING,
        GAME_OVER,
        READY,
        HIGHSCORE,
        VICTORY
    }
}
