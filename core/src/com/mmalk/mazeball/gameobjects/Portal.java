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
 * represents a portal and holds reference to it's 'sibling'
 * each gameMap has an even number of portals
 * if a mainBall encounters a portal P1 on it's way, it is moved to the portal's sibling P2 and continues its journey
 * in such a way that it doesn't change the direction of movement
 */

public class Portal extends GameObjectStationary {

    public static final String TAG = "MyBlock";

    protected Rectangle boundingShape;
    protected Portal portalSibling;

    public Portal(float x, float y, float width, float height, float scale) {
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

    public Portal getPortalSibling() {
        return portalSibling;
    }

    public void setPortalSibling(Portal portalSibling) {
        this.portalSibling = portalSibling;
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
