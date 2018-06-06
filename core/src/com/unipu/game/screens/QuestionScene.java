package com.unipu.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.unipu.game.SystemDynamicGame;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.additional.ButtonComponent;
import com.uwsoft.editor.renderer.systems.action.Actions;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

public class QuestionScene implements Screen {

    private SystemDynamicGame game;
    private SceneLoader sceneLoader;

    public QuestionScene(SystemDynamicGame game) {
        this.game = game;

    }
    Entity btn_back_q;
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
        sceneLoader.loadScene("QuestionScene", viewport);

        final ItemWrapper root = new ItemWrapper(sceneLoader.getRoot());

        ButtonComponent buttonComponent = new ButtonComponent();
        sceneLoader.addComponentsByTagName("button", ButtonComponent.class);

        btn_back_q = root.getChild("btn_back_q").getEntity();
        //  scaleAnimate(panel, -1, 1);
        scaleAnimate(btn_back_q, -1, 0);
        scaleAnimate(btn_back_q, 1, 1);



        root.getChild("btn_back_q").getEntity().getComponent(ButtonComponent.class).addListener(new ButtonComponent.ButtonListener() {
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
    Skin skin;
    TextField text;

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
