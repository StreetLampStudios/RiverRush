package nl.tudelft.ti2806.riverrush.graphics;

import nl.tudelft.ti2806.riverrush.domain.entity.RiverBanks;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.google.inject.Inject;

/**
 * Centerstage is a custom made stage that can be used to create the middle partioning of the
 * screen.
 *
 */
public class CenterStage extends Table {

    /**
     * Creates a center stage. This stage renders the middle section of the screen.
     *
     * @param assets
     *            refers to the manager that has made all loaded assets available for use.
     * @param width
     *            is the width size of the stage.
     * @param height
     *            is the height size of the stage.
     */
    @Inject
    public CenterStage(final AssetManager assets, final float width, final float height) {
        this.setBounds(0, 0, width, height);

        RiverBanks background = new RiverBanks(assets, 0, 0, width, height);
        this.addActor(background);

    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

}
