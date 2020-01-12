package com.mmalk.mazeball.gameobjects.framework;

import com.badlogic.gdx.math.Vector2;

/**
 * base class for all game objects with basic functionality: draw on screen and update
 */

public abstract class GameObject implements Renderable, Updatable {

    protected Vector2 position;
    protected float width;
    protected float height;
    protected float rotation = 0f;
    protected float scale = 1f;

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
