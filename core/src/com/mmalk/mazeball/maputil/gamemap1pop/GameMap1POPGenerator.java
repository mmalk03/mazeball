package com.mmalk.mazeball.maputil.gamemap1pop;

import com.mmalk.mazeball.maputil.ArrayPosition;
import com.mmalk.mazeball.maputil.framework.GameMap;
import com.mmalk.mazeball.maputil.framework.GameMapGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class GameMap1POPGenerator implements GameMapGenerator {
    public static final String TAG = "GameMap1POPGenerator";

    protected int rows;
    protected int cols;
    protected Random random;
    protected GameMap.GameMapObject[][] map;
    protected GameMap1POP gameMap1POP;
    protected float portalProbability = 1.0f;

    public GameMap1POPGenerator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        random = new Random();
    }

    public GameMap.GameMapObject[][] getMap() {
        return map;
    }

    public void setMap(GameMap.GameMapObject[][] map) {
        this.map = map;
    }

    public GameMap1POP getGameMap1POP() {
        return gameMap1POP;
    }

    public void setGameMap1POP(GameMap1POP gameMap1POP) {
        this.gameMap1POP = gameMap1POP;
    }

    public int getRows() {

        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    @Override
    public GameMap generateMap() {
        map = new GameMap.GameMapObject[rows][cols];

        placeMyBlocks();
        placeMainBall();
        placeWinningSquare();
        placePortals();

        gameMap1POP = new GameMap1POP(
                map,
                rows,
                cols
        );

        return gameMap1POP;
    }

    private void placeMyBlocks() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (random.nextFloat() < 0.2f) {
                    map[i][j] = GameMap.GameMapObject.MY_BLOCK;
                } else {
                    map[i][j] = GameMap.GameMapObject.EMPTY_CELL;
                }
            }
        }
    }

    private void placeMainBall() {
        map[0][random.nextInt(cols)] = GameMap.GameMapObject.MAIN_BALL;
    }

    private void placeWinningSquare() {
        map[rows - 1][random.nextInt(cols)] = GameMap.GameMapObject.WS_ALL;
    }

    private void placePortals() {
        if (random.nextFloat() < portalProbability) {
            ArrayPosition tempArrayPosition;
            List<ArrayPosition> list = new LinkedList<ArrayPosition>();

            for (int i = 1; i < rows - 1; i++) {
                for (int j = 1; j < cols - 1; j++) {
                    if (map[i][j] == GameMap.GameMapObject.EMPTY_CELL) {
                        if (map[i][j - 1] == GameMap.GameMapObject.EMPTY_CELL &&
                                map[i - 1][j] == GameMap.GameMapObject.EMPTY_CELL &&
                                map[i][j + 1] == GameMap.GameMapObject.EMPTY_CELL &&
                                map[i + 1][j] == GameMap.GameMapObject.EMPTY_CELL) {
                            list.add(new ArrayPosition(i, j));
                        }
                    }
                }
            }

            if (list.size() >= 2) {
                int index1 = random.nextInt(list.size());
                int index2;
                ArrayPosition portal1 = list.get(index1);
                ArrayPosition portal2;

                do {
                    while ((index2 = random.nextInt(list.size())) == index1) ;
                    portal2 = list.get(index2);
                }
                while (portal1.getRow() == portal2.getRow() ||
                        portal1.getColumn() == portal2.getColumn());

                int currentIndex = 0;
                ListIterator<ArrayPosition> listIterator = list.listIterator();
                while (listIterator.hasNext()) {
                    tempArrayPosition = listIterator.next();
                    if (currentIndex == index1) {
                        map[tempArrayPosition.getRow()][tempArrayPosition.getColumn()] = GameMap.GameMapObject.PORTAL;
                    } else if (currentIndex == index2) {
                        map[tempArrayPosition.getRow()][tempArrayPosition.getColumn()] = GameMap.GameMapObject.PORTAL;
                    }
                    currentIndex++;
                }
            }
        }
    }

}
