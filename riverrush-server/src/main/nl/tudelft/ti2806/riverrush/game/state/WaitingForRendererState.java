package nl.tudelft.ti2806.riverrush.game.state;

import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.game.Game;

/**
 * State in which the game is waiting for the renderer to join.
 */
public class WaitingForRendererState implements GameState {

    private final EventDispatcher dispatcher;
    private final Game game;

    /**
     * Create the waiting for renderer state.
     *
     * @param eventDispatcher The event dispatcher for firing events
     * @param aGame           The main aGame
     */
    public WaitingForRendererState(final EventDispatcher eventDispatcher, final Game aGame) {
        this.dispatcher = eventDispatcher;
        this.game = aGame;
    }

    @Override
    public void dispose() {
        // Nothing to dispose.
    }

    /**
     * Get the event for the current state to send to new connections.
     *
     * @return The event for the current state
     */
    public Event getStateEvent() {
        return null;
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
    public GameState finish(final Integer team) {
        return this;
    }

    @Override
    public GameState waitForPlayers() {
        this.dispose();
        return new WaitingGameState(this.dispatcher, this.game);
    }
}
