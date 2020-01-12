package com.mmalk.mazeball.maputil.gamemap1pop;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mmalk.mazeball.exceptions.EndOfSolutionException;
import com.mmalk.mazeball.exceptions.PortalOnTheWayException;
import com.mmalk.mazeball.helpers.GameEngine;
import com.mmalk.mazeball.maputil.ArrayPosition;
import com.mmalk.mazeball.maputil.framework.GameMap;
import com.mmalk.mazeball.maputil.framework.GameMapSolution;
import com.mmalk.mazeball.maputil.framework.GameMapSolutionInterpreter;
import com.mmalk.mazeball.maputil.framework.GameMapSolutionRenderer;

public class GameMap1POPSolutionRenderer implements GameMapSolutionRenderer {

    //width of the line for drawing gameMapSolution
    public static final int SOLUTION_LINE_THICKNESS = 4;

    private ShapeRenderer shapeRenderer;

    private int colorModifier;

    public GameMap1POPSolutionRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    @Override
    public void render(GameMap gameMap, GameMapSolution gameMapSolution, GameMapSolutionInterpreter solutionInterpreter) {
        colorModifier = 100;

        ArrayPosition source;
        ArrayPosition dest;

        try {
            source = solutionInterpreter.getFirstArrayPosition(gameMap, gameMapSolution);
        } catch (EndOfSolutionException e) {
            return;
        }

        while (true) {
            updateShapeRenderer();
            try {
                dest = solutionInterpreter.getNextArrayPosition();
                drawLine(source, dest);
                source = dest;
            } catch (EndOfSolutionException e) {
                break;
            } catch (PortalOnTheWayException e) {
                try {
                    source = solutionInterpreter.getNextArrayPosition();
                    dest = solutionInterpreter.getNextArrayPosition();
                } catch (PortalOnTheWayException e1) {
                    //shouldn't happen
                    break;
                } catch (EndOfSolutionException e1) {
                    break;
                }
                drawLine(source, dest);
                source = dest;
            }
        }
    }

    private void updateShapeRenderer() {
        colorModifier += 15;
        if (colorModifier > 255) {
            colorModifier = 100;
        }
        shapeRenderer.setColor(
                (float) (.0 * colorModifier / 255f),
                (float) (1.0 * colorModifier / 255f),
                (float) (1.0 * colorModifier / 255f),
                0.3f);
    }

    private void drawLine(ArrayPosition source, ArrayPosition dest) {
        float sourceX = source.getColumn() * GameEngine.DEFAULT_GAME_OBJECT_SIZE + GameEngine.DEFAULT_GAME_OBJECT_SIZE / 2;
        float sourceY = source.getRow() * GameEngine.DEFAULT_GAME_OBJECT_SIZE + GameEngine.DEFAULT_GAME_OBJECT_SIZE / 2 + GameEngine.NAVIGATION_BAR_HEIGHT;
        float destX = dest.getColumn() * GameEngine.DEFAULT_GAME_OBJECT_SIZE + GameEngine.DEFAULT_GAME_OBJECT_SIZE / 2;
        float destY = dest.getRow() * GameEngine.DEFAULT_GAME_OBJECT_SIZE + GameEngine.DEFAULT_GAME_OBJECT_SIZE / 2 + GameEngine.NAVIGATION_BAR_HEIGHT;

        if (source.getColumn() == dest.getColumn()) {
            //vertical line
            sourceX -= SOLUTION_LINE_THICKNESS / 2;
            shapeRenderer.rect(
                    sourceX,
                    Math.min(sourceY, destY),
                    SOLUTION_LINE_THICKNESS,
                    Math.abs(sourceY - destY));
        } else if (source.getRow() == dest.getRow()) {
            //horizontal line
            sourceY -= SOLUTION_LINE_THICKNESS / 2;
            shapeRenderer.rect(
                    Math.min(sourceX, destX),
                    sourceY,
                    Math.abs(sourceX - destX),
                    SOLUTION_LINE_THICKNESS);
        }
    }
}
