package com.mmalk.mazeball.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mmalk.mazeball.gameworld.framework.RendererAbstract;
import com.mmalk.mazeball.gameworld.framework.WorldAbstract;
import com.mmalk.mazeball.maputil.framework.GameMap;
import com.mmalk.mazeball.maputil.framework.GameMapSolutionInterpreter;

public class RendererModeEndless extends RendererAbstract {

    public RendererModeEndless(WorldAbstract gameWorld) {
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
        spriteBatch.end();
        shapeRenderer.end();
    }

    @Override
    protected void renderSolution() {
        super.renderSolution();

    }
}
