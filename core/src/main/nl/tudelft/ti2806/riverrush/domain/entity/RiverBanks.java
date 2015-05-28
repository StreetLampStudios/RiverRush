package nl.tudelft.ti2806.riverrush.domain.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.inject.Inject;

/**
 * Game class represents the river bank.
 */
public class RiverBanks extends Actor {

    private final AssetManager manager;
    private static final String FILENAME = "data/field.jpg";

    private static final int END_REGIONX = 103; // 229;
    private static final int END_REGIONY = 314; // 138;

    /**
     * Creates an river banks object with a given graphical representation.
     *
     * @param assetManager enables the object to retrieve its assets
     * @param xpos         represents the position of the river banks on the x axis
     * @param ypos         represents the position of the river banks on the y axis
     * @param width        represents the width of the river banks object
     * @param height       represents the height of the river banks object
     */
    @Inject
    public RiverBanks(
        final AssetManager assetManager,
        final float xpos,
        final float ypos,
        final float width,
        final float height
    ) {
        this.manager = assetManager;
        this.setPosition(xpos, ypos);
        this.setWidth(width);
        this.setHeight(height);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        super.draw(batch, parentAlpha);
        Texture tex = this.manager.get(FILENAME, Texture.class);
        TextureRegion region = new TextureRegion(tex, 0, 0, END_REGIONX, END_REGIONY);
        batch.draw(region, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }
}
