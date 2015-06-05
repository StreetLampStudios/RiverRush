package nl.tudelft.ti2806.riverrush.graphics.entity;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;

public class SceneDemo3 implements ApplicationListener {

    public class MyActor extends Actor {
        Texture texture = new Texture(Gdx.files.internal("data/ship.png"));
        public boolean started = false;

        public MyActor() {
            this.setBounds(this.getX(), this.getY(), this.texture.getWidth(),
                    this.texture.getHeight());
        }

        @Override
        public void draw(Batch batch, float alpha) {
            batch.draw(this.texture, this.getX(), this.getY(), this.getOriginX(),
                    this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(),
                    this.getScaleY(), this.getRotation(), 0, 0, this.texture.getWidth(),
                    this.texture.getHeight(), false, false);
        }

        @Override
        public void act(float delta) {
            for (Iterator<Action> iter = this.getActions().iterator(); iter.hasNext();) {
                iter.next().act(delta);
            }
        }
    }

    private Stage stage;

    @Override
    public void create() {
        this.stage = new Stage();
        Gdx.input.setInputProcessor(this.stage);

        MyActor myActor = new MyActor();

        MoveToAction moveAction = new MoveToAction();
        RotateToAction rotateAction = new RotateToAction();
        ScaleToAction scaleAction = new ScaleToAction();

        moveAction.setPosition(300f, 0f);
        moveAction.setDuration(5f);
        rotateAction.setRotation(90f);
        rotateAction.setDuration(5f);
        scaleAction.setScale(0.5f);
        scaleAction.setDuration(5f);

        myActor.addAction(moveAction);
        myActor.addAction(rotateAction);
        // myActor.addAction(scaleAction);

        this.stage.addActor(myActor);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act(Gdx.graphics.getDeltaTime());
        this.stage.draw();
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
}