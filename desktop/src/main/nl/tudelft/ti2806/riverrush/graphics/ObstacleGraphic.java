package nl.tudelft.ti2806.riverrush.graphics;

import nl.tudelft.ti2806.riverrush.desktop.MainDesktop;
import nl.tudelft.ti2806.riverrush.domain.entity.Monkey;
import nl.tudelft.ti2806.riverrush.domain.entity.Obstacle;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

/**
 * Adds obstacles on the screen
 */
public class ObstacleGraphic extends Actor implements Obstacle {

    /**
     * Size of the graphic.
     */
    private static final double SIZE = 256;
    private static final float VELOCITY = 3f;

    private final AssetManager assets;
    private final double HEIGHT = MainDesktop.HEIGHT;
    private final double WIDTH = MainDesktop.WIDTH;

    /**
     * Creates a new obstacle
     *
     * @param assetsManager
     *            - Manager of assets
     * @param offset
     *            - Shoots from different parts of the screen. Must be between 0
     *            and 1
     */
    public ObstacleGraphic(final AssetManager assetsManager, final double offset) {
        this.assets = assetsManager;
        this.setWidth((float) SIZE);
        this.setHeight((float) (SIZE * this.HEIGHT / this.WIDTH) / 2);
        this.setPosition((float) ((800.0 + 320.0 * offset) - SIZE / 2),
                (float) this.HEIGHT);

        MoveToAction moveDown = new MoveToAction();
        moveDown.setPosition((float) (this.WIDTH / 2 - SIZE / 2),
                (float) (-2 * SIZE));
        moveDown.setDuration(VELOCITY);

        this.addAction(moveDown);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        Texture tex = this.assets.get("data/cannonball.png", Texture.class);
        TextureRegion region = new TextureRegion(tex, 0, 0, 512, 512);
        batch.enableBlending();
        batch.draw(region, this.getX(), this.getY(), this.getOriginX(),
                this.getOriginY(), this.getWidth(), this.getHeight(),
                this.getScaleX(), this.getScaleY(), this.getRotation());
    }

    /**
     * @return true if the animation is done
     */
    public boolean isDone() {
        // -2
        if (this.getY() == -2 * SIZE) {
            return true;
        }
        return false;
    }

    public boolean collide(Monkey monk) {
        float monkx = monk.getX();
        float monkxedge = monk.getX() + monk.getWidth();
        float monky = monk.getY();
        float monkyedge = monk.getY() + monk.getHeight();
        float[] x = { monkx, monkxedge };
        float[] y = { monky, monkyedge };

        for (float edgex : x) {
            for (float edgey : y) {
                if (edgex < this.getX() + this.getWidth()
                        && edgex > this.getX()
                        && edgey < this.getY() + this.getHeight()
                        && edgey > this.getY()) {
                    return true;
                }
            }
        }
        return false;

    }

}
