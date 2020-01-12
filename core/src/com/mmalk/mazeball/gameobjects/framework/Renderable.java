package com.mmalk.mazeball.gameobjects.framework;


import com.mmalk.mazeball.gameworld.framework.RendererAbstract;

/**
 * implemented by subclasses of GameObject
 * implement if given gameObject has to be drawn on screen
 * render(RendererAbstract rendererAbstract) is used for double dispatch, so GameRenderer can decide how to render given GameObject
 */

public interface Renderable {

    void render(RendererAbstract rendererAbstract);
}
