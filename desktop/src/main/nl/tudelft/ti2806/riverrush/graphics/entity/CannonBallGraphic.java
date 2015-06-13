package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import nl.tudelft.ti2806.riverrush.graphics.Assets;

/**
 * Adds an obstacle on the screen.
 */
public class CannonBallGraphic extends AbstractObstacle {

    /**
     * Size of the graphic.
     */
    private static final double SIZE = 256;
    private static final float VELOCITY = 3f;

    private static final double HEIGHT = Gdx.graphics.getHeight();
    private static final double WIDTH = Gdx.graphics.getWidth();
    private static final double INIT_POS = 800.0;
    private static final double OFFSET_POS = 320.0;
    private static final int NEGATIVE_MULTIPLIER = -2;
    private final double offset;
    private Circle bounds;

    /**
     * Creates a new obstacle.
     *
     * @param off           Configures the place from which the obstacle is fired. Must be between 0 and 1
     */
    public CannonBallGraphic(final double off) {
        this.offset = off;

    }

    /**
     * Actually adds the obstacle to the screen.
     */
    public void init() {
        this.setWidth((float) SIZE);
        this.setHeight((float) (SIZE * HEIGHT / WIDTH) / 2);
        this.setPosition((float) ((INIT_POS + OFFSET_POS * this.offset) - SIZE / 2), (float) HEIGHT);

        Vector2 v = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
        this.localToStageCoordinates(v);

        this.bounds = new Circle(v.x, v.y, this.getHeight() / 2);

        MoveToAction moveDown = new MoveToAction();
        moveDown.setPosition((float) (WIDTH / 2 - SIZE / 2), (float) (NEGATIVE_MULTIPLIER * SIZE));
        moveDown.setDuration(VELOCITY);

        this.addAction(moveDown);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        batch.enableBlending();

        Vector2 v = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
        this.localToStageCoordinates(v);

        this.bounds = new Circle(v.x, v.y, this.getHeight() / 2);

        batch.draw(Assets.cannonball, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
            this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(),
            this.getRotation());
    }

    /**
     * @return true if the animation is done
     */
    public boolean isDone() {
        return this.getY() == NEGATIVE_MULTIPLIER * SIZE;
    }

    /**
     * Calculates whether or not this obtacle is currently colliding with the given monkey. We find
     * a collision to be true if any part of the monkey is within the bounds of the obstacle.
     *
     * @param monk refers to the monkey for which the collision has to be calculated
     * @return true if collision occurs, false if it doesn't.
     */
    public boolean calculateCollision(final AnimalActor monk) {
        boolean ret = this.bounds.overlaps(monk.getBounds());
        return ret;
    }
}
