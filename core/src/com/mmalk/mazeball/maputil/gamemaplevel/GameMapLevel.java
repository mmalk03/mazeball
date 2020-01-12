package com.mmalk.mazeball.maputil.gamemaplevel;

import com.mmalk.mazeball.maputil.framework.GameMap;


public class GameMapLevel extends GameMap {

    private int movesToSolve;

    public GameMapLevel(GameMapObject[][] map, int rows, int columns, int movesToSolve) {
        super(map, rows, columns);
        this.movesToSolve = movesToSolve;
    }
}
