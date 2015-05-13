package nl.tudelft.ti2806.Graphics;

import nl.tudelft.ti2806.riverrush.domain.entity.Boat;
import nl.tudelft.ti2806.riverrush.domain.entity.River;
import nl.tudelft.ti2806.riverrush.domain.entity.RiverBanks;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.inject.Inject;

public class SideStage extends AbstractStage {

    private Boat boat;
    private River river;
    private RiverBanks background;

    @Inject
    public SideStage(AssetManager assets, float width, float height,
            String fileName) {
        this.setBounds(0, 0, width, height);
        // Texture tex = new Texture(Gdx.files.internal(fileName));
        // this.background = new Image(tex);

        SpriteBatch batch = new SpriteBatch();

        this.background = new RiverBanks(assets, 0, 0, width, height);
        this.boat = new Boat(assets, width / 2, height / 2, 200, 200, batch);
        this.river = new River(assets, 192, 0, 1536, 1080);
        this.addActor(this.background);
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
