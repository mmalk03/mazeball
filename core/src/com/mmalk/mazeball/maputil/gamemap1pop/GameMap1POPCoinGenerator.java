package com.mmalk.mazeball.maputil.gamemap1pop;

import com.badlogic.gdx.Gdx;
import com.mmalk.mazeball.exceptions.MainBallNotFoundException;
import com.mmalk.mazeball.maputil.ArrayPosition;
import com.mmalk.mazeball.maputil.framework.GameMap;
import com.mmalk.mazeball.maputil.framework.GameMapCoinGenerator;

import java.util.Random;
import java.util.Stack;


public class GameMap1POPCoinGenerator extends GameMapCoinGenerator {

    public static final String TAG = "GameMap1POPCoinGenerator";

    private boolean[][] visited;
    private Stack<ArrayPosition> stack;
    private int rows;
    private int cols;
    private ArrayPosition mainBallPosition;
    private GameMap.GameMapObject[][] map;
    private GameMap gameMap;

    public GameMap1POPCoinGenerator(float coinProbability) {
        super(coinProbability);
        stack = new Stack<ArrayPosition>();
    }

    /**
     * populates the gameMap with coins using DFS algorithm
     */
    @Override
    public void generateCoins(GameMap gameMap) throws MainBallNotFoundException {
        initLocalVars(gameMap);
        initVisitedArray();
        stack.clear();

        ArrayPosition tempArrayPosition;
        stack.push(mainBallPosition);
        Random random = new Random();

        while (!stack.isEmpty()) {
            tempArrayPosition = stack.pop();
            //generate a coin
            if (!tempArrayPosition.equals(mainBallPosition)) {
                if (random.nextFloat() < 0.35f) {
                    map[tempArrayPosition.getRow()][tempArrayPosition.getColumn()] = GameMap.GameMapObject.COIN;
                }
            }
            putNeighboursOnStack(tempArrayPosition);
        }
    }

    private void initLocalVars(GameMap gameMap) throws MainBallNotFoundException {
        this.gameMap = gameMap;
        this.rows = gameMap.getRows();
        this.cols = gameMap.getColumns();
        this.mainBallPosition = gameMap.findMainBallPosition();
        this.map = gameMap.getMap();
    }

    private void initVisitedArray() {
        visited = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                visited[i][j] = false;
            }
        }
    }

    private void putNeighboursOnStack(ArrayPosition tempArrayPosition) {
        //left
        checkHorizontal(tempArrayPosition, -1);
        //top
        checkVertical(tempArrayPosition, -1);
        //right
        checkHorizontal(tempArrayPosition, 1);
        //bottom
        checkVertical(tempArrayPosition, 1);
    }

    private boolean checkHorizontal(ArrayPosition tempArrayPosition, int columnModifier) {
        boolean hasNeighbour = true;
        int i = tempArrayPosition.getRow();
        int j = tempArrayPosition.getColumn() + columnModifier;

        try {
            while (map[i][j] == GameMap.GameMapObject.EMPTY_CELL || map[i][j] == GameMap.GameMapObject.COIN) {
                j += columnModifier;
            }
        } catch (Exception e) {
            //reached end of row
            hasNeighbour = false;
        }
        if (hasNeighbour) {
            switch (map[i][j]) {
                case MY_BLOCK:
                    checkAndPlaceHorizontal(i, j, tempArrayPosition, columnModifier);
                    break;
                case PORTAL:
                    try {
                        if (checkHorizontalAfterPortal(gameMap.findPortalSibling(new ArrayPosition(i, j)), columnModifier, tempArrayPosition)) {
                            return true;
                        }
                    } catch (Exception e) {
                        Gdx.app.log(TAG, "portalSibling not found");
                    }
                    break;
                case WS_ALL:
                    return true;
                case WS_LEFT:
                    checkAndPlaceHorizontal(i, j, tempArrayPosition, columnModifier);
                    break;
                case WS_TOP:
                    checkAndPlaceHorizontal(i, j, tempArrayPosition, columnModifier);
                    break;
                case WS_RIGHT:
                    return true;
                case WS_BOTTOM:
                    checkAndPlaceHorizontal(i, j, tempArrayPosition, columnModifier);
                    break;
                default:
                    break;
            }
        }

        return false;
    }

    private boolean checkHorizontalAfterPortal(ArrayPosition portalSibling, int columnModifier, ArrayPosition alternativeParent) {
        boolean hasNeighbour = true;
        int i = portalSibling.getRow();
        int j = portalSibling.getColumn() + columnModifier;

        try {
            while (map[i][j] == GameMap.GameMapObject.EMPTY_CELL || map[i][j] == GameMap.GameMapObject.COIN) {
                j += columnModifier;
            }
        } catch (Exception e) {
            //reached left end of row
            hasNeighbour = false;
        }
        if (hasNeighbour) {
            switch (map[i][j]) {
                case MY_BLOCK:
                    checkAndPlaceHorizontal(i, j, alternativeParent, columnModifier);
                    break;
                case PORTAL:
                    //prevent infinite recursion:
                    //Let row R from gameMap M be like: ... PORTAL(P1) ... EMPTY_CELL with MY_BLOCK adjacent just below ... PORTAL(P2) ...
                    //Consider a situation when you swipe down and land on top of MY_BLOCK so that mainBall is in R
                    //Swiping left results in entering P1 from right side, so the mainBall is teleported to P2 and still goes left until it once again meets P1
                    //Analogous situation when swiping right
                    return false;
                case WS_ALL:
                    return true;
                case WS_LEFT:
                    checkAndPlaceHorizontal(i, j, alternativeParent, columnModifier);
                    break;
                case WS_TOP:
                    checkAndPlaceHorizontal(i, j, alternativeParent, columnModifier);
                    break;
                case WS_RIGHT:
                    return true;
                case WS_BOTTOM:
                    checkAndPlaceHorizontal(i, j, alternativeParent, columnModifier);
                    break;
                default:
                    break;
            }
        }

        return false;
    }

    private boolean checkVertical(ArrayPosition tempArrayPosition, int rowModifier) {
        boolean hasNeighbour = true;
        int i = tempArrayPosition.getRow() + rowModifier;
        int j = tempArrayPosition.getColumn();
        try {
            while (map[i][j] == GameMap.GameMapObject.EMPTY_CELL || map[i][j] == GameMap.GameMapObject.COIN) {
                i += rowModifier;
            }
        } catch (Exception e) {
            //reached end of column
            hasNeighbour = false;
        }
        if (hasNeighbour) {
            switch (map[i][j]) {
                case MY_BLOCK:
                    checkAndPlaceVertical(i, j, tempArrayPosition, rowModifier);
                    break;
                case PORTAL:
                    try {
                        if (checkVerticalAfterPortal(gameMap.findPortalSibling(new ArrayPosition(i, j)), rowModifier, tempArrayPosition)) {
                            return true;
                        }
                    } catch (Exception e) {
                        Gdx.app.log(TAG, "portalSibling not found");
                    }
                    break;
                case WS_ALL:
                    return true;
                case WS_LEFT:
                    checkAndPlaceVertical(i, j, tempArrayPosition, rowModifier);
                    break;
                case WS_TOP:
                    checkAndPlaceVertical(i, j, tempArrayPosition, rowModifier);
                    break;
                case WS_RIGHT:
                    return true;
                case WS_BOTTOM:
                    checkAndPlaceVertical(i, j, tempArrayPosition, rowModifier);
                    break;
                default:
                    break;
            }
        }

        return false;
    }

    private boolean checkVerticalAfterPortal(ArrayPosition portalSibling, int rowModifier, ArrayPosition alternativeParent) {
        boolean hasNeighbour = true;
        int i = portalSibling.getRow() + rowModifier;
        int j = portalSibling.getColumn();
        try {
            while (map[i][j] == GameMap.GameMapObject.EMPTY_CELL || map[i][j] == GameMap.GameMapObject.COIN) {
                i += rowModifier;
            }
        } catch (Exception e) {
            //reached left end of row
            hasNeighbour = false;
        }
        if (hasNeighbour) {
            switch (map[i][j]) {
                case MY_BLOCK:
                    checkAndPlaceVertical(i, j, alternativeParent, rowModifier);
                    break;
                case PORTAL:
                    //prevent infinite recursion:
                    //Let column C from gameMap M be like: (... PORTAL(P1) ... EMPTY_CELL with MY_BLOCK adjacent on left side ... PORTAL(P2) ...) Transposed
                    //Consider a situation when you swipe right and stick to right side of MY_BLOCK so that mainBall is in C
                    //Swiping up results in entering P1 from bottom, so the mainBall is teleported to P2 and still goes up until it once again meets P1
                    //Analogous situation when swiping down
                    return false;
                case WS_ALL:
                    return true;
                case WS_LEFT:
                    checkAndPlaceVertical(i, j, alternativeParent, rowModifier);
                    break;
                case WS_TOP:
                    checkAndPlaceVertical(i, j, alternativeParent, rowModifier);
                    break;
                case WS_RIGHT:
                    return true;
                case WS_BOTTOM:
                    checkAndPlaceVertical(i, j, alternativeParent, rowModifier);
                    break;
                default:
                    break;
            }
        }

        return false;
    }

    private void checkAndPlaceHorizontal(int i, int j, ArrayPosition tempArrayPosition, int columnModifier) {
        if ((j - columnModifier) != tempArrayPosition.getColumn()) {
            if (!visited[i][j - columnModifier]) {
                visited[i][j - columnModifier] = true;
                stack.push(new ArrayPosition(i, j - columnModifier));
            }
        }
    }

    private void checkAndPlaceVertical(int i, int j, ArrayPosition tempArrayPosition, int rowModifier) {
        if ((i - rowModifier) != tempArrayPosition.getRow()) {
            if (!visited[i - rowModifier][j]) {
                visited[i - rowModifier][j] = true;
                stack.push(new ArrayPosition(i - rowModifier, j));
            }
        }
    }
}
