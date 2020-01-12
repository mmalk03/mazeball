package com.mmalk.mazeball;

import com.badlogic.gdx.Game;
import com.mmalk.mazeball.helpers.AssetLoader;
import com.mmalk.mazeball.screens.ScreenModeLevel;
import com.mmalk.mazeball.screens.ScreenTest;
import com.mmalk.mazeball.screens.SplashScreen;

public class MazeballGame extends Game{

    @Override
    public void create() {
        AssetLoader.load();
        setScreen(new SplashScreen(this));
        //setScreen(new ScreenTest());
    }

    @Override
    public void dispose(){
        super.dispose();
        AssetLoader.dispose();
    }
}
