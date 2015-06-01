package nl.tudelft.ti2806.riverrush.game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import nl.tudelft.ti2806.riverrush.domain.entity.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameAboutToStartEvent;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;
import nl.tudelft.ti2806.riverrush.domain.event.PlayerAddedEvent;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Represents an ongoing or waiting game.
 */
@Singleton
public class Game {

  /**
   * Game about to start timer delay.
   */
  public static final int DELAY = 5;

  /**
   * The current state of the game.
   */
  private GameState gameState;
  private int playerCount = 0;
  private final EventDispatcher eventDispatcher;

  /**
   * Create a game instance.
   *
   * @param dispatcher The event dispatcher
   */
  @Inject
  public Game(final EventDispatcher dispatcher) {
    this.gameState = new WaitingForRendererState(dispatcher);
    this.eventDispatcher = dispatcher;

    HandlerLambda<PlayerAddedEvent> addPlayer = (e) -> this.addPlayerHandler();
    this.eventDispatcher.attach(PlayerAddedEvent.class, addPlayer);
  }

  /**
   * Handler that adds a player to the game.
   */
  private void addPlayerHandler() {
    this.playerCount++;
    if (this.playerCount > 0) {
      this.eventDispatcher.dispatch(new GameAboutToStartEvent());
      final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
      scheduler.schedule(this::start, DELAY, TimeUnit.SECONDS);
    }
  }

  /**
   * Start the game.
   */
  public void start() {
    this.gameState = this.gameState.start();
  }

  /**
   * Stop the game.
   */
  public void stop() {
    this.gameState = this.gameState.stop();
  }

  /**
   * Finish the game.
   */
  public void finish() {
    this.gameState = this.gameState.finish();
  }

  /**
   * Go to the wait for players state.
   */
  public void waitForPlayers() {
    this.gameState = this.gameState.waitForPlayers();
  }
}
