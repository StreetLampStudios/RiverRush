package nl.tudelft.ti2806.riverrush.game.state;

import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameFinishedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameStoppedEvent;
import nl.tudelft.ti2806.riverrush.game.Game;

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

    /**
     * Create the stopped game state.
     *
     * @param dispatcher The event dispatcher for dispatching events
     * @param game
     */
    public StoppedGameState(final EventDispatcher dispatcher, Game game) {
        dispatcher.dispatch(new GameStoppedEvent());

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> System.exit(0), DELAY, TimeUnit.SECONDS);

        dispatcher.dispatch(this.getStateEvent());
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
        return new GameStoppedEvent();
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
    public GameState finish(Integer team) {
        return this;
    }

    @Override
    public GameState waitForPlayers() {
        return this;
    }
}
