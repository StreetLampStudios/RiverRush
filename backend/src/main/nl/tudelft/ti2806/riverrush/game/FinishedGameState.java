package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.state.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameFinishedEvent;

/**
 * Created by m.olsthoorn on 5/18/2015.
 */
public class FinishedGameState implements GameState {

    private final EventDispatcher eventDispatcher;

    public FinishedGameState(final EventDispatcher dispatcher) {
        this.eventDispatcher = dispatcher;

        dispatcher.dispatch(new GameFinishedEvent());
    }

    @Override
    public GameState start() {
        return new WaitingGameState(this.eventDispatcher);
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
        return new WaitingGameState(this.eventDispatcher);
    }
}
