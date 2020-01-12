package com.mmalk.mazeball.gameobjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mmalk.mazeball.gameobjects.framework.GameObject;
import com.mmalk.mazeball.gameobjects.framework.GameObjectStationary;
import com.mmalk.mazeball.gameworld.framework.RendererAbstract;
import com.mmalk.mazeball.gameworld.framework.WorldAbstract;
import com.mmalk.mazeball.helpers.GameEngine;

/**
 * to achieve victory in the game, the user has to move the mainBall in such a way that it reaches the winningSquare
 */

public class WinningSquare extends GameObjectStationary {

    protected Rectangle boundingShape;

    /**
     * represents the side from which the winningSquare has to be entered to achieve victory
     * if winningSquare.side == Side.LEFT and the mainBall hits the right side of winningSquare, the winningSquare
     * acts as myBlock
     */
    protected Side side;

    public WinningSquare(float x, float y, float width, float height, float scale, Side side) {
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.boundingShape = new Rectangle();
        this.scale = scale;
        this.side = side;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    @Override
    public boolean collidesWith(MainBall mainBall) {
        return Intersector.overlaps(mainBall.getBoundingShape(), this.boundingShape);
    }

    @Override
    public void onCollision(WorldAbstract worldAbstract) {
        worldAbstract.onCollision(this);
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

    public Rectangle getBoundingShape() {
        return boundingShape;
    }

    public void setBoundingShape(Rectangle boundingShape) {
        this.boundingShape = boundingShape;
    }

    public enum Side {
        ALL,
        LEFT,
        TOP,
        RIGHT,
        BOTTOM
    }
}
