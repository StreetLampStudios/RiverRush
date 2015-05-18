package nl.tudelft.ti2806.Graphics;

import nl.tudelft.ti2806.riverrush.domain.entity.Boat;
import nl.tudelft.ti2806.riverrush.domain.entity.River;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.google.inject.Inject;

public class SideStage extends AbstractStage {

    private Boat boat;
    private River river;

    @Inject
    public SideStage(AssetManager assets, float width, float height,
            boolean left) {
        this.setBounds(0, 0, width, height);

        this.river = new River(assets, 0, 1920, 1080, left);
        this.boat = new Boat(assets, this.river.getMid() - 300, 150, 600, 600);

        this.addActor(this.river);
        this.addActor(this.boat);

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
