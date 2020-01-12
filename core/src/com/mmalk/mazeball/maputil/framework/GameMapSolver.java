package com.mmalk.mazeball.maputil.framework;

import com.mmalk.mazeball.exceptions.InvalidMapException;
import com.mmalk.mazeball.exceptions.SolutionNotFoundException;

public interface GameMapSolver {
    void solve(GameMap gameMap) throws SolutionNotFoundException, InvalidMapException;
}
