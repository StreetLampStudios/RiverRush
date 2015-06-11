package nl.tudelft.ti2806.riverrush.game.state;

import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.game.Game;

/**
 * State in which the game is waiting for the renderer to join.
 */
public class WaitingForRendererState implements GameState {

    private final EventDispatcher dispatcher;
    private Game game;

    /**
     * Create the waiting for renderer state.
     *
     * @param eventDispatcher The event dispatcher for firing events
     * @param game
     */
    public WaitingForRendererState(final EventDispatcher eventDispatcher, final Game game) {
        this.dispatcher = eventDispatcher;
        this.game = game;
    }

    @Override
    public void dispose() {
        // Nothing to dispose.
    }

    @Override
    public GameState start() {
        return this;
    }

    @Override
    public GameState stop() {
        return this;
    }

    @Override
    public GameState finish() {
        return this;
    }

    @Override
    public GameState waitForPlayers() {
        this.dispose();
        return new WaitingGameState(this.dispatcher, this.game);
    }
}
