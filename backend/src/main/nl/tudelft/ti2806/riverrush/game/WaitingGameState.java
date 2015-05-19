package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.state.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameWaitingEvent;

/**
 * State in which the game is waiting for players to join.
 */
public class WaitingGameState implements GameState {

    private final EventDispatcher eventDispatcher;

    public WaitingGameState(final EventDispatcher dispatcher) {
        this.eventDispatcher = dispatcher;

        dispatcher.dispatch(new GameWaitingEvent());
    }

    @Override
    public GameState start() {
        return new PlayingGameState(this.eventDispatcher);
    }

    @Override
    public GameState stop() {
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
