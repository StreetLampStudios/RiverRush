package nl.tudelft.ti2806.Graphics;

import com.badlogic.gdx.Game;
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
    private SpriteBatch batch;
    private LoadingScreen loadingScreen;

    /**
     * Creates a game.
     *
     * @param provider provides the game with its loading screen
     */
    @Inject
    public RiverGame(final Provider<LoadingScreen> provider) {
        this.provider = provider;
    }

    @Override
    public void create() {
        this.loadingScreen = this.provider.get();
        this.batch = new SpriteBatch();
        this.setScreen(this.loadingScreen);
    }

    @Override
    public void dispose() {
        this.loadingScreen.dispose();
    }

    /**
     * @return sprite batch of the game.
     */
    public SpriteBatch getBatch() {
        return this.batch;
    }

}
