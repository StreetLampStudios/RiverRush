package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.state.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameStoppedEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * State in which the game is stopped.
 */
public class StoppedGameState implements GameState {

    /**
     * Stopped game timer delay.
     */
    public static final int DELAY = 30;

    private final EventDispatcher eventDispatcher;

    /**
     * Create the stopped game state.
     *
     * @param dispatcher The event dispatcher for dispatching events
     */
    public StoppedGameState(final EventDispatcher dispatcher) {
        this.eventDispatcher = dispatcher;
        this.eventDispatcher.dispatch(new GameStoppedEvent());

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> System.exit(0), DELAY, TimeUnit.SECONDS);

        dispatcher.dispatch(new GameStoppedEvent());
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
        return this;
    }
}
