package nl.tudelft.ti2806.riverrush.domain.entity.game;

import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameStoppedEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by m.olsthoorn on 5/18/2015.
 */
public class StoppedGameState implements GameState {

    private final EventDispatcher eventDispatcher;

    public StoppedGameState(final EventDispatcher dispatcher) {
        this.eventDispatcher = dispatcher;
        this.eventDispatcher.dispatch(new GameStoppedEvent());

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> System.exit(0), 30, TimeUnit.SECONDS);

        dispatcher.dispatch(new GameStoppedEvent());
    }

    @Override
    public GameState play() {
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
}
