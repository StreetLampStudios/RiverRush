package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WinObject extends EndObject {

    public WinObject(AssetManager assetManager) {
        super(assetManager);
        Texture tex = assetManager.get("assets/data/win.png", Texture.class);
        this.region = new TextureRegion(tex, 0, 0, 313, 232);

    }

}