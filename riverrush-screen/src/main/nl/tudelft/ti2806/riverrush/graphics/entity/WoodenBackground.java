package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.graphics.Assets;

/**
 * Game class represents the river bank.
 */
public class WoodenBackground extends Actor {

    /**
     * Creates an river banks object with a given graphical representation.
     *
     * @param xpos   represents the position of the wooden background on the x axis
     * @param ypos   represents the position of the wooden background on the y axis
     * @param width  represents the width of the wooden background object
     * @param height represents the height of the wooden background object
     */
    @Inject
    public WoodenBackground(final float xpos, final float ypos,
                            final float width, final float height) {
        this.setPosition(xpos, ypos);
        this.setWidth(width);
        this.setHeight(height);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(Assets.woodFloor, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }
}
