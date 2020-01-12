package com.mmalk.mazeball.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.input.GestureDetector;

public class DirectionGestureDetector extends GestureDetector {

    public DirectionGestureDetector(DirectionListener directionListener) {
        super(new DirectionGestureListener(directionListener));
    }

    public interface DirectionListener {

        void onLeft();

        void onRight();

        void onUp();

        void onDown();

        void onClick();
    }

    private static class DirectionGestureListener extends GestureAdapter {

        DirectionListener directionListener;

        public DirectionGestureListener(DirectionListener directionListener) {
            this.directionListener = directionListener;
        }

        @Override
        public boolean touchDown(float x, float y, int pointer, int button) {
            directionListener.onClick();
            return super.touchDown(x, y, pointer, button);
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            if (Math.abs(velocityX) > Math.abs(velocityY)) {
                if (velocityX > 0) {
                    directionListener.onRight();
                } else {
                    directionListener.onLeft();
                }
            } else {
                if (velocityY > 0) {
                    directionListener.onDown();
                } else {
                    directionListener.onUp();
                }
            }
            return super.fling(velocityX, velocityY, button);
        }

    }
}
