package com.unipu.game.screens;

import com.badlogic.ashley.core.Component;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
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

public class GameScene implements Screen {

    private SystemDynamicGame game;
    private SceneLoader sceneLoader;


    public GameScene(SystemDynamicGame game){
        this.game=game;
    }

    @Override
    public void show() {
        Viewport viewport = new FitViewport(480, 800);
        sceneLoader = new SceneLoader();
        sceneLoader.loadScene("GameScene", viewport);

        final ItemWrapper root = new ItemWrapper(sceneLoader.getRoot());

        ButtonComponent buttonComponent = new ButtonComponent();
        sceneLoader.addComponentsByTagName("button", ButtonComponent.class);

        root.getChild("btn_start").getEntity().getComponent(ButtonComponent.class).addListener(new ButtonComponent.ButtonListener() {
            @Override
            public void touchUp() {

            }

            @Override
            public void touchDown() {

            }

            @Override
            public void clicked() {

                // game.setScreen(new GameScene(game));
                System.out.println("Result: ");
                System.out.println(Izracunaj());
            }
        });

        root.getChild("btn_settings").getEntity().getComponent(ButtonComponent.class).addListener(new ButtonComponent.ButtonListener() {
            @Override
            public void touchUp() {

            }

            @Override
            public void touchDown() {

            }

            @Override
            public void clicked() {

                game.setScreen(new SettingsScene(game));
                System.out.println("Settings");

            }
        });

        root.getChild("btn_question").getEntity().getComponent(ButtonComponent.class).addListener(new ButtonComponent.ButtonListener() {
            @Override
            public void touchUp() {

            }

            @Override
            public void touchDown() {

            }

            @Override
            public void clicked() {

                 game.setScreen(new QuestionScene(game));
                System.out.println("question");
            }
        });





        btn_start = root.getChild("btn_start").getEntity();
        btn_ques = root.getChild("btn_question").getEntity();
        btn_settings = root.getChild("btn_settings").getEntity();


        scaleAnimate(btn_start, -1, 0);
        scaleAnimate(btn_start, 1, 1);

        scaleAnimate(btn_ques, -1, 0);
        scaleAnimate(btn_ques, 1, 1);

        scaleAnimate(btn_settings, -1, 0);
        scaleAnimate(btn_settings, 1, 1);

        stage = new Stage();

        label_result = createLabel("");
        label_result.setPosition(240, 650);
        label_result.setAlignment(Align.center);
        stage.addActor(label_result);

        Gdx.input.setInputProcessor(stage);
    }
    Entity btn_start, btn_settings, btn_ques, arrow, pnl_settings, btn_back_s;
    Stage stage;
    Label label_result;


    public static void scaleAnimate(Entity entity, float scaleFactor, float time){
        //    Actions.addAction(entity, Actions.scaleBy(scaleFactor, scaleFactor, time));
        DimensionsComponent dimension = ComponentRetriever.get(entity, DimensionsComponent.class);

        Actions.addAction(entity, Actions.parallel(
                Actions.scaleBy(scaleFactor, scaleFactor, time),
                Actions.moveBy(dimension.width*scaleFactor*-1/2, dimension.height*scaleFactor*-1/2 , time)));
    }

    private com.badlogic.gdx.scenes.scene2d.ui.Label createLabel(String text){
        com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle ss = new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("bitmapfont/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;

        BitmapFont font24 = generator.generateFont(parameter); // font size 24 pixels
        generator.dispose();
        ss.font = font24;
        com.badlogic.gdx.scenes.scene2d.ui.Label s = new Label(text, ss);
        // s.sizeBy(100, 50);
        s.setAlignment(Align.right);
        s.sizeBy(5);
        s.setColor(Color.WHITE);
        return s;
    }


    int Izracunaj(){
        int value = 0;
        int saving_interest = 0;
        for (int i = game.startAge; i<= game.endAge; i++){
            saving_interest = game.saving + value * game.interest/100;
            value = value + (saving_interest);

        }

        final_result=value;
        game.retirementFund =0;

        System.out.println("final resault: " + final_result);

        if(final_result <= 0)
            label_result.setText("Wrong values!!!");
        else
            label_result.setText("Retirement fund:\n" + Integer.toString(final_result));


        return value;
    }
    int final_result=0;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sceneLoader.getEngine().update(Gdx.graphics.getDeltaTime());

        stage.draw();


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
        stage.dispose();
    }
}
