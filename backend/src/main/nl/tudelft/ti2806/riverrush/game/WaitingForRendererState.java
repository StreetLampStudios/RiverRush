package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.state.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

public class WaitingForRendererState implements GameState {

    private final EventDispatcher dispatcher;

    public WaitingForRendererState(final EventDispatcher eventDispatcher) {
        this.dispatcher = eventDispatcher;
    }

    @Override
    public void dispose() {

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
