package com.mmalk.mazeball.maputil.gamemap1pop;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mmalk.mazeball.exceptions.InvalidMapException;
import com.mmalk.mazeball.helpers.GameEngine;
import com.mmalk.mazeball.maputil.framework.GameMapCoinGenerator;
import com.mmalk.mazeball.maputil.framework.GameMapGenerator;
import com.mmalk.mazeball.maputil.framework.GameMapProvider;
import com.mmalk.mazeball.maputil.framework.GameMapSolution;
import com.mmalk.mazeball.maputil.framework.GameMapSolutionInterpreter;
import com.mmalk.mazeball.maputil.framework.GameMapSolutionRenderer;
import com.mmalk.mazeball.maputil.framework.GameMapSolver;
import com.mmalk.mazeball.maputil.framework.SolvableGameMapFactory;


public class GameMap1POPFactory extends SolvableGameMapFactory {

    private GameMapGenerator getGameMapGenerator() {
        return new GameMap1POPGenerator(GameEngine.BIG_MAP_SIZE_ROWS, GameEngine.BIG_MAP_SIZE_COLUMNS);
    }

    @Override
    public GameMapCoinGenerator getGameMapCoinGenerator() {
        return new GameMap1POPCoinGenerator(GameEngine.DEFAULT_COIN_PROBABILITY);
    }

    @Override
    public GameMapSolver getGameMapSolver() {
        return new GameMap1POPSolver();
    }

    @Override
    public GameMapProvider getGameMapProvider() {
        return new GameMap1POPProvider(
                getGameMapGenerator(),
                getGameMapCoinGenerator(),
                getGameMapSolver()
        );
    }

    @Override
    public GameMapSolutionInterpreter getGameMapSolutionInterpreter() {
        return new GameMap1POPSolutionInterpreter();
    }

    @Override
    public GameMapSolutionRenderer getGameMapSolutionRenderer(ShapeRenderer shapeRenderer) {
        return new GameMap1POPSolutionRenderer(shapeRenderer);
    }
}
