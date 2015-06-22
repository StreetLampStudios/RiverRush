package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.google.inject.Inject;

/**
 * Game class represents the river bank.
 */
public class LittleBoat extends Actor {
    private final TextureRegion image;
    private static final float PROGRESS_DIVISION = 100f;
    private static final float PROGRESS_MOVE_DURATION = 0.5f;
    private final float xpos;
    private final float topXPosition;

    private double progress;

    /**
     * Creates an river banks object with a given graphical representation.
     *
     * @param x       - represents the position of the little boat on the x axis
     * @param ypos    - represents the position of the little boat on the y axis
     * @param width   - represents the width of the little boat object
     * @param height  - represents the height of the little boat object
     * @param boat    - the texture that represents the boat
     * @param topXPos - the position of the top of the boat.
     */
    @Inject
    public LittleBoat(final float x, final float topXPos, final float ypos,
                      final float width, final float height, final TextureRegion boat) {
        this.xpos = x;
        this.topXPosition = topXPos;
        this.setPosition(x, ypos);
        this.setWidth(width);
        this.setHeight(height);
        this.image = boat;
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(image, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }

    /**
     * @param pro - Sets progress to this value
     */
    public void setProgress(final double pro) {
        this.progress = pro;
        float newY = (float) progress / PROGRESS_DIVISION;
        MoveToAction action = new MoveToAction();
        action.setPosition(this.xpos + newY * (this.topXPosition), this.getY());
        action.setDuration(PROGRESS_MOVE_DURATION);
        this.addAction(action);
    }
}
