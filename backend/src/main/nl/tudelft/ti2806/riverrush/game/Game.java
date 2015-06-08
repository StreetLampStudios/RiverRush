package nl.tudelft.ti2806.riverrush.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.Team;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalAddedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalRemovedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameAboutToStartEvent;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;
import nl.tudelft.ti2806.riverrush.game.state.GameState;
import nl.tudelft.ti2806.riverrush.game.state.WaitingForRendererState;

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
        this.gameState = new WaitingForRendererState(dispatcher, this);
        this.gameTrack = new BasicGameTrack(dispatcher);
        this.eventDispatcher = dispatcher;

        HandlerLambda<AnimalAddedEvent> addAnimal = (e) -> this.addAnimalHandler();
        HandlerLambda<AnimalRemovedEvent> removeAnimal = (e) -> this.removeAnimalHandler(e);
        this.eventDispatcher.attach(AnimalAddedEvent.class, addAnimal);
        this.eventDispatcher.attach(AnimalRemovedEvent.class, removeAnimal);
    }

    /**
     * Handler that adds a player to the game.
     */
    private void addAnimalHandler() {
        this.playerCount++;
        if (this.playerCount == 2) {
            GameAboutToStartEvent event = new GameAboutToStartEvent();
            event.setSeconds(DELAY);
            this.eventDispatcher.dispatch(event);

            final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.schedule(this::start, DELAY, TimeUnit.SECONDS);
        }
    }

    /**
     * Handler that adds a player to the game.
     */
    private void removeAnimalHandler(AnimalRemovedEvent event) {
        this.playerCount--;
        Integer team = event.getTeam();
        Integer animal = event.getAnimal();
        this.gameTrack.getTeam(team).getAnimals().remove(animal);
    }

    /**
     * Start the game.
     */
    public void start() {
        this.gameState = this.gameState.start();
        this.gameTrack.startTimer();
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
     * Add the player to the team.
     *
     * @param animal The animal
     * @param team   The team
     */
    public void addPlayerToTeam(final AbstractAnimal animal, final Integer team) {
        try {
            this.gameTrack.addAnimal(team, animal);

            AnimalAddedEvent event = new AnimalAddedEvent();
            event.setAnimal(animal.getId());
            event.setTeam(team);
            event.setVariation(animal.getVariation());
            this.eventDispatcher.dispatch(event);
        } catch (NoSuchTeamException e) {
            // Empty for now TODO
        }

    }

    /**
     * Jumps an animal.
     *
     * @param animal - the animal to jump
     */
    public void jumpAnimal(final AbstractAnimal animal) {
        animal.jump();
    }

    public void voteMove(final AbstractAnimal animal, final Direction direction) {
        animal.voteOneDirection(direction);
    }

    /**
     * Remove all the animals from a given boat that moved to the wrong direction.
     *
     * @param rightOneDirection the direction given by the boat collided event.
     * @param teamID            the team which the action applies to.
     */
    public void sweepAnimals(
        final Direction rightOneDirection, final Integer teamID) {
        Team tm = this.gameTrack.getTeam(teamID);
        for (AbstractAnimal anim : tm.getAnimals().values()) {
            if (anim.getVoteDirection().equals(rightOneDirection)) {
                // TODO: check if this equals works properly
                anim.fall();
            }
        }
    }

    /**
     * kick an animal off the boat
     *
     * @param animal - integer that represents the animal
     * @param team   - integer that represents the team
     */
    public void collideAnimal(final Integer animal, final Integer team) {
        Team team1 = this.gameTrack.getTeam(team);
        AbstractAnimal animal1 = team1.getAnimals().get(animal);
        animal1.fall();
    }
}
