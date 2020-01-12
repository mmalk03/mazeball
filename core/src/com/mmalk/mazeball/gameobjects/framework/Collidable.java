package com.mmalk.mazeball.gameobjects.framework;

import com.mmalk.mazeball.gameobjects.MainBall;
import com.mmalk.mazeball.gameobjects.MyBlock;
import com.mmalk.mazeball.gameworld.framework.WorldAbstract;

/**
 * implemented by subclasses of GameObject
 * implement if given gameObject can collide with mainBall
 * onCollision(WorldAbstract worldAbstract) is used for double dispatch, so given GameWorld can decide how to handle the collision
 */

public interface Collidable {

    boolean collidesWith(MainBall mainBall);
    void onCollision(WorldAbstract worldAbstract);
}
