package com.mmalk.mazeball.maputil.gamemap1pop;

import com.mmalk.mazeball.exceptions.InvalidMapException;
import com.mmalk.mazeball.exceptions.MainBallNotFoundException;
import com.mmalk.mazeball.exceptions.SolutionNotFoundException;
import com.mmalk.mazeball.maputil.ArrayPosition;
import com.mmalk.mazeball.maputil.framework.GameMap;
import com.mmalk.mazeball.maputil.framework.GameMapSolution;
import com.mmalk.mazeball.maputil.framework.GameMapSolver;

import java.util.LinkedList;
import java.util.Stack;

public class GameMap1POPSolver implements GameMapSolver {

    LinkedList<ArrayPosition> queue;
    private GameMap gameMap;
    private ArrayPosition[][] parentArray; //during BFS parent of currently considered node will be placed inside this array to remember the path
    private boolean[][] visited;
    private ArrayPosition mainBallPosition;

    @Override
    public void solve(GameMap gameMap) throws SolutionNotFoundException, InvalidMapException {
        this.gameMap = gameMap;

        queue = new LinkedList<ArrayPosition>();
        try {
            mainBallPosition = gameMap.findMainBallPosition();
        } catch (MainBallNotFoundException e) {
            throw new InvalidMapException();
        }

        initVisitedArray();
        initParentArray();
        queue.clear();

        ArrayPosition tempArrayPosition;
        queue.push(mainBallPosition);
        setParent(mainBallPosition, new ArrayPosition(-2, -2));

        while (!queue.isEmpty()) {
            tempArrayPosition = queue.removeLast();
            if (mainBallPosition.getRow() != 0) {
            }
            if (putNeighboursToQueue(tempArrayPosition)) {
                GameMapSolution gameMapSolution = traceTheSolution(tempArrayPosition);
                gameMap.setGameMapSolution(gameMapSolution);
                return;
            }
        }

        throw new SolutionNotFoundException();
    }

    private void initParentArray() {
        parentArray = new ArrayPosition[gameMap.getRows()][gameMap.getColumns()];
        for (int i = 0; i < gameMap.getRows(); i++) {
            for (int j = 0; j < gameMap.getColumns(); j++) {
                parentArray[i][j] = new ArrayPosition(-1, -1);
            }
        }
    }

    private void initVisitedArray() {
        visited = new boolean[gameMap.getRows()][gameMap.getColumns()];
        for (int i = 0; i < gameMap.getRows(); i++) {
            for (int j = 0; j < gameMap.getColumns(); j++) {
                visited[i][j] = false;
            }
        }
    }

    private boolean putNeighboursToQueue(ArrayPosition tempArrayPosition) {
        //left
        if (checkHorizontal(tempArrayPosition, -1)) {
            return true;
        }
        //top
        if (checkVertical(tempArrayPosition, -1)) {
            return true;
        }
        //right
        if (checkHorizontal(tempArrayPosition, 1)) {
            return true;
        }
        //bottom
        if (checkVertical(tempArrayPosition, 1)) {
            return true;
        }

        return false;
    }

    /**
     * @param tempArrayPosition
     * @param columnModifier    -1 go left      1 go right
     * @return
     */
    private boolean checkHorizontal(ArrayPosition tempArrayPosition, int columnModifier) {
        GameMap.GameMapObject[][] map = gameMap.getMap();
        boolean hasNeighbour = true;
        int i = tempArrayPosition.getRow();
        int j = tempArrayPosition.getColumn() + columnModifier;

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
                    checkAndPlaceHorizontal(i, j, tempArrayPosition, columnModifier);
                    break;
                case PORTAL:
                    try {
                        if (checkHorizontalAfterPortal(gameMap.findPortalSibling(new ArrayPosition(i, j)), columnModifier, tempArrayPosition)) {
                            return true;
                        }
                    } catch (Exception e) {
                        //portalSibling not found
                        e.printStackTrace();
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
        GameMap.GameMapObject[][] map = gameMap.getMap();
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
        GameMap.GameMapObject[][] map = gameMap.getMap();
        boolean hasNeighbour = true;
        int i = tempArrayPosition.getRow() + rowModifier;
        int j = tempArrayPosition.getColumn();
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
                    checkAndPlaceVertical(i, j, tempArrayPosition, rowModifier);
                    break;
                case PORTAL:
                    try {
                        if (checkVerticalAfterPortal(gameMap.findPortalSibling(new ArrayPosition(i, j)), rowModifier, tempArrayPosition)) {
                            return true;
                        }
                    } catch (Exception e) {
                        //portalSibling not found
                        e.printStackTrace();
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
        GameMap.GameMapObject[][] map = gameMap.getMap();
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
                queue.addFirst(new ArrayPosition(i, j - columnModifier));
                setParent(new ArrayPosition(i, j - columnModifier), tempArrayPosition);
            }
        }
    }

    private void checkAndPlaceVertical(int i, int j, ArrayPosition tempArrayPosition, int rowModifier) {
        if ((i - rowModifier) != tempArrayPosition.getRow()) {
            if (!visited[i - rowModifier][j]) {
                visited[i - rowModifier][j] = true;
                queue.addFirst(new ArrayPosition(i - rowModifier, j));
                setParent(new ArrayPosition(i - rowModifier, j), tempArrayPosition);
            }
        }
    }

    private GameMapSolution traceTheSolution(ArrayPosition lastArrayPosition) throws SolutionNotFoundException {
        Stack<ArrayPosition> solution = new Stack<ArrayPosition>();
        ArrayPosition tempArrayPosition = new ArrayPosition(lastArrayPosition.getRow(), lastArrayPosition.getColumn());
        solution.push(tempArrayPosition);
        tempArrayPosition = findParent(tempArrayPosition);

        while (tempArrayPosition.getRow() != -2 ||
                tempArrayPosition.getColumn() != -2) {

            solution.push(tempArrayPosition);
            tempArrayPosition = findParent(tempArrayPosition);
        }

        return new GameMap1POPSolution(solution);
    }

    private ArrayPosition findParent(ArrayPosition tempArrayPosition) {
        return new ArrayPosition(
                parentArray[tempArrayPosition.getRow()][tempArrayPosition.getColumn()].getRow(),
                parentArray[tempArrayPosition.getRow()][tempArrayPosition.getColumn()].getColumn());
    }

    private void setParent(ArrayPosition currentPosition, ArrayPosition parentPosition) {
        parentArray[currentPosition.getRow()][currentPosition.getColumn()] = new ArrayPosition(parentPosition.getRow(), parentPosition.getColumn());
    }
}

