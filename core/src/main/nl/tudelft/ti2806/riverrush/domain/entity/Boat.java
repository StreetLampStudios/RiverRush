package nl.tudelft.ti2806.riverrush.domain.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.inject.Inject;

/**
 * Represents a boat that the animals row on.
 */
public class Boat extends Actor implements Group {

    private AssetManager manager;

    // private float xPos;
    // private float yPos;
    // private float WIDTH;
    // private float HEIGHT;

    // For Testing
    // private SpriteBatch batch;

    @Inject
    public Boat(AssetManager assetManager, float x, float y, float w, float h) {
        this.manager = assetManager;
        // this.xPos = x;
        // this.yPos = y;
        // this.WIDTH = w;
        // this.HEIGHT = h;
        this.setX(x);
        this.setY(y);
        // this.setOriginX(0);
        // this.setOriginY(0);
        this.setWidth(w);
        this.setHeight(h);

        // this.batch = batch;

        // RotateToAction action = new RotateToAction();
        // action.setRotation(90f);
        // action.setDuration(5f);
        // this.addAction(action);
        //
        // ScaleToAction scale = new ScaleToAction();
        // scale.setScale(0.5f);
        // scale.setDuration(5f);
        // this.addAction(scale);

        // MoveToAction move = new MoveToAction();
        // move.setPosition(1000, 1000);
        // move.setDuration(5f);
        // this.addAction(move);
        // this.addAction(Actions.moveTo(100f, 100f, 5f));

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Texture tex = this.manager.get("assets/data/ship2.png", Texture.class);
        TextureRegion region = new TextureRegion(tex, 0, 0, 345, 792); // 584,
                                                                       // 1574
        // batch.draw(tex, this.xPos, this.yPos, this.WIDTH, this.HEIGHT);

        // batch.draw(region, x, y, originX, originY, width, height, scaleX,
        // scaleY, rotation);

        // batch.draw(region, this.xPos, this.yPos, 0f, 0f, this.WIDTH,
        // this.HEIGHT, this.getScaleX(), this.getScaleY(),
        // this.getRotation());
        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); // GL11.GL_SRC_ALPHA,
        // GL11.GL_ONE_MINUS_SRC_ALPHA

        batch.draw(region, this.getX(), this.getY(), this.getOriginX(),
                this.getOriginY(), this.getWidth(), this.getHeight(),
                this.getScaleX(), this.getScaleY(), this.getRotation());
        batch.disableBlending();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

}
