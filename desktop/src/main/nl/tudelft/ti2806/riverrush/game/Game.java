package nl.tudelft.ti2806.riverrush.game;

import com.badlogic.gdx.assets.AssetManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import nl.tudelft.ti2806.riverrush.domain.entity.state.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.GdxGame;
import nl.tudelft.ti2806.riverrush.screen.LoadingScreen;
import nl.tudelft.ti2806.riverrush.screen.WaitingScreen;

/**
 * Shared application class.
 */
@Singleton
public class Game extends GdxGame {

    private final AssetManager assets;
    private final EventDispatcher dispatcher;

    private LoadingScreen loadingScreen;

    private GameState currentGameState;


    /**
     * Creates a game.
     * @param provider provides the game with its loading screen
     */
    @Inject
    public Game(final EventDispatcher eventDispatcher,
                final AssetManager assetManager) {
        this.assets = assetManager;
        this.dispatcher = eventDispatcher;
    }

    @Override
    public void create() {
        this.currentGameState = new LoadingGameState(dispatcher, assets, this);
    }

    @Override
    public void dispose() {
        this.currentGameState = this.currentGameState.stop();
    }


    public void start() {
        this.currentGameState = this.currentGameState.start();
    }

    public void stop() {
        this.currentGameState = this.currentGameState.stop();
    }

    public void finish() {
        this.currentGameState = this.currentGameState.finish();
    }

    public void waitForPlayers() {
        this.currentGameState = this.currentGameState.waitForPlayers();
    }
}
