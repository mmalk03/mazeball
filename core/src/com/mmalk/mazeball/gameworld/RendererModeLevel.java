package com.mmalk.mazeball.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mmalk.mazeball.gameworld.framework.RendererAbstract;
import com.mmalk.mazeball.gameworld.framework.WorldAbstract;
import com.mmalk.mazeball.helpers.GameEngine;

public class RendererModeLevel extends RendererAbstract {

    public RendererModeLevel(WorldAbstract gameWorld) {
        super(gameWorld);
    }

    public void render(float runningTime) {
        this.runningTime = runningTime;

        // Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        renderBackground();
        renderSolution();
        renderGameObjects();
        renderNavigationBarBackground();
        renderGameOverBackground();
        renderVictoryBackground();
        spriteBatch.end();
        shapeRenderer.end();
    }

    private void renderVictoryBackground() {
        WorldAbstract.GameState gameState = gameWorld.getGameState();
        if (gameState == WorldModeEndless.GameState.VICTORY || gameState == WorldAbstract.GameState.HIGHSCORE) {
            shapeRenderer.setColor(33 / 255f, 56 / 255f, 94 / 255f, 1);
            shapeRenderer.rect(GameEngine.SCREEN_WIDTH / 8, GameEngine.SCREEN_HEIGHT / 4 - GameEngine.SCREEN_HEIGHT / 16,
                    GameEngine.SCREEN_WIDTH / 4,
                    GameEngine.SCREEN_HEIGHT / 8);
        }
    }

}
