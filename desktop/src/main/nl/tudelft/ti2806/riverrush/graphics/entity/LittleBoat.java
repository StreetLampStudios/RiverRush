package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.graphics.Assets;

/**
 * Game class represents the river bank.
 */
public class LittleBoat extends Actor {
    private float ypos;
    private float top_ypos;

    private double progress;

    /**
     * Creates an river banks object with a given graphical representation.
     *
     * @param xpos         represents the position of the little boat on the x axis
     * @param y_position   represents the position of the little boat on the y axis
     * @param width        represents the width of the little boat object
     * @param height       represents the height of the little boat object
     */
    @Inject
    public LittleBoat(final float xpos, final float y_position,
                      final float top_y_position, final float width, final float height) {
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
        batch.draw(Assets.bootje, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }

    /**
     * @param progres - Sets progress to this value
     */
    public void setProgress(final double progres) {
        this.progress = progres;
        float newY = new Double(progres / 100).floatValue();
        MoveToAction action = new MoveToAction();
        action.setPosition(this.getX(), this.ypos + newY * (this.top_ypos - this.ypos));
        action.setDuration(0.5f);
        this.addAction(action);
    }
}
