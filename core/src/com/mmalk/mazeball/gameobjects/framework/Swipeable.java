package com.mmalk.mazeball.gameobjects.framework;

/**
 * implemented by subclasses of GameObject
 * implement if given gameObject has to respond to screen swipe
 */

public interface Swipeable {

    void onSwipeLeft();
    void onSwipeRight();
    void onSwipeUp();
    void onSwipeDown();
}
