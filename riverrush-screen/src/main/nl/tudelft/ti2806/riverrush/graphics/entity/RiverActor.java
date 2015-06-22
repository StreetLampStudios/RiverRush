package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
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
    private static final float BASE_FLOW_DURATION = 6f;
    private static final float MAX_FLOW_DURATION = 15f;

    private final float mid;
    private final float originalPosition;

    private MoveToAction flow;
    private MoveToAction reset;

    /**
     * Creates an river object with a given graphical representation.
     *
     * @param startPosition represents the position of the river on the y axis
     * @param width         represents the width of the river object
     * @param height        represents the height of the river object
     */
    @Inject
    public RiverActor(final float startPosition, final float width, final float height) {
        this.setPosition(0, 0);
        this.setWidth(width);
        this.setHeight(height);
        this.originalPosition = startPosition;
        this.mid = height / 2;

        this.flow = new MoveToAction();
        this.flow.setPosition(width * -1, 0);
        this.flow.setDuration(BASE_FLOW_DURATION);

        this.reset = new MoveToAction();
        this.reset.setPosition(startPosition, 0);

        SequenceAction seq = sequence(this.reset, this.flow);
        RepeatAction rep = forever(seq);

        this.addAction(rep);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        batch.draw(Assets.river, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                this.getWidth() * 2, this.getHeight(), this.getScaleX(), this.getScaleY(),
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

    public void updateFlow(final double speed) {
        float speedMultiplier = (float) speed;
        float currentFlow = Math.min(MAX_FLOW_DURATION, BASE_FLOW_DURATION / speedMultiplier);
        // speed <= 0.2 ? 15f : BASE_FLOW_DURATION / speedMultiplier;
        float resetDuration = currentFlow * (1 - (this.getX() / (-1 * this.getWidth())));

        this.flow.setDuration(resetDuration);

        this.clearActions();

        MoveToAction newFlow = new MoveToAction();
        newFlow.setPosition(this.getWidth() * -1, 0);
        newFlow.setDuration(currentFlow);

        MoveToAction newReset = new MoveToAction();
        newReset.setPosition(this.originalPosition, 0);

        SequenceAction newSeq = sequence(newReset, newFlow);
        RepeatAction newRep = forever(newSeq);

        SequenceAction seq = sequence(this.flow, newRep);
        this.addAction(seq);
    }
}
