package com.mmalk.mazeball.maputil.framework;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mmalk.mazeball.exceptions.InvalidMapException;

public abstract class SolvableGameMapFactory {
    public abstract GameMapCoinGenerator getGameMapCoinGenerator();
    public abstract GameMapSolver getGameMapSolver();
    public abstract GameMapProvider getGameMapProvider();
    public abstract GameMapSolutionInterpreter getGameMapSolutionInterpreter();
    public abstract GameMapSolutionRenderer getGameMapSolutionRenderer(ShapeRenderer shapeRenderer);
}
