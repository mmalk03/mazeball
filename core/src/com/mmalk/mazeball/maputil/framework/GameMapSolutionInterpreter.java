package com.mmalk.mazeball.maputil.framework;

import com.mmalk.mazeball.exceptions.EndOfSolutionException;
import com.mmalk.mazeball.exceptions.PortalOnTheWayException;
import com.mmalk.mazeball.maputil.ArrayPosition;

public interface GameMapSolutionInterpreter {
    ArrayPosition getFirstArrayPosition(GameMap gameMap, GameMapSolution gameMapSolution) throws EndOfSolutionException;
    ArrayPosition getNextArrayPosition() throws PortalOnTheWayException, EndOfSolutionException;
}
