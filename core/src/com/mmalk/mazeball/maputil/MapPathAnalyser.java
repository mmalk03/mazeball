package com.mmalk.mazeball.maputil;

import com.mmalk.mazeball.exceptions.PortalNotFoundException;
import com.mmalk.mazeball.maputil.framework.GameMap;

public class MapPathAnalyser {

    private GameMap gameMap;

    public MapPathAnalyser(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public Direction getDirectionFromTo(ArrayPosition source, ArrayPosition dest) {
        if (isReachableGoingLeft(source, dest))
            return Direction.LEFT;
        else if (isReachableGoingTop(source, dest))
            return Direction.TOP;
        else if (isReachableGoingRight(source, dest))
            return Direction.RIGHT;
        else if (isReachableGoingBottom(source, dest))
            return Direction.BOTTOM;
        else return Direction.NONE;
    }

    public ArrayPosition findNeighboringPortal(ArrayPosition arrayPosition) throws PortalNotFoundException {
        try {
            return findPortalLeft(arrayPosition);
        } catch (PortalNotFoundException e) {
        }
        try {
            return findPortalTop(arrayPosition);
        } catch (PortalNotFoundException e) {
        }
        try {
            return findPortalRight(arrayPosition);
        } catch (PortalNotFoundException e) {
        }
        try {
            return findPortalBottom(arrayPosition);
        } catch (PortalNotFoundException e) {
        }

        throw new PortalNotFoundException();
    }

    private ArrayPosition findPortalLeft(ArrayPosition arrayPosition) throws PortalNotFoundException {
        GameMap.GameMapObject[][] map = gameMap.getMap();
        boolean hasNeighbour = true;
        int i = arrayPosition.getRow();
        int j = arrayPosition.getColumn();

        try {
            do {
                j--;
            }
            while (map[i][j] == GameMap.GameMapObject.EMPTY_CELL || map[i][j] == GameMap.GameMapObject.COIN);
        } catch (Exception e) {
            //reached end of row
            hasNeighbour = false;
        }
        if (hasNeighbour) {
            if (map[i][j] == GameMap.GameMapObject.PORTAL) {
                return new ArrayPosition(i, j);
            }
        }

        throw new PortalNotFoundException();
    }

    private ArrayPosition findPortalTop(ArrayPosition arrayPosition) throws PortalNotFoundException {
        GameMap.GameMapObject[][] map = gameMap.getMap();
        boolean hasNeighbour = true;
        int i = arrayPosition.getRow();
        int j = arrayPosition.getColumn();

        try {
            do {
                i--;
            }
            while (map[i][j] == GameMap.GameMapObject.EMPTY_CELL || map[i][j] == GameMap.GameMapObject.COIN);
        } catch (Exception e) {
            //reached end of row
            hasNeighbour = false;
        }
        if (hasNeighbour) {
            if (map[i][j] == GameMap.GameMapObject.PORTAL) {
                return new ArrayPosition(i, j);
            }
        }

        throw new PortalNotFoundException();
    }

    private ArrayPosition findPortalRight(ArrayPosition arrayPosition) throws PortalNotFoundException {
        GameMap.GameMapObject[][] map = gameMap.getMap();
        boolean hasNeighbour = true;
        int i = arrayPosition.getRow();
        int j = arrayPosition.getColumn();

        try {
            do {
                j++;
            }
            while (map[i][j] == GameMap.GameMapObject.EMPTY_CELL || map[i][j] == GameMap.GameMapObject.COIN);
        } catch (Exception e) {
            //reached end of row
            hasNeighbour = false;
        }
        if (hasNeighbour) {
            if (map[i][j] == GameMap.GameMapObject.PORTAL) {
                return new ArrayPosition(i, j);
            }
        }

        throw new PortalNotFoundException();
    }

    private ArrayPosition findPortalBottom(ArrayPosition arrayPosition) throws PortalNotFoundException {
        GameMap.GameMapObject[][] map = gameMap.getMap();
        boolean hasNeighbour = true;
        int i = arrayPosition.getRow();
        int j = arrayPosition.getColumn();

        try {
            do {
                i++;
            }
            while (map[i][j] == GameMap.GameMapObject.EMPTY_CELL || map[i][j] == GameMap.GameMapObject.COIN);
        } catch (Exception e) {
            //reached end of row
            hasNeighbour = false;
        }
        if (hasNeighbour) {
            if (map[i][j] == GameMap.GameMapObject.PORTAL) {
                return new ArrayPosition(i, j);
            }
        }

        throw new PortalNotFoundException();
    }

    public boolean arePositionsInTheSameLine(ArrayPosition position1, ArrayPosition position2) {
        return position1.getRow() == position2.getRow() ||
                position1.getColumn() == position2.getColumn();
    }

    public boolean isReachable(ArrayPosition source, ArrayPosition dest, Direction direction) {
        switch (direction) {
            case LEFT:
                return isReachableGoingLeft(source, dest);
            case TOP:
                return isReachableGoingTop(source, dest);
            case RIGHT:
                return isReachableGoingRight(source, dest);
            case BOTTOM:
                return isReachableGoingBottom(source, dest);
            case NONE:
                return false;
            default:
                return false;
        }
    }

    private boolean isReachableGoingLeft(ArrayPosition source, ArrayPosition dest) {
        GameMap.GameMapObject[][] map = gameMap.getMap();
        int i = source.getRow();
        int j = source.getColumn();

        try {
            do {
                j--;
                if (j == dest.getColumn()) {
                    return true;
                }
            }
            while (map[i][j] == GameMap.GameMapObject.EMPTY_CELL || map[i][j] == GameMap.GameMapObject.COIN);
        } catch (Exception e) {
            //reached end of row
            return false;
        }
        return j == dest.getColumn();
    }

    private boolean isReachableGoingTop(ArrayPosition source, ArrayPosition dest) {
        GameMap.GameMapObject[][] map = gameMap.getMap();
        int i = source.getRow();
        int j = source.getColumn();

        try {
            do {
                i--;
                if (i == dest.getRow()) {
                    return true;
                }
            }
            while (map[i][j] == GameMap.GameMapObject.EMPTY_CELL || map[i][j] == GameMap.GameMapObject.COIN);
        } catch (Exception e) {
            //reached end of row
            return false;
        }
        return i == dest.getRow();
    }

    private boolean isReachableGoingRight(ArrayPosition source, ArrayPosition dest) {
        GameMap.GameMapObject[][] map = gameMap.getMap();
        int i = source.getRow();
        int j = source.getColumn();

        try {
            do {
                j++;
                if (j == dest.getColumn()) {
                    return true;
                }
            }
            while (map[i][j] == GameMap.GameMapObject.EMPTY_CELL || map[i][j] == GameMap.GameMapObject.COIN);
        } catch (Exception e) {
            //reached end of row
            return false;
        }
        return j == dest.getColumn();
    }

    private boolean isReachableGoingBottom(ArrayPosition source, ArrayPosition dest) {
        GameMap.GameMapObject[][] map = gameMap.getMap();
        int i = source.getRow();
        int j = source.getColumn();

        try {
            do {
                i++;
                if (i == dest.getRow()) {
                    return true;
                }
            }
            while (map[i][j] == GameMap.GameMapObject.EMPTY_CELL || map[i][j] == GameMap.GameMapObject.COIN);
        } catch (Exception e) {
            //reached end of row
            return false;
        }
        return i == dest.getRow();
    }
}
