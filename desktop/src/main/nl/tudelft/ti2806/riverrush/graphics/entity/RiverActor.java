package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.graphics.Assets;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Game class representing a river.
 */
public class RiverActor extends Actor {
    private static final float BASE_FLOW_DURATION = 3f;
    private static final float MAX_FLOW_DURATION = 15f;

    private final float mid;
    private final float originalPosition;

    private MoveToAction moveDown;
    private MoveToAction moveUp;

    /**
     * Creates an river object with a given graphical representation.
     *
     * @param startPosition represents the position of the river on the y axis
     * @param width represents the width of the river object
     * @param height represents the height of the river object
     */
    @Inject
    public RiverActor(final float startPosition, final float width, final float height) {
        this.setPosition(0, height);
        this.setWidth(width);
        this.setHeight(height);
        this.originalPosition = startPosition;
        this.mid = width / 2;

        this.moveDown = new MoveToAction();
        this.moveDown.setPosition(0, height * -1);
        this.moveDown.setDuration(BASE_FLOW_DURATION);

        this.moveUp = new MoveToAction();
        this.moveUp.setPosition(0, startPosition);

        SequenceAction seq = sequence(this.moveUp, this.moveDown);
        RepeatAction rep = forever(seq);

        this.addAction(rep);

    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        batch.draw(Assets.river, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight() * 2, this.getScaleX(), this.getScaleY(),
                this.getRotation());
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }

    /**
     * Gives the middle of the river on the x-axis.
     *
     * @return a float that represents the middle of the river
     */
    public float getMid() {
        return this.mid;
    }

    public void updateFlow(double speed) {
        float speedMultiplier = (float) speed;
        float currentFlow = Math.min(MAX_FLOW_DURATION, BASE_FLOW_DURATION / speedMultiplier);
        // speed <= 0.2 ? 15f : BASE_FLOW_DURATION / speedMultiplier;
        float resetDuration = currentFlow * (1 - (this.getY() / (-1 * this.getHeight())));

        this.moveDown.setDuration(resetDuration);

        this.clearActions();

        MoveToAction newDown = new MoveToAction();
        newDown.setPosition(0, this.getHeight() * -1);
        newDown.setDuration(currentFlow);

        MoveToAction newUp = new MoveToAction();
        newUp.setPosition(0, this.originalPosition);

        SequenceAction newSeq = sequence(newUp, newDown);
        RepeatAction newRep = forever(newSeq);

        SequenceAction seq = sequence(this.moveDown, newRep);
        this.addAction(seq);
        this.moveDown.setTime(0f);

    }
}
