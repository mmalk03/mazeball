package com.mmalk.mazeball.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mmalk.mazeball.gameobjects.framework.GameObjectLiving;
import com.mmalk.mazeball.gameworld.framework.RendererAbstract;
import com.mmalk.mazeball.gameworld.framework.WorldAbstract;
import com.mmalk.mazeball.helpers.GameEngine;

/**
 * represents the main character of the game
 */

public class MainBall extends GameObjectLiving {

    public static final String TAG = "MainBall";

    protected Circle boundingShape;

    public MainBall(float x, float y, float width, float height, float velocity_x, float velocity_y, float scale) {
        this.position = new Vector2(x, y);
        this.defaultVelocity = new Vector2(velocity_x, velocity_y);
        this.velocity = new Vector2(0f, 0f);
        this.width = width;
        this.height = height;
        this.boundingShape = new Circle();
        this.scale = scale;
    }

    public Circle getBoundingShape() {
        return boundingShape;
    }

    public void setBoundingShape(Circle boundingShape) {
        this.boundingShape = boundingShape;
    }

    @Override
    public void update(float delta) {
        //update boundingShape
        this.boundingShape.set(
                this.position.x + this.width / 2,
                this.position.y + this.height / 2,
                this.width / 2);
        this.move(delta);
    }

    @Override
    public void onClick() {
    }

    @Override
    public void render(RendererAbstract rendererAbstract) {
        rendererAbstract.render(this);
    }

    @Override
    public void move(float delta) {
        position.add(velocity.cpy().scl(delta));
    }

    @Override
    public void onSwipeLeft() {
        //prevent changing the velocity of the ball while it's moving
        if (!this.velocity.equals(Vector2.Zero)) {
            return;
        }
        this.velocity.x = -this.defaultVelocity.x;
        this.velocity.y = 0;
    }

    @Override
    public void onSwipeRight() {
        //prevent changing the velocity of the ball while it's moving
        if (!this.velocity.equals(Vector2.Zero)) {
            return;
        }
        this.velocity.x = this.defaultVelocity.x;
        this.velocity.y = 0;
    }

    @Override
    public void onSwipeUp() {
        //prevent changing the velocity of the ball while it's moving
        if (!this.velocity.equals(Vector2.Zero)) {
            return;
        }
        this.velocity.y = -this.defaultVelocity.y;
        this.velocity.x = 0;
    }

    @Override
    public void onSwipeDown() {
        //prevent changing the velocity of the ball while it's moving
        if (!this.velocity.equals(Vector2.Zero)) {
            return;
        }
        this.velocity.y = this.defaultVelocity.y;
        this.velocity.x = 0;
    }
}
