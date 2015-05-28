package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.state.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameFinishedEvent;

/**
 * When a team wins the game, it will go into this state.
 */
public class FinishedGameState implements GameState {

    private final EventDispatcher eventDispatcher;

    /**
     * Initializes the state where the game is finished.
     * A game is finished when one team has won.
     *
     * @param dispatcher The dispatcher, so we can dispatch {@link GameFinishedEvent}
     */
    public FinishedGameState(final EventDispatcher dispatcher) {
        this.eventDispatcher = dispatcher;

        dispatcher.dispatch(new GameFinishedEvent());
    }

    @Override
    public void dispose() {
        // Nothing to dispose.
    }

    @Override
    public GameState start() {
        this.dispose();
        return new WaitingGameState(this.eventDispatcher);
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
        this.dispose();
        return new WaitingGameState(this.eventDispatcher);
    }
}
