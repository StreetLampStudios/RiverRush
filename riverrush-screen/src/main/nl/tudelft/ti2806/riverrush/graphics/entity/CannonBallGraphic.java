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
    private static final float SIZE = 128;
    private static final float VELOCITY = 3f;

    private static final float HEIGHT = Gdx.graphics.getHeight();
    private static final float WIDTH = Gdx.graphics.getWidth();
    private static final float INIT_POS = (HEIGHT / 2) - (SIZE / 2);
    private static final float OFFSET_POS = 160f;
    private static final int NEGATIVE_MULTIPLIER = -2;
    private final double offset;
    private float origY;

    /**
     * Creates a new obstacle.
     *
     * @param off Configures the place from which the obstacle is fired. Must be between 0 and 1
     */
    public CannonBallGraphic(final double off) {
        this.offset = off;

    }

    /**
     * Actually adds the obstacle to the screen.
     */
    public void init() {
        this.setWidth(SIZE * 0.45f); // 0.45 is the percentage of the screen of his stage.
        this.setHeight(SIZE);
        float xpos = (float) ((INIT_POS + OFFSET_POS * this.offset) - SIZE / 2);
        this.origY = xpos;
        this.setPosition(WIDTH, xpos);

        Vector2 v = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
        v = this.localToStageCoordinates(v);

        this.setBounds(new Circle(v.x, v.y, this.getHeight() / 2));

        MoveToAction moveDown = new MoveToAction();
        // moveDown.setX(NEGATIVE_MULTIPLIER * SIZE);
        moveDown.setPosition(NEGATIVE_MULTIPLIER * SIZE, (float) ((INIT_POS + OFFSET_POS
                * this.offset) - SIZE / 2));
        moveDown.setDuration(VELOCITY);

        this.addAction(moveDown);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        batch.enableBlending();

        Vector2 v = new Vector2(this.getWidth() / 2, origY + this.getHeight() / 2);
        v = this.localToStageCoordinates(v);

        this.getBounds().setPosition(v.x, v.y);

        batch.draw(Assets.cannonball, this.getX(), origY, this.getOriginX(),
                this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(),
                this.getScaleY(), this.getRotation());
    }

    /**
     * @return true if the animation is done
     */
    public boolean isDone() {
        return this.getY() == NEGATIVE_MULTIPLIER * SIZE;
    }

}
