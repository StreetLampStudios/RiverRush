package nl.tudelft.ti2806.riverrush.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import nl.tudelft.ti2806.riverrush.domain.entity.game.Game;

/**
 * Shared application class.
 */
@Singleton
public class RiverGame extends com.badlogic.gdx.Game {

    private final AssetManager assets;
    private final Provider<LoadingScreen> loadingScreenP;
    private final Provider<WaitingScreen> waitingScreenP;
    private final Provider<GameScreen> gameScreenP;
    private SpriteBatch batch;
    private LoadingScreen loadingScreen;
    private Game game;

    /**
     * Creates a game.
     *
     * @param provider provides the game with its loading screen
     */
    @Inject
    public RiverGame(final Provider<LoadingScreen> loadingScreenProvider,
                     final Provider<WaitingScreen> waitingScreenProvider,
                     final Provider<GameScreen> gameScreenProvider,
                     final Game game,
                     final AssetManager assetManager) {
        this.game = game;
        this.assets = assetManager;
        this.loadingScreenP = loadingScreenProvider;
        this.waitingScreenP = waitingScreenProvider;
        this.gameScreenP = gameScreenProvider;
    }


    @Override
    public void create() {
        this.loadingScreen = this.loadingScreenP.get();
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

    public void play() {
        this.game.play();
    }

    public void stop() {
        this.game.stop();
    }

    public void finish() {
        this.game.finish();
    }

    public void loadWaitingScreen() {
        this.setScreen(waitingScreenP.get());
    }

    public void loadGameScreen() {
        this.setScreen(gameScreenP.get());
    }

    public void startTimer() {
        ((WaitingScreen)this.getScreen()).startTimer(30);
    }
}
