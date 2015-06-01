package nl.tudelft.ti2806.riverrush.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.graphics.entity.RiverBanksActor;

/**
 * Centerstage is a custom made stage that can be used to create the middle partioning of the
 * screen.
 */
public class CenterStage extends Table {

    /**
     * The constructor of the center stage class.
     *
     * @param assets - specifies the assets
     * @param width  - specifies the width
     * @param height - specifies the height
     */
    @Inject
    public CenterStage(final AssetManager assets, final float width, final float height) {
        this.setBounds(0, 0, width, height);

        RiverBanksActor background = new RiverBanksActor(assets, 0, 0, width, height);
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
