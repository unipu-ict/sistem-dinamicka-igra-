package com.unipu.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.unipu.game.SystemDynamicGame;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.additional.ButtonComponent;
import com.uwsoft.editor.renderer.systems.action.Actions;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.security.PublicKey;

public class MenuScreen implements Screen {

    private SystemDynamicGame game;
    private SceneLoader sceneLoader;

    public MenuScreen(SystemDynamicGame game) {
        this.game = game;

    }
    Entity btn_play, title;
    public static void scaleAnimate(Entity entity, float scaleFactor, float time){
        //    Actions.addAction(entity, Actions.scaleBy(scaleFactor, scaleFactor, time));
        DimensionsComponent dimension = ComponentRetriever.get(entity, DimensionsComponent.class);

        Actions.addAction(entity, Actions.parallel(
                Actions.scaleBy(scaleFactor, scaleFactor, time),
                Actions.moveBy(dimension.width*scaleFactor*-1/2, dimension.height*scaleFactor*-1/2 , time)));
    }

    @Override
    public void show() {
        Viewport viewport = new FitViewport(480, 800);
        sceneLoader = new SceneLoader();
        sceneLoader.loadScene("MainScene", viewport);

        final ItemWrapper root = new ItemWrapper(sceneLoader.getRoot());

        ButtonComponent buttonComponent = new ButtonComponent();
        sceneLoader.addComponentsByTagName("button", ButtonComponent.class);

        btn_play = root.getChild("btn_play").getEntity();
        //  scaleAnimate(panel, -1, 1);
        scaleAnimate(btn_play, -1, 0);
        scaleAnimate(btn_play, 1, 1);



        root.getChild("btn_play").getEntity().getComponent(ButtonComponent.class).addListener(new ButtonComponent.ButtonListener() {
            @Override
            public void touchUp() {

            }

            @Override
            public void touchDown() {

            }

            @Override
            public void clicked() {

                game.setScreen(new GameScene(game));
                //System.out.println("Hello");
            }
        });

    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        sceneLoader.getEngine().update(Gdx.graphics.getDeltaTime());

    }

    @Override
    public void resize(int width, int height) {

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

    }


}
