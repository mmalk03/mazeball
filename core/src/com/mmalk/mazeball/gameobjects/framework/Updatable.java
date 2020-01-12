package com.mmalk.mazeball.gameobjects.framework;

/**
 * implemented by subclasses of GameObject
 * implement if given gameObject has to update it's properties on each game loop
 */

public interface Updatable {

    void update(float delta);
}
