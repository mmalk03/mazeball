package com.mmalk.mazeball.maputil;

import com.mmalk.mazeball.maputil.framework.GameMap;
import com.mmalk.mazeball.maputil.framework.GameMapProvider;

import java.util.concurrent.ArrayBlockingQueue;

public class GameMapGeneratorThread extends Thread {

    public static final String TAG = "GameMapGeneratorThread";
    private static GameMapGeneratorThread instance = null;
    private static ArrayBlockingQueue<GameMap> gameMapQueue = new ArrayBlockingQueue<GameMap>(3);
    private GameMapProvider gameMapProvider;

    private GameMapGeneratorThread(GameMapProvider gameMapProvider) {
        this.gameMapProvider = gameMapProvider;
    }

    public static GameMapGeneratorThread getInstance(GameMapProvider gameMapProvider) {
        if (instance == null) {
            instance = new GameMapGeneratorThread(gameMapProvider);
        }
        return instance;
    }

    @Override
    public void run() {
        GameMap gameMap;
        while (true) {
            gameMap = gameMapProvider.provideMap();
            try {
                gameMapQueue.put(gameMap);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public ArrayBlockingQueue<GameMap> getGameMapQueue() {
        return gameMapQueue;
    }
}
