package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.graphics.Assets;

public class RockGraphic extends AbstractObstacle {

    /**
     * Size of the graphic.
     */
    private static final float SIZE = 256;
    private static final float VELOCITY = ((Gdx.graphics.getHeight() + (SIZE * 2)) / 720f) * 2;
    // 720f = velocity river

    private static final float DESKTOP_HEIGHT = Gdx.graphics.getHeight();
    private static final float DESKTOP_WIDTH = Gdx.graphics.getWidth();

    private static final int NEGATIVE_MULTIPLIER = -2;

    private final float offset;
    private final Direction direction;

    /**
     * Creates a new obstacle.
     *
     * @param dir           Configures the place from which the obstacle is fired. Must be between 0 and 1
     */
    public RockGraphic(final Direction dir) {

        this.direction = dir;
        if (dir == Direction.LEFT) {
            this.offset = 0.3f;
        } else if (dir == Direction.RIGHT) {
            this.offset = 0.7f;
        } else {
            this.offset = 0.5f;
        }
    }

    /**
     * Actually adds the obstacle to the screen.
     */
    public void init() {
        this.setWidth(SIZE);
        this.setHeight(SIZE * DESKTOP_HEIGHT / DESKTOP_WIDTH / 2);
        this.setPosition((DESKTOP_WIDTH * this.offset) - SIZE / 2, DESKTOP_HEIGHT); // 1080

        Vector2 v = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
        this.localToStageCoordinates(v);

        this.setBounds(new Circle(v.x, v.y, this.getHeight() / 2));

        MoveToAction moveDown = new MoveToAction();
        moveDown.setPosition(this.getX(), NEGATIVE_MULTIPLIER * SIZE); // 1592 distance
        moveDown.setDuration(VELOCITY);

        this.addAction(moveDown);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        batch.enableBlending();

        Vector2 v = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
        this.localToStageCoordinates(v);

        this.getBounds().setPosition(v.x, v.y);

        batch.draw(Assets.iceberg, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
            this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(),
            this.getRotation());
    }

    /**
     * @return true if the animation is done
     */
    public boolean isDone() {
        return this.getY() == NEGATIVE_MULTIPLIER * SIZE;
    }

    public Direction getDirection() {
        return this.direction;
    }

}
