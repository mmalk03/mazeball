package com.mmalk.mazeball.maputil.gamemap1pop;

import com.mmalk.mazeball.exceptions.InvalidMapException;
import com.mmalk.mazeball.exceptions.MainBallNotFoundException;
import com.mmalk.mazeball.exceptions.SolutionNotFoundException;
import com.mmalk.mazeball.exceptions.WinningSquareNotFoundException;
import com.mmalk.mazeball.maputil.ArrayPosition;
import com.mmalk.mazeball.maputil.framework.GameMap;
import com.mmalk.mazeball.maputil.framework.GameMapCoinGenerator;
import com.mmalk.mazeball.maputil.framework.GameMapGenerator;
import com.mmalk.mazeball.maputil.framework.GameMapProvider;
import com.mmalk.mazeball.maputil.framework.GameMapSolver;

import java.util.Random;


public class GameMap1POPEndlessProvider implements GameMapProvider {

    private GameMapGenerator mapGenerator;
    private GameMapCoinGenerator coinGenerator;
    private GameMapSolver mapSolver;
    private GameMap gameMap;

    private Random random;
    private ArrayPosition nextMainBallPosition;

    public GameMap1POPEndlessProvider(GameMapGenerator mapGenerator, GameMapCoinGenerator coinGenerator, GameMapSolver mapSolver) {
        this.mapGenerator = mapGenerator;
        this.coinGenerator = coinGenerator;
        this.mapSolver = mapSolver;

        random = new Random();
    }

    @Override
    public GameMap provideMap() {
        while (true) {
            gameMap = mapGenerator.generateMap();

            if (nextMainBallPosition != null) {
                removeMainBallFromCurrentPosition();
                placeMainBallAtNewPosition();
            }

            try {
                mapSolver.solve(gameMap);

                //gameMap is solvable
                ArrayPosition winningSquarePosition = gameMap.findWinningSquarePosition();
                setNextMainBallPosition(winningSquarePosition);

                coinGenerator.generateCoins(gameMap);
                break;
            } catch (SolutionNotFoundException e) {
                continue;
            } catch (MainBallNotFoundException e) {
                continue;
            } catch (InvalidMapException invalidMapException) {
                continue;
            } catch (WinningSquareNotFoundException e) {
                continue;
            }
        }
        return gameMap;
    }

    private void removeMainBallFromCurrentPosition() {
        try {
            ArrayPosition mainBallPosition = gameMap.findMainBallPosition();
            gameMap.getMap()[mainBallPosition.getRow()][mainBallPosition.getColumn()] = GameMap.GameMapObject.EMPTY_CELL;
        } catch (MainBallNotFoundException e) {
            //don't expect this to happen
        }
    }

    private void setNextMainBallPosition(ArrayPosition winningSquarePosition) {
        nextMainBallPosition = new ArrayPosition(0, winningSquarePosition.getColumn());
    }

    private void placeMainBallAtNewPosition() {
        gameMap.getMap()[nextMainBallPosition.getRow()][nextMainBallPosition.getColumn()] = GameMap.GameMapObject.MAIN_BALL;
    }
}
