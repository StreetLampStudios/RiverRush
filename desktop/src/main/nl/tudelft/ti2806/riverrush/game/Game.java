package nl.tudelft.ti2806.riverrush.game;

import com.badlogic.gdx.assets.AssetManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import nl.tudelft.ti2806.riverrush.domain.entity.state.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.GdxGame;

/**
 * Shared application class.
 */
@Singleton
public class Game extends GdxGame {

    private final AssetManager assets;
    private final EventDispatcher dispatcher;
    private GameState currentGameState;

    /**
     * Creates a game class.
     *
     * @param eventDispatcher the dispatcher that handles the events that are relevant to the game class.
     * @param assetManager    has all necessary assets loaded and available for use.
     */
    @Inject
    public Game(final EventDispatcher eventDispatcher, final AssetManager assetManager) {
        this.assets = assetManager;
        this.dispatcher = eventDispatcher;
    }

    @Override
    public void create() {

        System.out.println("onCreate " + Thread.currentThread().getId());
        this.currentGameState = new LoadingGameState(this.dispatcher, this.assets, this);

    }

    @Override
    public void dispose() {
        this.currentGameState = this.currentGameState.stop();
    }

    /**
     * Starts the game. This action is relegated to the current game state.
     */
    public void start() {
        this.currentGameState = this.currentGameState.start();
    }

    /**
     * Stops the game. This action is relegated to the current game state.
     */
    public void stop() {
        this.currentGameState = this.currentGameState.stop();
    }

    /**
     * Commands the game to finish. This action is relegated to the current game state.
     */
    public void finish() {
        this.currentGameState = this.currentGameState.finish();
    }

    /**
     * Puts the game in the waiting game state. This action is relegated to the current game state.
     */
    public void waitForPlayers() {
        this.currentGameState = this.currentGameState.waitForPlayers();
    }
}
