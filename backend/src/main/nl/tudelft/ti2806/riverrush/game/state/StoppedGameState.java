package nl.tudelft.ti2806.riverrush.game.state;

import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
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

    private final EventDispatcher dispatcher;
    private final Game game;

    /**
     * Create the stopped aGame state.
     *
     * @param eventDispatcher The event eventDispatcher for dispatching events
     * @param aGame           The main game
     */
    public StoppedGameState(final EventDispatcher eventDispatcher, Game aGame) {
        this.dispatcher = eventDispatcher;
        this.game = aGame;
        this.dispatcher.dispatch(new GameStoppedEvent());

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> System.exit(0), DELAY, TimeUnit.SECONDS);

        this.dispatcher.dispatch(new GameStoppedEvent());
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
    public GameState finish(Integer team) {
        return this;
    }

    @Override
    public GameState waitForPlayers() {
        return this;
    }
}
