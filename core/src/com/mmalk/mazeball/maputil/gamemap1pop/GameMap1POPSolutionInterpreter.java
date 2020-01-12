package com.mmalk.mazeball.maputil.gamemap1pop;

import com.mmalk.mazeball.exceptions.EndOfSolutionException;
import com.mmalk.mazeball.exceptions.PortalNotFoundException;
import com.mmalk.mazeball.exceptions.PortalOnTheWayException;
import com.mmalk.mazeball.exceptions.WinningSquareNotFoundException;
import com.mmalk.mazeball.maputil.ArrayPosition;
import com.mmalk.mazeball.maputil.Direction;
import com.mmalk.mazeball.maputil.MapPathAnalyser;
import com.mmalk.mazeball.maputil.framework.GameMap;
import com.mmalk.mazeball.maputil.framework.GameMapSolution;
import com.mmalk.mazeball.maputil.framework.GameMapSolutionInterpreter;

public class GameMap1POPSolutionInterpreter implements GameMapSolutionInterpreter {

    private ArrayPosition lastArrayPosition;
    private ArrayPosition portal1;
    private ArrayPosition portal2;
    private boolean returnedCoordinateOfFirstPortal;
    private boolean returnedCoordinateOfSecondPortal;
    private boolean shouldHandleSecondPortal;

    private GameMap gameMap;
    private GameMapSolution gameMapSolution;
    private GameMapSolution gameMapSolutionTemp;
    private boolean returnedWinningSquarePosition;

    private MapPathAnalyser mapPathAnalyser;

    public GameMap1POPSolutionInterpreter() {

    }

    private void init(GameMap gameMap, GameMapSolution gameMapSolution) {
        returnedCoordinateOfFirstPortal = false;
        returnedCoordinateOfSecondPortal = false;
        returnedWinningSquarePosition = false;
        shouldHandleSecondPortal = false;
        gameMapSolutionTemp = new GameMap1POPSolution(gameMapSolution);
        this.gameMap = gameMap;

        mapPathAnalyser = new MapPathAnalyser(gameMap);
    }

    @Override
    public ArrayPosition getFirstArrayPosition(GameMap gameMap, GameMapSolution gameMapSolution) {
        init(gameMap, gameMapSolution);
        lastArrayPosition = gameMapSolutionTemp.pop();
        return lastArrayPosition;
    }

    @Override
    public ArrayPosition getNextArrayPosition() throws PortalOnTheWayException, EndOfSolutionException {
        if (returnedCoordinateOfFirstPortal) {
            returnedCoordinateOfFirstPortal = false;
            shouldHandleSecondPortal = true;
            throw new PortalOnTheWayException();
        } else if (shouldHandleSecondPortal) {
            shouldHandleSecondPortal = false;
            returnedCoordinateOfSecondPortal = true;
            return portal2;
        } else if (returnedCoordinateOfSecondPortal) {
            returnedCoordinateOfSecondPortal = false;
            lastArrayPosition = gameMapSolutionTemp.pop();
            return lastArrayPosition;
        }

        ArrayPosition newArrayPosition;
        if (gameMapSolutionTemp.size() > 0) {
            newArrayPosition = gameMapSolutionTemp.pop();

            if (mapPathAnalyser.arePositionsInTheSameLine(newArrayPosition, lastArrayPosition)) {
                lastArrayPosition = newArrayPosition;
                return newArrayPosition;
            } else {
                gameMapSolutionTemp.push(newArrayPosition);
                return handlePortalOnTheWay(lastArrayPosition, newArrayPosition);
            }
        } else if (!returnedWinningSquarePosition) {
            try {
                returnedWinningSquarePosition = true;
                ArrayPosition winningSquarePosition = gameMap.findWinningSquarePosition();

                if (mapPathAnalyser.arePositionsInTheSameLine(lastArrayPosition, winningSquarePosition)) {
                    return winningSquarePosition;
                } else {
                    //portal is at the end of solution
                    returnedWinningSquarePosition = true;
                    gameMapSolutionTemp.push(winningSquarePosition);
                    return handlePortalOnTheWay(lastArrayPosition, winningSquarePosition);
                }

            } catch (WinningSquareNotFoundException e) {
                //shouldn't happen
                throw new EndOfSolutionException();
            }
        }

        throw new EndOfSolutionException();
    }

    private ArrayPosition handlePortalOnTheWay(ArrayPosition source, ArrayPosition dest) throws EndOfSolutionException {
        //portal is on the way
        try {
            portal1 = mapPathAnalyser.findNeighboringPortal(source);
        } catch (PortalNotFoundException e) {
            throw new EndOfSolutionException();
        }
        try {
            portal2 = gameMap.findPortalSibling(portal1);
        } catch (PortalNotFoundException e) {
            throw new EndOfSolutionException();
        }

        if (!isReachableThroughPortal(source, dest, portal1, portal2)) {
            ArrayPosition swap = portal1;
            portal1 = portal2;
            portal2 = swap;
        }

        returnedCoordinateOfFirstPortal = true;
        return portal1;
    }

    private boolean isReachableThroughPortal(ArrayPosition source, ArrayPosition dest, ArrayPosition portal1, ArrayPosition portal2) {
        Direction fromSourceToFirstPortal = mapPathAnalyser.getDirectionFromTo(source, portal1);
        if (fromSourceToFirstPortal == Direction.NONE) {
            return false;
        }
        return mapPathAnalyser.isReachable(portal2, dest, fromSourceToFirstPortal);
    }
}
