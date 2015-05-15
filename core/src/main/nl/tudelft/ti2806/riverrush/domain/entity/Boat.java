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

    @Inject
    public Boat(AssetManager assetManager, float x, float y, float w, float h) {
        this.manager = assetManager;
        this.setX(x);
        this.setY(y);
        this.setWidth(w);
        this.setHeight(h);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Texture tex = this.manager.get("assets/data/shipv2.png", Texture.class);
        TextureRegion region = new TextureRegion(tex, 0, 0, 584, 1574);
        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
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
