package nl.tudelft.ti2806.monkeyrush.desktop;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {

    public static AssetManager manager = new AssetManager();

    public static void loading() {

        // manager.load("test", Texture.class);
        manager.load("data/boat.jpg", Texture.class);

        manager.finishLoading();
    }

    public static boolean update() {
        return manager.update();
    }

}
