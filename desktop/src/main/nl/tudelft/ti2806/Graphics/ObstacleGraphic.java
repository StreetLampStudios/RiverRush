package nl.tudelft.ti2806.Graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import nl.tudelft.ti2806.riverrush.desktop.DesktopLauncher;
import nl.tudelft.ti2806.riverrush.domain.entity.Obstacle;

/**
 * Adds obstacles on the screen
 */
public class ObstacleGraphic extends Actor implements Obstacle {

    /**
     * Size of the graphic.
     */
    private static final double SIZE = 256;
    private static final float DURATIONOFANIMATION = 3f;

    private final AssetManager assets;
    private final double HEIGHT = DesktopLauncher.HEIGHT;
    private final double WIDTH = DesktopLauncher.WIDTH;


    /**
     * Creates a new obstacle
     * @param assetsManager - Manager of assets
     * @param offset - Shoots from different parts of the screen. Must be between 0 and 1
     */
    public ObstacleGraphic(final AssetManager assetsManager, final double offset) {
        this.assets = assetsManager;
        setWidth((float)SIZE);
        setHeight((float) (SIZE * HEIGHT / WIDTH) / 2);
        setPosition((float)((800.0 + 320.0 * offset) - SIZE/2), (float) HEIGHT);


        MoveToAction moveDown = new MoveToAction();
        moveDown.setPosition((float) (WIDTH/2 - SIZE / 2), (float)( -2 * SIZE));
        moveDown.setDuration(DURATIONOFANIMATION);

        addAction(moveDown);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        Texture tex = this.assets.get("assets/data/cannonball.png", Texture.class);
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
        //-2
        if (getY() == -2 * SIZE) {
            return true;
        }
        return false;
    }
}
