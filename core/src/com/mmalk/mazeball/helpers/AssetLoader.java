package com.mmalk.mazeball.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mmalk.mazeball.maputil.GameMapGeneratorThread;
import com.mmalk.mazeball.maputil.gamemap1pop.GameMap1POPEndlessFactory;
import com.mmalk.mazeball.maputil.gamemap1pop.GameMap1POPFactory;

public class AssetLoader {

    public static Texture texture;
    public static TextureRegion textureRegionBackground;
    public static TextureRegion[] textureRegionsMainBall;
    public static TextureRegion[] textureRegionsMyBlock;
    public static TextureRegion[] textureRegionsWinningSquare;
    public static TextureRegion[] textureRegionsPortal;
    public static TextureRegion[] textureRegionsCoin;
    public static Animation animationMainBall;
    public static Animation animationMyBlock;
    public static Animation animationWinningSquare;
    public static Animation animationPortal;
    public static Animation animationCoin;
    public static Skin skin;
    public static Preferences preferences;

    private AssetLoader() {
    }

    public static void load() {
        loadMainTexture();

        loadBackgroundTextures();
        loadMainBallTextures();
        loadMyBlockTextures();
        loadPortalTextures();
        loadWinningSquareTextures();
        loadCoinTextures();

        createAnimationForGameObjects();

        loadSkins();

        loadPreferences();

        startMapGeneratorThread();
    }

    private static void loadMainTexture() {
        texture = new Texture(Gdx.files.internal(GameEngine.TEXTURE_PATH));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    private static void startMapGeneratorThread() {
        GameMap1POPEndlessFactory factory = new GameMap1POPEndlessFactory();

        GameMapGeneratorThread gameMapGeneratorThread = GameMapGeneratorThread.getInstance(
                factory.getGameMapProvider()
        );
        gameMapGeneratorThread.start();
    }

    private static void loadPreferences() {
        preferences = Gdx.app.getPreferences("Mazeball");
        if (!preferences.contains("highScore")) {
            preferences.putInteger("highScore", 0);
        }
        for (int i = 0; i < GameEngine.LEVELS_MODE_LEVEL; i++) {
            if (!preferences.contains("level" + i + "stars")) {
                preferences.putInteger("level" + i + "stars", 0);
            }
        }
        for (int i = 0; i < GameEngine.LEVELS_MODE_LEVEL; i++) {
            if (!preferences.contains("level" + i + "unlocked")) {
                preferences.putBoolean("level" + i + "unlocked", false);
            }
        }
        preferences.putBoolean("level" + 0 + "unlocked", true);
        preferences.flush();
    }

    private static void loadSkins() {
        //AssetManager assetManager = new AssetManager();
        //assetManager.load("data/pack.atlas", TextureAtlas.class);
        //TextureAtlas textureAtlas = assetManager.get("data/pack.atlas", TextureAtlas.class);
        /*TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("data/pack4.atlas"));
        skin = new Skin(Gdx.files.internal("data/basic_ui_skin.json"), textureAtlas);*/

        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("data/packs/pack5.atlas"));
        skin = new Skin(Gdx.files.internal("data/skins/skin5.json"), textureAtlas);
    }

    private static void createAnimationForGameObjects() {
        animationMainBall = new Animation(GameEngine.MAIN_BALL_ANIMATION_FRAME_DURATION, textureRegionsMainBall);
        animationMainBall.setPlayMode(Animation.PlayMode.LOOP);

        animationMyBlock = new Animation(GameEngine.MY_BLOCK_ANIMATION_FRAME_DURATION, textureRegionsMyBlock);
        animationMyBlock.setPlayMode(Animation.PlayMode.LOOP);

        animationPortal = new Animation(GameEngine.PORTAL_ANIMATION_FRAME_DURATION, textureRegionsPortal);
        animationPortal.setPlayMode(Animation.PlayMode.LOOP);

        animationWinningSquare = new Animation(GameEngine.WINNING_SQUARE_ANIMATION_FRAME_DURATION, textureRegionsWinningSquare);
        animationWinningSquare.setPlayMode(Animation.PlayMode.LOOP);

        animationCoin = new Animation(GameEngine.COIN_ANIMATION_FRAME_DURATION, textureRegionsCoin);
        animationCoin.setPlayMode(Animation.PlayMode.LOOP);
    }

    private static void loadCoinTextures() {
        textureRegionsCoin = new TextureRegion[GameEngine.COIN_SPRITE_COUNT];
        for (int i = 0; i < GameEngine.COIN_SPRITE_COUNT; i++) {
            textureRegionsCoin[i] = new TextureRegion(texture,
                    GameEngine.COIN_X + i * GameEngine.COIN_TEXTURE_WIDTH,
                    GameEngine.COIN_Y,
                    GameEngine.COIN_TEXTURE_WIDTH,
                    GameEngine.COIN_TEXTURE_HEIGHT);
            textureRegionsCoin[i].flip(false, true);
        }
    }

    private static void loadWinningSquareTextures() {
        textureRegionsWinningSquare = new TextureRegion[GameEngine.WINNING_SQUARE_SPRITE_COUNT];
        for (int i = 0; i < GameEngine.WINNING_SQUARE_SPRITE_COUNT; i++) {
            textureRegionsWinningSquare[i] = new TextureRegion(texture,
                    GameEngine.WINNING_SQUARE_X + i * GameEngine.WINNING_SQUARE_TEXTURE_WIDTH,
                    GameEngine.WINNING_SQUARE_Y,
                    GameEngine.WINNING_SQUARE_TEXTURE_WIDTH,
                    GameEngine.WINNING_SQUARE_TEXTURE_HEIGHT);
            textureRegionsWinningSquare[i].flip(false, true);
        }
    }

    private static void loadPortalTextures() {
        textureRegionsPortal = new TextureRegion[GameEngine.PORTAL_SPRITE_COUNT];
        for (int i = 0; i < GameEngine.PORTAL_SPRITE_COUNT; i++) {
            textureRegionsPortal[i] = new TextureRegion(texture,
                    GameEngine.PORTAL_X + i * GameEngine.PORTAL_TEXTURE_WIDTH,
                    GameEngine.PORTAL_Y,
                    GameEngine.PORTAL_TEXTURE_WIDTH,
                    GameEngine.PORTAL_TEXTURE_HEIGHT);
            textureRegionsPortal[i].flip(false, true);
        }
    }

    private static void loadMyBlockTextures() {
        textureRegionsMyBlock = new TextureRegion[GameEngine.MY_BLOCK_SPRITE_COUNT];
        for (int i = 0; i < GameEngine.MY_BLOCK_SPRITE_COUNT; i++) {
            textureRegionsMyBlock[i] = new TextureRegion(texture,
                    GameEngine.MY_BLOCK_X + i * GameEngine.MY_BLOCK_TEXTURE_WIDTH,
                    GameEngine.MY_BLOCK_Y,
                    GameEngine.MY_BLOCK_TEXTURE_WIDTH,
                    GameEngine.MY_BLOCK_TEXTURE_HEIGHT);
            textureRegionsMyBlock[i].flip(false, true);
        }
    }

    private static void loadMainBallTextures() {
        textureRegionsMainBall = new TextureRegion[GameEngine.MAIN_BALL_SPRITE_COUNT];
        for (int i = 0; i < GameEngine.MAIN_BALL_SPRITE_COUNT; i++) {
            textureRegionsMainBall[i] = new TextureRegion(texture,
                    GameEngine.MAIN_BALL_X + i * GameEngine.MAIN_BALL_TEXTURE_WIDTH,
                    GameEngine.MAIN_BALL_Y,
                    GameEngine.MAIN_BALL_TEXTURE_WIDTH,
                    GameEngine.MAIN_BALL_TEXTURE_HEIGHT);
            textureRegionsMainBall[i].flip(false, true);
        }
    }

    private static void loadBackgroundTextures() {
        textureRegionBackground = new TextureRegion(texture,
                GameEngine.BACKGROUND_X,
                GameEngine.BACKGROUND_Y,
                GameEngine.BACKGROUND_WIDTH,
                GameEngine.BACKGROUND_HEIGHT);
        textureRegionBackground.flip(false, true);
    }

    public static void dispose() {
        texture.dispose();
    }

    public static int getHighScore() {
        return preferences.getInteger("highScore");
    }

    public static void setHighScore(int highScore) {
        preferences.putInteger("highScore", highScore);
        preferences.flush();
    }

    public static void setStars(int level, int stars) {
        preferences.putInteger("level" + level + "stars", stars);
        preferences.flush();
    }

    public static int getStars(int level) {
        return preferences.getInteger("level" + level + "stars");
    }

    public static void setUnlocked(int level, boolean value) {
        preferences.putBoolean("level" + level + "unlocked", value);
        preferences.flush();
    }

    public static boolean isUnlocked(int level) {
        return preferences.getBoolean("level" + level + "unlocked");
    }
}
