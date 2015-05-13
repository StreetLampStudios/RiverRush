package nl.tudelft.ti2806.Graphics;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Shared application class.
 */
@Singleton
public class RiverGame extends Game {

    private final Provider<LoadingScreen> provider;
    protected SpriteBatch batch;
    protected BitmapFont font;
    private LoadingScreen loadingScreen;

    @Inject
    public RiverGame(final Provider<LoadingScreen> provider) {
        this.provider = provider;
    }

    @Override
    public void create() {
        this.loadingScreen = this.provider.get();
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.setScreen(this.loadingScreen);
    }

    @Override
    public void dispose() {
        this.loadingScreen.dispose();
    }

    public SpriteBatch getBatch() {
        return this.batch;
    }

    public BitmapFont getFont() {
        return this.font;
    }

}
