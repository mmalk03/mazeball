package com.mmalk.mazeball.gameobjects.framework;

/**
 * implemented by subclasses of GameObject
 * implement if given gameObject can change position
 */

public interface Movable {

    void move(float delta);
}
