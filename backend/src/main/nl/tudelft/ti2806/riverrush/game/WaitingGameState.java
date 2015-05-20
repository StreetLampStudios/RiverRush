package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.state.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;
import nl.tudelft.ti2806.riverrush.domain.event.PlayerAddedEvent;

/**
 * State in which the game is waiting for players to join.
 */
public class WaitingGameState implements GameState {

    private final EventDispatcher eventDispatcher;
    private final HandlerLambda<PlayerAddedEvent> addPlayer;

    public WaitingGameState(final EventDispatcher dispatcher) {
        this.eventDispatcher = dispatcher;
        this.addPlayer = this::addPlayer;
        this.eventDispatcher.attach(PlayerAddedEvent.class, addPlayer);
    }

    private void addPlayer(final PlayerAddedEvent playerAddedEvent) {

    }

    @Override
    public void dispose() {
        this.eventDispatcher.detach(PlayerAddedEvent.class, addPlayer);
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
