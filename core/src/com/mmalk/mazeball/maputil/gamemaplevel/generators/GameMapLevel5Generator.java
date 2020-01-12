package com.mmalk.mazeball.maputil.gamemaplevel.generators;

import com.mmalk.mazeball.helpers.GameEngine;
import com.mmalk.mazeball.maputil.framework.GameMap;
import com.mmalk.mazeball.maputil.gamemaplevel.GameMapLevel;
import com.mmalk.mazeball.maputil.gamemaplevel.GameMapLevelGenerator;


public class GameMapLevel5Generator extends GameMapLevelGenerator {
    @Override
    public GameMap generateMap() {
        return new GameMapLevel(
                GameMap.convertMapIntToEnum(
                        new int[][]{
                                {0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0},
                                {0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3},
                                {0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                                {0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0},
                                {0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20},
                                {0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 3},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0},
                                {0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0}},
                        GameEngine.DEFAULT_MAP_SIZE_ROWS,
                        GameEngine.DEFAULT_MAP_SIZE_COLUMNS),
                GameEngine.DEFAULT_MAP_SIZE_ROWS,
                GameEngine.DEFAULT_MAP_SIZE_COLUMNS,
                8
        );
    }
}
