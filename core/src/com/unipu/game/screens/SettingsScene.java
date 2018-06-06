package com.unipu.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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

public class SettingsScene implements Screen {

    private SystemDynamicGame game;
    private SceneLoader sceneLoader;

    public SettingsScene(SystemDynamicGame game) {
        this.game = game;

    }
    Entity btn_back_s;
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
        sceneLoader.loadScene("SettingsScene", viewport);

        final ItemWrapper root = new ItemWrapper(sceneLoader.getRoot());

        ButtonComponent buttonComponent = new ButtonComponent();
        sceneLoader.addComponentsByTagName("button", ButtonComponent.class);

        btn_back_s = root.getChild("btn_back_s").getEntity();
        //  scaleAnimate(panel, -1, 1);
        scaleAnimate(btn_back_s, -1, 0);
        scaleAnimate(btn_back_s, 1, 1);



        root.getChild("btn_back_s").getEntity().getComponent(ButtonComponent.class).addListener(new ButtonComponent.ButtonListener() {
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

        stage = new Stage();

        label_startAge = createLabel("40");
        label_endAge = createLabel("65");
        label_annualSaving = createLabel("1000");
        label_interest = createLabel("5");

        Label[] labels = new Label[]{
                label_interest, label_annualSaving, label_endAge,  label_startAge
        };

        int j =1;
        for (Label l: labels) {

            l.setPosition(220, (150 * j) + 20 );
            j++;
            stage.addActor(l);
            l.setAlignment(Align.center);
        }



//annual
        slider_annualSaving = createSlider(0, 5000, 100, 100, 600, 50);
        slider_annualSaving.setPosition(240, 700);
//interrst
        slider_interest = createSlider(0, 20, 1, 5, 600, 50);
        slider_interest.setPosition(240, 600);
//start
        slider_startAge = createSlider(0, 65, 1, 40, 600, 50);
        slider_startAge.setPosition(240, 500);
//end
        slider_endAge = createSlider(0, 65, 1, 100, 600, 50);
        slider_endAge.setPosition(240, 400);

       Slider[] sliders = new Slider[]{
          slider_interest, slider_annualSaving, slider_endAge,  slider_startAge
       };

        for (Slider s: sliders) {
            s.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println(slider_annualSaving.getValue());
                }
            });
        }



        int i = 1;
        for (Slider s: sliders) {
            stage.addActor(s);

            Container<Slider> container=new Container<Slider>(s);
            container.setTransform(true);   // for enabling scaling and rotation
            container.size(100, 60);
            container.setOrigin(container.getWidth() / 2, container.getHeight() / 2);
            container.setPosition(240,150 * i);
            container.setScale(3, 1);  //scale according to your requirement
            stage.addActor(container);
            i++;
        }

        slider_startAge.setValue((float)game.startAge);
        System.out.println(game.startAge);
        slider_endAge.setValue(game.endAge);
        slider_annualSaving.setValue(game.saving);
        slider_interest.setValue(game.interest);


        Gdx.input.setInputProcessor(stage);

    }
    Stage stage;
    Slider slider, slider_startAge, slider_endAge, slider_annualSaving, slider_interest;
    Label label_startAge, label_endAge, label_annualSaving, label_interest;

    private Slider createSlider(float min, float max, float step, float defaultValue, float width, float height){

        Slider.SliderStyle ss = new Slider.SliderStyle();

        ss.background = new TextureRegionDrawable(new TextureRegion(new Texture("slider/slider2.png")));
        ss.background.setMinHeight(height-40);
        ss.background.setMinWidth(width);

        ss.knob = new TextureRegionDrawable(new TextureRegion(new Texture("slider/knob2.png")));
        ss.knob.setMinHeight(height-25);
        ss.knob.setMinWidth(height-45);


        Slider  s = new Slider(min, max, step, false, ss);
        s.setValue(defaultValue);

        return s;
    }

    private Label createLabel(String text){
        Label.LabelStyle ss = new Label.LabelStyle();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("bitmapfont/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;

        BitmapFont font24 = generator.generateFont(parameter); // font size 24 pixels
        generator.dispose();
        ss.font = font24;
        Label  s = new Label(text, ss);
       // s.sizeBy(100, 50);
        s.setAlignment(Align.right);
        s.sizeBy(5);
        s.setColor(Color.WHITE);
        return s;
    }





    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sceneLoader.getEngine().update(Gdx.graphics.getDeltaTime());
        stage.draw();

        game.startAge = (int) slider_startAge.getValue();
        game.endAge = (int) slider_endAge.getValue();
        game.saving = (int) slider_annualSaving.getValue();
        game.interest = (int) slider_interest.getValue();

        label_startAge.setText(Integer.toString(game.startAge) + " year");
        label_endAge.setText(Integer.toString(game.endAge) + " year");
        label_annualSaving.setText(Integer.toString(game.saving) + " $");
        label_interest.setText(Integer.toString(game.interest) + " % / year");
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
