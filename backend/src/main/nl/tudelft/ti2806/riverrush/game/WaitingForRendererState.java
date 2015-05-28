package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.state.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * State in which the game is waiting for the renderer to join.
 */
public class WaitingForRendererState implements GameState {

    private final EventDispatcher dispatcher;

    /**
     * Create the waiting for renderer state.
     *
     * @param eventDispatcher The event dispatcher for firing events
     */
    public WaitingForRendererState(final EventDispatcher eventDispatcher) {
        this.dispatcher = eventDispatcher;
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
        return new WaitingGameState(dispatcher);
    }
}
