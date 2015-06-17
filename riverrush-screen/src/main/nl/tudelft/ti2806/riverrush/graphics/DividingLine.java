package nl.tudelft.ti2806.riverrush.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.inject.Inject;

/**
 * Game class represents the river bank.
 */
public class DividingLine extends Actor {

    /**
     * Creates an river banks object with a given graphical representation.
     *
     * @param xpos         represents the position of the line on the x axis
     * @param ypos         represents the position of the line on the y axis
     * @param width        represents the width of the line object
     * @param height       represents the height of the line object
     */
    @Inject
    public DividingLine(
        final float xpos,
        final float ypos,
        final float width,
        final float height
    ) {
        this.setPosition(xpos, ypos);
        this.setWidth(width);
        this.setHeight(height);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(Assets.line, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }
}
