package nl.tudelft.ti2806.riverrush.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.google.inject.Inject;

/**
 * Game class represents the river bank.
 */
public class LittleBoat extends Actor {
    private final AssetManager manager;
    private float ypos;
    private float top_ypos;
    private static final String FILENAME = "data/bootje.jpg";

    private static final int END_REGIONX = 150; // 229;
    private static final int END_REGIONY = 425; // 138;
    private int progress;

    /**
     * Creates an river banks object with a given graphical representation.
     *
     * @param assetManager enables the object to retrieve its assets
     * @param xpos         represents the position of the little boat on the x axis
     * @param y_position   represents the position of the little boat on the y axis
     * @param width        represents the width of the little boat object
     * @param height       represents the height of the little boat object
     */
    @Inject
    public LittleBoat(
        final AssetManager assetManager,
        final float xpos,
        final float y_position,
        final float top_y_position,
        final float width,
        final float height
    ) {
        this.manager = assetManager;
        this.ypos = y_position;
        this.top_ypos = top_y_position;
        this.setPosition(xpos, y_position);
        this.setWidth(width);
        this.setHeight(height);
        this.progress = 0;
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

    /**
     * @param progres - Sets progress to this value
     */
    public void setProgress(final int progres) {
        this.progress = progres;
        MoveToAction action = new MoveToAction();
        action.setY(ypos + (progress/100f) * top_ypos);
        action.setDuration(0.5f);
        this.addAction(action);
    }
}
