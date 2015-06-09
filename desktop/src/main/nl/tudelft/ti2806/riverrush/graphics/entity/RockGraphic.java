package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import nl.tudelft.ti2806.riverrush.desktop.MainDesktop;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;

public class RockGraphic extends AbstractObstacle {

    /**
     * Size of the graphic.
     */
    private static final double SIZE = 256;
    private static final float VELOCITY = 5f;

    private final AssetManager assets;
    private static final double HEIGHT = MainDesktop.getHeight();
    private static final double WIDTH = MainDesktop.getWidth();
    private static final double INIT_POS = 800.0;
    private static final double OFFSET_POS = 320.0;
    private static final int NEGATIVE_MULTIPLIER = -2;
    private static final int TEXTURE_SIZE_X = 679;
    private static final int TEXTURE_SIZE_Y = 436;
    private static final float HITBOX_OFFSET_X = 0.1f;
    private static final float HITBOX_OFFSET_Y = 0.1f;

    private final double offset;
    private final Direction direction;
    private Circle bounds;

    /**
     * Creates a new obstacle.
     *
     * @param assetsManager refers to the manager that has made all loaded assets available for use.
     * @param off Configures the place from which the obstacle is fired. Must be between 0 and 1
     */
    public RockGraphic(final AssetManager assetsManager, final Direction dir) {
        this.assets = assetsManager;

        this.direction = dir;
        if (dir == Direction.LEFT) {
            this.offset = 0.2;
        } else if (dir == Direction.RIGHT) {
            this.offset = 0.8;
        } else {
            this.offset = 0.5;
        }
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
        Texture tex = this.assets.get("data/iceberg.png", Texture.class);
        TextureRegion region = new TextureRegion(tex, 0, 0, TEXTURE_SIZE_X, TEXTURE_SIZE_Y);
        batch.enableBlending();

        Vector2 v = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
        this.localToStageCoordinates(v);

        this.bounds = new Circle(v.x, v.y, this.getHeight() / 2);

        batch.draw(region, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(),
                this.getRotation());
    }

    /**
     * @return true if the animation is done
     */
    public boolean isDone() {
        return this.getY() == NEGATIVE_MULTIPLIER * SIZE;
    }

    public boolean calculateCollision(final BoatGroup boat) {
        boolean ret = this.bounds.overlaps(boat.getBounds());
        return ret;
        // Vector2 v = new Vector2(0, 0);
        // v = boat.localToStageCoordinates(v);
        // Vector2 o = new Vector2(0, 0);
        // o = this.localToStageCoordinates(o);
        //
        // float boatx = v.x + (boat.getWidth() * HITBOX_OFFSET_X);
        // float boatxedge = v.x + boat.getWidth() - (boat.getWidth() * HITBOX_OFFSET_X);
        // float boaty = v.y + (boat.getHeight() * HITBOX_OFFSET_Y);
        // float boatyedge = v.y + boat.getHeight() - (boat.getHeight() * HITBOX_OFFSET_Y);
        // float[] x = {boatx, boatxedge};
        // float[] y = {boaty, boatyedge};
        //
        // for (float edgex : x) {
        // for (float edgey : y) {
        // if (edgex < o.x + this.getWidth() && edgex > o.x && edgey < o.y + this.getHeight()
        // && edgey > o.y) {
        // return true;
        // }
        // }
        // }
        // return false;
    }

    public Direction getDirection() {
        return this.direction;
    }

}
