package nl.tudelft.ti2806.riverrush.domain.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Actor representing a team has lost.
 */
public class LoseActor extends EndActor {

    private static final int END_REGIONX = 375;
    private static final int END_REGIONY = 360;

    /**
     * Creates an lose game object that presents itself on the screen to the losing party.
     *
     * @param assetManager enables the object to retrieve its assets.
     */
    public LoseActor(final AssetManager assetManager) {
        super();
        Texture tex = assetManager.get("assets/data/lose.png", Texture.class);
        this.setRegion(new TextureRegion(tex, 0, 0, END_REGIONX, END_REGIONY));
    }

}
