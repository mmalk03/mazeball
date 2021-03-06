package com.mmalk.mazeball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ScreenTest implements Screen {

    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;

    public ScreenTest()
    {
        atlas = new TextureAtlas();
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);

        //Stage should controll input:
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void show() {
        //Create Table
        Table mainTable = new Table();
        Table rowTable = new Table();
        //Set table to fill stage
        //mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.top();

        //Create buttons
        TextButton playButton = new TextButton("Play", skin);
        TextButton optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);
        //Add listeners to buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new ScreenMainMenu((Game)Gdx.app.getApplicationListener()));
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //Add buttons to table
        mainTable.add(playButton).width(500f).height(800f);
        mainTable.add(optionsButton).width(500f).height(800f);
        mainTable.row();
        mainTable.add(exitButton).width(500f).height(800f);
        mainTable.add(new TextButton("button 1", skin)).width(500f).height(800f);
        mainTable.add(new TextButton("button 1.5", skin)).width(500f).height(800f);
        mainTable.row();
        mainTable.add(new TextButton("button 2", skin)).width(500f).height(800f);
        mainTable.add(new TextButton("button 3", skin)).width(500f).height(800f);
        mainTable.row();
        rowTable.add(new TextButton("row button 2", skin));
        rowTable.add(new TextButton("row button 3", skin));
        mainTable.add(rowTable).width(500f).height(800f);

        ScrollPane scrollPane = new ScrollPane(mainTable);

        Table table = new Table();
        table.setFillParent(true);
        table.add(scrollPane).fill().expand();

        //Add table to stage
        stage.addActor(table);


//        //Create Table
//        Table mainTable = new Table();
//        //Set table to fill stage
//        //mainTable.setFillParent(true);
//        //Set alignment of contents in the table.
//        mainTable.top();
//
//        //Create buttons
//        TextButton playButton = new TextButton("Play", skin);
//        TextButton optionsButton = new TextButton("Options", skin);
//        TextButton exitButton = new TextButton("Exit", skin);
//        //Add listeners to buttons
//        playButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                ((Game)Gdx.app.getApplicationListener()).setScreen(new ScreenMainMenu((Game)Gdx.app.getApplicationListener()));
//            }
//        });
//        exitButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                Gdx.app.exit();
//            }
//        });
//
//        //Add buttons to table
//        mainTable.add(playButton).width(500f).height(800f);
//        mainTable.row();
//        mainTable.add(optionsButton).width(500f).height(800f);
//        mainTable.row();
//        mainTable.add(exitButton).width(500f).height(800f);
//
//        ScrollPane scrollPane = new ScrollPane(mainTable);
//
//        Table table = new Table();
//        table.setFillParent(true);
//        table.add(scrollPane).fill().expand();
//
//        //Add table to stage
//        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        skin.dispose();
        atlas.dispose();
    }
}
