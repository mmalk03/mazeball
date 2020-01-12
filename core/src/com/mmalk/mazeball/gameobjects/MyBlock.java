package com.mmalk.mazeball.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mmalk.mazeball.gameobjects.framework.GameObject;
import com.mmalk.mazeball.gameobjects.framework.GameObjectLiving;
import com.mmalk.mazeball.gameobjects.framework.GameObjectStationary;
import com.mmalk.mazeball.gameworld.framework.RendererAbstract;
import com.mmalk.mazeball.gameworld.framework.WorldAbstract;
import com.mmalk.mazeball.helpers.GameEngine;

/**
 * represents a block or a wall which blocks the movement of the mainBall
 */

public class MyBlock extends GameObjectStationary {

    public static final String TAG = "MyBlock";

    protected Rectangle boundingShape;

    public MyBlock(float x, float y, float width, float height, float scale) {
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.boundingShape = new Rectangle();
        this.scale = scale;
    }

    @Override
    public void onCollision(WorldAbstract worldAbstract) {
        worldAbstract.onCollision(this);
    }

    public Rectangle getBoundingShape() {
        return boundingShape;
    }

    public void setBoundingShape(Rectangle boundingShape) {
        this.boundingShape = boundingShape;
    }

    @Override
    public void render(RendererAbstract rendererAbstract) {
        rendererAbstract.render(this);
    }

    @Override
    public void update(float delta) {
        this.boundingShape.set(
                this.position.x,
                this.position.y,
                this.width,
                this.height
        );
    }

    @Override
    public boolean collidesWith(MainBall mainBall) {
        return Intersector.overlaps(mainBall.getBoundingShape(), this.boundingShape);
    }
}
