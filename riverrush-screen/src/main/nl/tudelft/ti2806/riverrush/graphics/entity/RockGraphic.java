package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.graphics.Assets;

/**
 * Create a rock obstacle.
 */
public class RockGraphic extends AbstractObstacle {

    /**
     * Size of the graphic.
     */
    private static final float SIZE = 256;
    private static final float RIVER_VELOCITY = 720f;
    private static final float VELOCITY = ((Gdx.graphics.getHeight() + (SIZE * 2)) / RIVER_VELOCITY) * 2;
    // 720f = velocity river

    private static final float DESKTOP_HEIGHT = Gdx.graphics.getHeight();
    private static final float DESKTOP_WIDTH = Gdx.graphics.getWidth();

    private static final int NEGATIVE_MULTIPLIER = -2;

    private final float offset;
    private final Direction direction;
    private static final float LEFT_ROCK_OFFSET = 0.3f;
    private static final float RIGHT_ROCK_OFFSET = 0.7f;
    private static final float NEUTRAL_ROCK_OFFSET = 0.5f;
    private static final float ROCK_DESTROYED_SCALE = 0.2f;
    private static final float DESTROY_ROCK_DURATION = 0.5f;
    private static final float STAGE_PARTITION = 0.45f;

    /**
     * Creates a new obstacle.
     *
     * @param dir Configures the place from which the obstacle is fired. Must be between 0 and 1
     */
    public RockGraphic(final Direction dir) {

        this.direction = dir;
        if (dir == Direction.LEFT) {
            this.offset = LEFT_ROCK_OFFSET;
        } else if (dir == Direction.RIGHT) {
            this.offset = RIGHT_ROCK_OFFSET;
        } else {
            this.offset = NEUTRAL_ROCK_OFFSET;
        }
    }

    /**
     * Adds an action that shows the rock being destroyed.
     */
    public void getDestroyed() {
    	ScaleToAction scale = new ScaleToAction();
    	scale.setScale(ROCK_DESTROYED_SCALE);
    	scale.setDuration(DESTROY_ROCK_DURATION);

    	AlphaAction fade = new AlphaAction();
    	fade.setAlpha(0f);
    	fade.setDuration(DESTROY_ROCK_DURATION);

    	this.addAction(scale);
    	this.addAction(fade);
    }

    /**
     * Actually adds the obstacle to the screen.
     */
    public void init() {
        this.setWidth(SIZE * STAGE_PARTITION); // 0.45 is the percentage of the screen of his stage.
        this.setHeight(SIZE);
        this.setPosition(DESKTOP_WIDTH, (DESKTOP_HEIGHT * this.offset) - SIZE / 2); // 1080
        this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);

        Vector2 v = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
        this.localToStageCoordinates(v);

        this.setBounds(new Circle(v.x, v.y, this.getHeight() / 2));

        MoveToAction moveDown = new MoveToAction();
        moveDown.setPosition(NEGATIVE_MULTIPLIER * SIZE, this.getY()); // 1592 distance
        moveDown.setDuration(VELOCITY);

        this.addAction(moveDown);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        batch.enableBlending();

        Vector2 v = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
        this.localToStageCoordinates(v);

        this.getBounds().setPosition(v.x, v.y);

        Color color = this.getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        batch.draw(Assets.iceberg, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(),
                this.getRotation());


        batch.setColor(Color.WHITE);
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
