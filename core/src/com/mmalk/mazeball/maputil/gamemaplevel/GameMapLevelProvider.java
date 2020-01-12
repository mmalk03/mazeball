package com.mmalk.mazeball.maputil.gamemaplevel;

import com.mmalk.mazeball.maputil.framework.GameMap;
import com.mmalk.mazeball.maputil.framework.GameMapGenerator;
import com.mmalk.mazeball.maputil.framework.GameMapProvider;
import com.mmalk.mazeball.maputil.gamemaplevel.generators.EmptyGameMapLevelGenerator;
import com.mmalk.mazeball.maputil.gamemaplevel.generators.GameMapLevel1Generator;
import com.mmalk.mazeball.maputil.gamemaplevel.generators.GameMapLevel2Generator;
import com.mmalk.mazeball.maputil.gamemaplevel.generators.GameMapLevel3Generator;
import com.mmalk.mazeball.maputil.gamemaplevel.generators.GameMapLevel4Generator;
import com.mmalk.mazeball.maputil.gamemaplevel.generators.GameMapLevel5Generator;
import com.mmalk.mazeball.maputil.gamemaplevel.generators.GameMapLevel6Generator;

import java.util.ArrayList;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public class GameMapLevelProvider implements GameMapProvider {

    private final int level;
    private final List<GameMapLevelGenerator> generators;

    public GameMapLevelProvider(int level) {
        this.level = level;
        generators = new ArrayList<GameMapLevelGenerator>();
        initGenerators();
    }

    private void initGenerators() {
        generators.add(new EmptyGameMapLevelGenerator());
        generators.add(new GameMapLevel1Generator());
        generators.add(new GameMapLevel2Generator());
        generators.add(new GameMapLevel3Generator());
        generators.add(new GameMapLevel4Generator());
        generators.add(new GameMapLevel5Generator());
        generators.add(new GameMapLevel6Generator());

    }

    @Override
    public GameMap provideMap() {
        if(level < 0 || level >= generators.size())
        {
            return new EmptyGameMapLevelGenerator().generateMap();
        }
        return generators.get(level).generateMap();
    }
}
