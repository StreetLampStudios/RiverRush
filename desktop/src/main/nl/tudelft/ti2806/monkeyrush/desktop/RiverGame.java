package nl.tudelft.ti2806.monkeyrush.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Shared application class.
 */
public class RiverGame extends Game {

    protected SpriteBatch batch;
    protected BitmapFont font;
    public Level level;
    public AssetManager manager = new AssetManager();

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.setScreen(new LoadingScreen(this));

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
    }
}
