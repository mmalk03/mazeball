package com.mmalk.mazeball.gameobjects.framework;

import com.badlogic.gdx.math.Vector2;

/**
 * basic class for game objects that can change position and react to user input
 */

public abstract class GameObjectLiving extends GameObject implements Clickable, Movable, Swipeable {

    protected Vector2 velocity;
    protected Vector2 defaultVelocity;

    public Vector2 getDefaultVelocity() {
        return defaultVelocity;
    }

    public void setDefaultVelocity(Vector2 defaultVelocity) {
        this.defaultVelocity = defaultVelocity;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public boolean isMoving(){
        return !getVelocity().equals(new Vector2(0, 0));
    }
}
