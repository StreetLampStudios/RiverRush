package nl.tudelft.ti2806.riverrush.graphics.entity;

import nl.tudelft.ti2806.riverrush.desktop.MainDesktop;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

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

    /**
     * Creates a new obstacle.
     *
     * @param assetsManager refers to the manager that has made all loaded assets available for use.
     * @param off Configures the place from which the obstacle is fired. Must be between 0 and 1
     */
    public RockGraphic(final AssetManager assetsManager, final double off) {
        this.assets = assetsManager;
        this.offset = off;
    }

    /**
     * Actually adds the obstacle to the screen.
     */
    public void init() {
        this.setWidth((float) SIZE);
        this.setHeight((float) (SIZE * HEIGHT / WIDTH) / 2);
        this.setPosition((float) ((INIT_POS + OFFSET_POS * this.offset) - SIZE / 2), (float) HEIGHT);

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
        float boatx = boat.getX() + (boat.getWidth() * HITBOX_OFFSET_X);
        float boatxedge = boat.getX() + boat.getWidth() - (boat.getWidth() * HITBOX_OFFSET_X);
        float boaty = boat.getY() + (boat.getHeight() * HITBOX_OFFSET_Y);
        float boatyedge = boat.getY() + boat.getHeight() - (boat.getHeight() * HITBOX_OFFSET_Y);
        float[] x = {boatx, boatxedge};
        float[] y = {boaty, boatyedge};

        for (float edgex : x) {
            for (float edgey : y) {
                if (edgex < this.getX() + this.getWidth() && edgex > this.getX()
                        && edgey < this.getY() + this.getHeight() && edgey > this.getY()) {
                    return true;
                }
            }
        }
        return false;

    }

}
