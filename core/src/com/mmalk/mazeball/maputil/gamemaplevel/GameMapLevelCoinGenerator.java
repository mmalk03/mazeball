package com.mmalk.mazeball.maputil.gamemaplevel;

import com.mmalk.mazeball.exceptions.MainBallNotFoundException;
import com.mmalk.mazeball.maputil.framework.GameMap;
import com.mmalk.mazeball.maputil.framework.GameMapCoinGenerator;


public class GameMapLevelCoinGenerator extends GameMapCoinGenerator {

    public GameMapLevelCoinGenerator(float coinProbability) {
        super(coinProbability);
    }

    @Override
    public void generateCoins(GameMap gameMap) throws MainBallNotFoundException {

    }
}
