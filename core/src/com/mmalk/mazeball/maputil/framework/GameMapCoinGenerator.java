package com.mmalk.mazeball.maputil.framework;

import com.mmalk.mazeball.exceptions.MainBallNotFoundException;

public abstract class GameMapCoinGenerator {
    protected float coinProbability;

    public GameMapCoinGenerator(float coinProbability) {
        this.coinProbability = coinProbability;
    }

    public abstract void generateCoins(GameMap gameMap) throws MainBallNotFoundException;

    public float getCoinProbability() {
        return coinProbability;
    }

    public void setCoinProbability(float coinProbability) {
        this.coinProbability = coinProbability;
    }
}
