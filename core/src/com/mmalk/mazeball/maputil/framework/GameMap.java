package com.mmalk.mazeball.maputil.framework;

import com.mmalk.mazeball.exceptions.MainBallNotFoundException;
import com.mmalk.mazeball.exceptions.PortalNotFoundException;
import com.mmalk.mazeball.exceptions.WinningSquareNotFoundException;
import com.mmalk.mazeball.maputil.ArrayPosition;

/**
 * following integers correspond to given values (defined in enum GameMapObject)
 * 0 => empty cell
 * 1 => MainBall
 * 3 => MyBlock
 * 4 => Portal
 * 5 => Coin
 * 20 => WinningSquare All
 * 21 => WinningSquare Left
 * 22 => WinningSquare Top
 * 23 => WinningSquare Right
 * 24 => WinningSquare Bottom
 */

public abstract class GameMap {

    public static final String TAG = "GameMap";

    private GameMapObject[][] map;
    private int rows;
    private int columns;
    private GameMapSolution gameMapSolution;

    public GameMap(GameMapObject[][] map, int rows, int columns) {
        this.map = map;
        this.rows = rows;
        this.columns = columns;
    }

    public static GameMapObject[][] convertMapIntToEnum(int map[][], int rows, int columns) {
        GameMapObject[][] gameMapObjects = new GameMapObject[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                switch (map[i][j]) {
                    case 0:
                        gameMapObjects[i][j] = GameMapObject.EMPTY_CELL;
                        break;
                    case 1:
                        gameMapObjects[i][j] = GameMapObject.MAIN_BALL;
                        break;
                    case 3:
                        gameMapObjects[i][j] = GameMapObject.MY_BLOCK;
                        break;
                    case 4:
                        gameMapObjects[i][j] = GameMapObject.PORTAL;
                        break;
                    case 20:
                        gameMapObjects[i][j] = GameMapObject.WS_ALL;
                        break;
                    case 21:
                        gameMapObjects[i][j] = GameMapObject.WS_LEFT;
                        break;
                    case 22:
                        gameMapObjects[i][j] = GameMapObject.WS_TOP;
                        break;
                    case 23:
                        gameMapObjects[i][j] = GameMapObject.WS_RIGHT;
                        break;
                    case 24:
                        gameMapObjects[i][j] = GameMapObject.WS_BOTTOM;
                        break;
                }
            }
        }

        return gameMapObjects;
    }

    public GameMapSolution getGameMapSolution() {
        return gameMapSolution;
    }

    public void setGameMapSolution(GameMapSolution gameMapSolution) {
        this.gameMapSolution = gameMapSolution;
    }

    public ArrayPosition findWinningSquarePosition() throws WinningSquareNotFoundException {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (map[i][j] == GameMapObject.WS_ALL ||
                        map[i][j] == GameMapObject.WS_LEFT ||
                        map[i][j] == GameMapObject.WS_TOP ||
                        map[i][j] == GameMapObject.WS_RIGHT ||
                        map[i][j] == GameMapObject.WS_BOTTOM) {
                    return new ArrayPosition(i, j);
                }
            }
        }
        throw new WinningSquareNotFoundException();
    }

    public ArrayPosition findMainBallPosition() throws MainBallNotFoundException {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (map[i][j] == GameMapObject.MAIN_BALL) {
                    return new ArrayPosition(i, j);
                }
            }
        }
        throw new MainBallNotFoundException();
    }

    /**
     * given a portal P1, finds it's 'sibling' P2
     * if the mainBall enters P1 from top, it changes position to P2 and continues moving downwards
     * analogical when entering P1 from different side
     *
     * @param arrayPositionPortal position of a portal in the gameMap
     * @return position of the portal's 'sibling'
     * @throws PortalNotFoundException when the 'sibling' can't be found
     */
    public ArrayPosition findPortalSibling(ArrayPosition arrayPositionPortal) throws PortalNotFoundException {
        if (arrayPositionPortal.getRow() < 0 ||
                arrayPositionPortal.getRow() >= rows ||
                arrayPositionPortal.getColumn() < 0 ||
                arrayPositionPortal.getColumn() >= columns) {
            throw new PortalNotFoundException();
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (map[i][j] == GameMapObject.PORTAL &&
                        (i != arrayPositionPortal.getRow() ||
                                j != arrayPositionPortal.getColumn())) {
                    return new ArrayPosition(i, j);
                }
            }
        }

        throw new PortalNotFoundException();
    }

    public GameMapObject[][] getMap() {
        return map;
    }

    public void setMap(GameMapObject[][] map) {
        this.map = map;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < rows; i++) {
            result += "[";
            for (int j = 0; j < columns; j++) {
                result = result + map[i][j] + ",";
            }
            result += "]\n";
        }
        return result;
    }

    /**
     * prints the game map in a concise way, use mainly for debugging purposes
     *
     * @return human-readable gameMap as 2D array containing integers
     */
    public String printGameMap() {
        String result = "";

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                switch (map[i][j]) {
                    case EMPTY_CELL:
                        result += "0";
                        break;
                    case MAIN_BALL:
                        result += "1";
                        break;
                    case MY_BLOCK:
                        result += "3";
                        break;
                    case PORTAL:
                        result += "4";
                        break;
                    case COIN:
                        result += "5";
                        break;
                    case WS_ALL:
                        result += "20";
                        break;
                    case WS_LEFT:
                        result += "21";
                        break;
                    case WS_TOP:
                        result += "22";
                        break;
                    case WS_RIGHT:
                        result += "23";
                        break;
                    case WS_BOTTOM:
                        result += "24";
                        break;
                }
            }
            result += "\n";
        }

        return result;
    }

    public enum GameMapObject {
        EMPTY_CELL,
        MAIN_BALL,
        MY_BLOCK,
        PORTAL,
        COIN,
        WS_ALL,
        WS_LEFT,
        WS_TOP,
        WS_RIGHT,
        WS_BOTTOM
    }
}