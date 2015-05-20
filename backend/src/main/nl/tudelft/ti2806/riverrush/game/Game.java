package nl.tudelft.ti2806.riverrush.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import nl.tudelft.ti2806.riverrush.domain.entity.state.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents an ongoing or waiting game.
 */
@Singleton
public class Game {

    /**
     * The current state of the game.
     */
    private GameState gameState;
    private final HandlerLambda<PlayerAddedEvent> addPlayer;
    private int playerCount = 0;
    private final EventDispatcher eventDispatcher;

    /**
     * Construct an application.
     */
    @Inject
    public Game(final EventDispatcher dispatcher) {
        this.gameState = new WaitingForRendererState(dispatcher);
        this.eventDispatcher = dispatcher;

        this.addPlayer = this::addPlayer;
        this.eventDispatcher.attach(PlayerAddedEvent.class, addPlayer);
    }

    private void addPlayer(final PlayerAddedEvent playerAddedEvent) {
        playerCount++;
        if (playerCount > 0) {
            eventDispatcher.dispatch(new GameAboutToStartEvent(5));
            final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.schedule(
                this::start,
                5,
                TimeUnit.SECONDS);
        }
    }

    public void start() {
        this.gameState = this.gameState.start();
    }

    public void stop() {
        this.gameState = this.gameState.stop();
    }

    public void finish() {
        this.gameState = this.gameState.finish();
    }

    public void waitForPlayers() {
        this.gameState = this.gameState.waitForPlayers();
    }
}
