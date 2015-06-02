package nl.tudelft.ti2806.riverrush.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalAddedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameAboutToStartEvent;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;
import nl.tudelft.ti2806.riverrush.game.state.GameState;
import nl.tudelft.ti2806.riverrush.game.state.WaitingForRendererState;
import nl.tudelft.ti2806.riverrush.network.event.JoinTeamCommand;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private GameTrack gameTrack;
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
        this.gameTrack = new BasicGameTrack(100);
        this.eventDispatcher = dispatcher;

        HandlerLambda<AnimalAddedEvent> addPlayer = (e) -> this.addAnimalHandler();
        this.eventDispatcher.attach(AnimalAddedEvent.class, addPlayer);
    }

    /**
     * Handler that adds a player to the game.
     */
    private void addAnimalHandler() {
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

    /**
     * Add the player to teh team
     */
    public void addPlayerToTeam(AbstractAnimal animal, Integer team) {
        if (team == 0) {
            this.gameTrack.getLeftTeam().addAnimal(animal);
        } else {
            this.gameTrack.getRightReam().addAnimal(animal);
        }
        AnimalAddedEvent event = new AnimalAddedEvent();
        event.setAnimal(animal.getId());
        event.setTeam(team);
        this.eventDispatcher.dispatch(new AnimalAddedEvent());
    }
}
