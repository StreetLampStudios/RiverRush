package nl.tudelft.ti2806.riverrush.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import nl.tudelft.ti2806.riverrush.domain.entity.state.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * Represents an ongoing or waiting game.
 */
@Singleton
public class Game {

    /**
     * The current state of the game.
     */
    private GameState gameState;

    /**
     * Construct an application.
     */
    @Inject
    public Game(final EventDispatcher dispatcher) {
        this.gameState = new WaitingForRendererState(dispatcher);
    }

    public void play() {
        this.gameState = this.gameState.start();
    }

    public void stop() {
        this.gameState = this.gameState.stop();
    }

    public void finish() {
        this.gameState = this.gameState.finish();
    }

    public void waitForPlayers() {
        this.gameState = this.gameState.waitForPlayers();
    }
}
