package nl.tudelft.ti2806.riverrush.game;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.Sector;
import nl.tudelft.ti2806.riverrush.domain.entity.Team;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalAddedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalRemovedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;
import nl.tudelft.ti2806.riverrush.game.state.GameState;
import nl.tudelft.ti2806.riverrush.game.state.WaitingForRendererState;
import nl.tudelft.ti2806.riverrush.game.state.WaitingGameState;

import java.util.Collection;
import java.util.List;

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

    private List<Sector> currentPlayerSectors = Lists.newArrayList(Sector.FRONT, Sector.FRONT);

    /**
     * Create a game instance.
     *
     * @param eventDispatcher The event dispatcher
     */
    @Inject
    public Game(final EventDispatcher eventDispatcher) {
        this.dispatcher = eventDispatcher;
        this.gameState = new WaitingForRendererState(dispatcher, this);
        this.gameTrack = new BasicGameTrack(dispatcher, this);
        HandlerLambda<AnimalRemovedEvent> removeAnimal = this::removeAnimalHandler;
        this.dispatcher.attach(AnimalRemovedEvent.class, removeAnimal);
    }

    /**
     * Resets the game.
     */
    public void reset() {
        this.gameTrack.reset();
        this.gameState = new WaitingGameState(dispatcher, this);
    }

    /**
     * Check if all teams have at least one player.
     *
     * @return True if they have, otherwise false
     */
    private Boolean allTeamsHaveAPlayer() {
        for (Team team : this.gameTrack.getTeams().values()) {
            if (team.getAnimals().size() == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Handler that adds a player to the game.
     */
    private void removeAnimalHandler(AnimalRemovedEvent event) {
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
     *
     * @param winningId - id of the winner.
     */
    public void finish(final Integer winningId) {
        this.gameState = this.gameState.finish(winningId);
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
        Integer teamId = this.gameTrack.addAnimal(team, animal);

        AnimalAddedEvent event = new AnimalAddedEvent();
        event.setAnimal(animal.getId());
        event.setTeam(teamId);
        event.setVariation(animal.getVariation());
        Sector nextSector = currentPlayerSectors.get(teamId).getNext();
        currentPlayerSectors.set(teamId, nextSector);
        event.setSector(nextSector);
        this.dispatcher.dispatch(event);
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
     * @param rockDirection the direction given by the boat collided event.
     * @param teamID            the team which the action applies to.
     */
    public void sweepAnimals(final Direction rockDirection, final Integer teamID) {
        Team tm = this.gameTrack.getTeam(teamID);
        for (AbstractAnimal anim : tm.getAnimals().values()) {
            if (anim.getVoteDirection() == rockDirection
                || anim.getVoteDirection() == Direction.NEUTRAL) {
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

    public Collection<Team> getTeams() {
        return gameTrack.getTeams().values();
    }
}
