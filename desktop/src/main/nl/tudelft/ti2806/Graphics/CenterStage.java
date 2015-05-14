package nl.tudelft.ti2806.Graphics;

import nl.tudelft.ti2806.riverrush.domain.entity.RiverBanks;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.google.inject.Inject;

public class CenterStage extends AbstractStage {

    private RiverBanks background;

    @Inject
    public CenterStage(AssetManager assets, float width, float height) {
        this.setBounds(0, 0, width, height);

        this.background = new RiverBanks(assets, 0, 0, width, height);
        this.addActor(this.background);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

}