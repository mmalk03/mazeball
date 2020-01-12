package com.mmalk.mazeball.maputil.gamemaplevel;

import com.mmalk.mazeball.exceptions.EndOfSolutionException;
import com.mmalk.mazeball.exceptions.PortalOnTheWayException;
import com.mmalk.mazeball.maputil.ArrayPosition;
import com.mmalk.mazeball.maputil.framework.GameMap;
import com.mmalk.mazeball.maputil.framework.GameMapSolution;
import com.mmalk.mazeball.maputil.framework.GameMapSolutionInterpreter;


public class GameMapLevelSolutionInterpreter implements GameMapSolutionInterpreter {
    @Override
    public ArrayPosition getFirstArrayPosition(GameMap gameMap, GameMapSolution gameMapSolution) throws EndOfSolutionException {
        throw new EndOfSolutionException();
    }

    @Override
    public ArrayPosition getNextArrayPosition() throws PortalOnTheWayException, EndOfSolutionException {
        throw new EndOfSolutionException();
    }
}
