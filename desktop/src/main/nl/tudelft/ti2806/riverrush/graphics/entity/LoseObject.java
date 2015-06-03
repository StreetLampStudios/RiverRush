package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LoseObject extends EndObject {

    public LoseObject(AssetManager assetManager) {
        super(assetManager);
        Texture tex = assetManager.get("assets/data/lose.png", Texture.class);
        this.region = new TextureRegion(tex, 0, 0, 375, 360);

    }

}