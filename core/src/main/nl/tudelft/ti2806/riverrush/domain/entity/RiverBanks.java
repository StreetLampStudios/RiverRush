package nl.tudelft.ti2806.riverrush.domain.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.inject.Inject;

public class RiverBanks extends Actor {

    private AssetManager manager;
    private float xPos;
    private float yPos;
    private float WIDTH;
    private float HEIGHT;

    @Inject
    public RiverBanks(AssetManager assetManager, float x, float y, float w,
            float h) {
        this.manager = assetManager;
        this.yPos = y;
        this.xPos = x;
        this.WIDTH = w;
        this.HEIGHT = h;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(this.manager.get("assets/data/grass.jpg", Texture.class),
                this.xPos, this.yPos, this.WIDTH, this.HEIGHT);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
