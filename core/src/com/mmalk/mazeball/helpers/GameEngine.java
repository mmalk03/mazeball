package com.mmalk.mazeball.helpers;

public class GameEngine {

    public static final String TEXTURE_PATH = "textures/mazeball_texture.png";
    public static final int SCREEN_WIDTH = 1080;
    public static final int SCREEN_HEIGHT = 1620;
    public static final int DEFAULT_GAME_OBJECT_SIZE = 27;

    public static final float NAVIGATION_BAR_HEIGHT = 3 * DEFAULT_GAME_OBJECT_SIZE;
    public static final float NAVIGATION_BAR_HEIGHT_PERCENT = 1/10f;

    /** Elements from textures/background1.png **/

    //MainBall properties
    public static final float MAIN_BALL_ANIMATION_FRAME_DURATION = 5.00f;
    public static final int MAIN_BALL_SPRITE_COUNT = 2;
    public static final int MAIN_BALL_X = 0;
    public static final int MAIN_BALL_Y = 1024;
    public static final int MAIN_BALL_TEXTURE_WIDTH = 128;
    public static final int MAIN_BALL_TEXTURE_HEIGHT = 128;
    public static final int MAIN_BALL_WIDTH = DEFAULT_GAME_OBJECT_SIZE;
    public static final int MAIN_BALL_HEIGHT = DEFAULT_GAME_OBJECT_SIZE;
    public static final float MAIN_BALL_VELOCITY_DEFAULT = 400.0f;
    public static final float MAIN_BALL_SCALE = DEFAULT_GAME_OBJECT_SIZE / MAIN_BALL_WIDTH;

    //MyBlock properties
    public static final float MY_BLOCK_ANIMATION_FRAME_DURATION = 20.00f;
    public static final int MY_BLOCK_SPRITE_COUNT = 1;
    public static final int MY_BLOCK_X = 0 + 2 * 128;
    public static final int MY_BLOCK_Y = 1152;
    public static final int MY_BLOCK_TEXTURE_WIDTH = 128;
    public static final int MY_BLOCK_TEXTURE_HEIGHT = 128;
    public static final int MY_BLOCK_WIDTH = DEFAULT_GAME_OBJECT_SIZE;
    public static final int MY_BLOCK_HEIGHT = DEFAULT_GAME_OBJECT_SIZE;
    public static final float MY_BLOCK_SCALE = DEFAULT_GAME_OBJECT_SIZE / MY_BLOCK_WIDTH;

    //Coin properties
    public static final float COIN_ANIMATION_FRAME_DURATION = 0.10f;
    public static final int COIN_SPRITE_COUNT = 1;
    public static final int COIN_X = 0;
    public static final int COIN_Y = 1536;
    public static final int COIN_TEXTURE_WIDTH = 128;
    public static final int COIN_TEXTURE_HEIGHT = 128;
    public static final int COIN_WIDTH = DEFAULT_GAME_OBJECT_SIZE;
    public static final int COIN_HEIGHT = DEFAULT_GAME_OBJECT_SIZE;
    public static final float COIN_SCALE = DEFAULT_GAME_OBJECT_SIZE / MY_BLOCK_WIDTH;

    //Portal properties
    public static final float PORTAL_ANIMATION_FRAME_DURATION = 5.00f;
    public static final int PORTAL_SPRITE_COUNT = 1;
    public static final int PORTAL_X = 0;
    public static final int PORTAL_Y = 1408;
    public static final int PORTAL_TEXTURE_WIDTH = 128;
    public static final int PORTAL_TEXTURE_HEIGHT = 128;
    public static final int PORTAL_WIDTH = DEFAULT_GAME_OBJECT_SIZE;
    public static final int PORTAL_HEIGHT = DEFAULT_GAME_OBJECT_SIZE;
    public static final float PORTAL_SCALE = DEFAULT_GAME_OBJECT_SIZE / PORTAL_WIDTH;

    //WinningSquare properties
    public static final float WINNING_SQUARE_ANIMATION_FRAME_DURATION = 5.00f;
    public static final int WINNING_SQUARE_SPRITE_COUNT = 2;
    public static final int WINNING_SQUARE_X = 0;
    public static final int WINNING_SQUARE_Y = 1280;
    public static final int WINNING_SQUARE_TEXTURE_WIDTH = 128;
    public static final int WINNING_SQUARE_TEXTURE_HEIGHT = 128;
    public static final int WINNING_SQUARE_WIDTH = DEFAULT_GAME_OBJECT_SIZE;
    public static final int WINNING_SQUARE_HEIGHT = DEFAULT_GAME_OBJECT_SIZE;
    public static final float WINNING_SQUARE_SCALE = DEFAULT_GAME_OBJECT_SIZE / WINNING_SQUARE_WIDTH;

    //Background properties
    public static final int BACKGROUND_X = 1024;
    public static final int BACKGROUND_Y = 0;
    public static final int BACKGROUND_WIDTH = 512;
    public static final int BACKGROUND_HEIGHT = 1024;

    /** GameWorldProperties **/

    public static final int DEFAULT_MAP_SIZE_ROWS = 22;
    public static final int DEFAULT_MAP_SIZE_COLUMNS = 15;
    public static final int BIG_MAP_SIZE_ROWS = 27;
    public static final int BIG_MAP_SIZE_COLUMNS = 20;

    public static final int LEVELS_MODE_LEVEL = 5;
    public static final int BUTTONS_IN_ROW = 4;

    public static final float DEFAULT_COIN_PROBABILITY = .4f;

    private GameEngine() {
    }
}
