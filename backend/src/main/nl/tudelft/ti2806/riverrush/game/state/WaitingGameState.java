package nl.tudelft.ti2806.riverrush.game.state;

import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameWaitingEvent;

/**
 * State in which the game is waiting for players to join.
 */
public class WaitingGameState implements GameState {

    private final EventDispatcher eventDispatcher;

    /**
     * Create the waiting game state.
     *
     * @param dispatcher The event dispatcher for firing events
     */
    public WaitingGameState(final EventDispatcher dispatcher) {
        this.eventDispatcher = dispatcher;
        this.eventDispatcher.dispatch(new GameWaitingEvent());
    }

    @Override
    public void dispose() {
        // Nothing to dispose.
    }

    @Override
    public GameState start() {
        this.dispose();
        return new PlayingGameState(this.eventDispatcher);
    }

    @Override
    public GameState stop() {
        this.dispose();
        return new StoppedGameState(this.eventDispatcher);
    }

    @Override
    public GameState finish() {
        return this;
    }

    @Override
    public GameState waitForPlayers() {
        return this;
    }
}
