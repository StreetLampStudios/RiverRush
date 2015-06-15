package nl.tudelft.ti2806.riverrush.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.Team;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalRemovedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;
import nl.tudelft.ti2806.riverrush.game.state.GameState;
import nl.tudelft.ti2806.riverrush.game.state.WaitingForRendererState;

import java.util.Collection;

/**
 * Represents an ongoing or waiting game.
 */
@Singleton
public class Game {


    /**
     * The current state of the game.
     */
    private GameState gameState;
    private GameTrack gameTrack;
    private final EventDispatcher dispatcher;

    /**
     * Create a game instance.
     *
     * @param eventDispatcher The event dispatcher
     * @param track           The game track to use
     */
    @Inject
    public Game(final EventDispatcher eventDispatcher, final GameTrack track) {
        this.dispatcher = eventDispatcher;
        this.gameState = new WaitingForRendererState(dispatcher, this);
        this.gameTrack = track;
        HandlerLambda<AnimalRemovedEvent> removeAnimal = this::removeAnimalHandler;
        this.dispatcher.attach(AnimalRemovedEvent.class, removeAnimal);

        //TODO: do not hardcode it.
        this.gameTrack.addTeam(new Team());
        this.gameTrack.addTeam(new Team());
    }

    /**
     * Add the player to the team.
     *
     * @param animal The animal
     * @param teamId The team
     */
    public void addPlayerToTeam(final AbstractAnimal animal, final Integer teamId) {
        this.gameTrack.addAnimalToTeam(animal, teamId);
    }

    /**
     * Remove all the animals from a given boat that moved to the wrong direction.
     *
     * @param rockDirection the direction given by the boat collided event.
     * @param teamId        the team which the action applies to.
     */
    public void sweepAnimals(final Direction rockDirection, final Integer teamId) {
        this.gameTrack.sweepAnimals(rockDirection, teamId);
    }

    /**
     * kick an animal off the boat.
     *
     * @param animalId - integer that represents the animal
     * @param teamId   - integer that represents the team
     */
    public void collideAnimal(final Integer animalId, final Integer teamId) {
        this.gameTrack.collideAnimal(animalId, teamId);
    }

    /**
     * Handler that adds a player to the game.
     *
     * @param event the animal added event.
     */
    private void removeAnimalHandler(final AnimalRemovedEvent event) {
        Integer team = event.getTeam();
        Integer animal = event.getAnimal();
        this.gameTrack.removeAnimalFromTeam(team, animal);
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
     *
     * @param winningId - id of the winner.
     */
    public void finish(final Integer winningId) {
        this.gameState = this.gameState.finish(winningId);
    }

    /**
     * Resets the game.
     */
    public void reset() {
        this.gameTrack.reset();
        this.gameState = this.gameState.waitForPlayers();
    }

    /**
     * Go to the wait for players state.
     */
    public void waitForPlayers() {
        this.gameState = this.gameState.waitForPlayers();
    }

    public Collection<Team> getTeams() {
        return this.gameTrack.getTeams();
    }

    /**
     * Get the event for the current state to send to new connections.
     *
     * @return The event for the current state
     */
    public Event getStateEvent() {
        return this.gameState.getStateEvent();
    }


    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(final GameState aGameState) {
        this.gameState = aGameState;
    }
}
