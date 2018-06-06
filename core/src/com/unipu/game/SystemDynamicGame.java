package com.unipu.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.unipu.game.screens.MenuScreen;
import com.uwsoft.editor.renderer.SceneLoader;

public class SystemDynamicGame extends Game {

	public int startAge= 40, endAge = 65, saving = 1000, interest = 5, retirementFund = 0;

    public SceneLoader sceneLoader;

	//public  static final String TITLE = "Naslov";

	public SpriteBatch batch;

	@Override
	public void create () {

		//Viewport viewport = new FitViewport(480, 800);
        sceneLoader = new SceneLoader();
        //sceneLoader.loadScene("MainScene", viewport);
        batch = new SpriteBatch();

        setScreen(new MenuScreen(this));


    }

	@Override
	public void render () {
		//Gdx.gl.glClearColor(1, 1, 1, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //sceneLoader.getEngine().update(Gdx.graphics.getDeltaTime());

		super.render();
    }
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
