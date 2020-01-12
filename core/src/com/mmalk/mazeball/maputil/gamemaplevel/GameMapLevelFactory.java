package com.mmalk.mazeball.maputil.gamemaplevel;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mmalk.mazeball.helpers.GameEngine;
import com.mmalk.mazeball.maputil.framework.GameMapCoinGenerator;
import com.mmalk.mazeball.maputil.framework.GameMapProvider;
import com.mmalk.mazeball.maputil.framework.GameMapSolutionInterpreter;
import com.mmalk.mazeball.maputil.framework.GameMapSolutionRenderer;
import com.mmalk.mazeball.maputil.framework.GameMapSolver;
import com.mmalk.mazeball.maputil.framework.SolvableGameMapFactory;


public class GameMapLevelFactory extends SolvableGameMapFactory {

    private final int level;

    public GameMapLevelFactory(int level) {
        this.level = level;
    }

    @Override
    public GameMapCoinGenerator getGameMapCoinGenerator() {
        return new GameMapLevelCoinGenerator(GameEngine.DEFAULT_COIN_PROBABILITY);
    }

    @Override
    public GameMapSolver getGameMapSolver() {
        return new GameMapLevelSolver();
    }

    @Override
    public GameMapProvider getGameMapProvider() {
        return new GameMapLevelProvider(level);
    }

    @Override
    public GameMapSolutionInterpreter getGameMapSolutionInterpreter() {
        return new GameMapLevelSolutionInterpreter();
    }

    @Override
    public GameMapSolutionRenderer getGameMapSolutionRenderer(ShapeRenderer shapeRenderer) {
        return new GameMapLevelSolutionRenderer();
    }
}
