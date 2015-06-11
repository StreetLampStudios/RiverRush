package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.inject.Inject;

public class DirectionFlag extends Actor {

    private static final int END_REGIONX = 16;
    private static final int END_REGIONY = 16;
    private static final float FLAG_WIDTH = 64;
    private static final float FLAG_HEIGHT = 32;
    private final AssetManager manager;

    /**
     * Creates a monkey object that represents player characters.
     *
     * @param assetManager enables the object to retrieve its assets
     * @param dispatcher Event dispatcher for dispatching events
     */
    @Inject
    public DirectionFlag(final AssetManager assetManager) {
        this.manager = assetManager;
        this.setWidth(FLAG_WIDTH);
        this.setHeight(FLAG_HEIGHT);

        // this.setOriginX(0);
        // this.setOriginY(0);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        Texture tex = this.manager.get("data/flag.png", Texture.class);
        TextureRegion region = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());

        Color color = this.getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.draw(region, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(),
                this.getRotation());

        batch.setColor(Color.WHITE);

        batch.disableBlending();
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }

}
