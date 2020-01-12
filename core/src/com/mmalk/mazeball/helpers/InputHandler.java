package com.mmalk.mazeball.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.mmalk.mazeball.gameobjects.framework.GameObject;
import com.mmalk.mazeball.gameobjects.framework.GameObjectStationary;

import java.util.List;

public class InputHandler implements InputProcessor {

    public static final String TAG = "InputHandler";

    private List<GameObjectStationary> gameObjectStationaryList;

    public InputHandler(List<GameObjectStationary> gameObjectStationaryList) {
        this.gameObjectStationaryList = gameObjectStationaryList;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        for (GameObjectStationary gameObjectStationary : gameObjectStationaryList) {
//            gameObjectStationary.onClick();
//        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
