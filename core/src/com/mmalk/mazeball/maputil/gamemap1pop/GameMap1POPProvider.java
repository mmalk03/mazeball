package com.mmalk.mazeball.maputil.gamemap1pop;

import com.mmalk.mazeball.exceptions.InvalidMapException;
import com.mmalk.mazeball.exceptions.MainBallNotFoundException;
import com.mmalk.mazeball.exceptions.SolutionNotFoundException;
import com.mmalk.mazeball.maputil.framework.GameMap;
import com.mmalk.mazeball.maputil.framework.GameMapCoinGenerator;
import com.mmalk.mazeball.maputil.framework.GameMapGenerator;
import com.mmalk.mazeball.maputil.framework.GameMapProvider;
import com.mmalk.mazeball.maputil.framework.GameMapSolver;

public class GameMap1POPProvider implements GameMapProvider {

    private GameMapGenerator mapGenerator;
    private GameMapCoinGenerator coinGenerator;
    private GameMapSolver mapSolver;

    public GameMap1POPProvider(GameMapGenerator mapGenerator, GameMapCoinGenerator coinGenerator, GameMapSolver mapSolver) {
        this.mapGenerator = mapGenerator;
        this.coinGenerator = coinGenerator;
        this.mapSolver = mapSolver;
    }

    @Override
    public GameMap provideMap() {
        GameMap gameMap;
        while(true){
            gameMap = mapGenerator.generateMap();
            try {
                mapSolver.solve(gameMap);
                coinGenerator.generateCoins(gameMap);
                break;
            } catch (SolutionNotFoundException e) {
                continue;
            } catch (MainBallNotFoundException e) {
                continue;
            } catch (InvalidMapException invalidMapException) {
                continue;
            }
        }
        return gameMap;
    }
}
