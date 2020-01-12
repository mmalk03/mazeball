package com.mmalk.mazeball.screens.framework;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mmalk.mazeball.gameobjects.MainBall;
import com.mmalk.mazeball.gameobjects.framework.GameObject;
import com.mmalk.mazeball.gameobjects.framework.GameObjectLiving;
import com.mmalk.mazeball.gameworld.framework.RendererAbstract;
import com.mmalk.mazeball.gameworld.framework.WorldAbstract;

import java.util.List;

public abstract class ScreenAbstract implements Screen{

    protected WorldAbstract worldAbstract;
    protected RendererAbstract rendererAbstract;
    protected Game game;

    protected float runningTime;
}
