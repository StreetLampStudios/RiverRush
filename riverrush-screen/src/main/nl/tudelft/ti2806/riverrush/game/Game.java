package nl.tudelft.ti2806.riverrush.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalFellOffEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalRemovedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;
import nl.tudelft.ti2806.riverrush.game.state.GameState;
import nl.tudelft.ti2806.riverrush.game.state.LoadingGameState;
import nl.tudelft.ti2806.riverrush.game.state.WaitingGameState;
import nl.tudelft.ti2806.riverrush.graphics.Assets;
import nl.tudelft.ti2806.riverrush.graphics.GdxGame;
import nl.tudelft.ti2806.riverrush.graphics.entity.Animal;
import nl.tudelft.ti2806.riverrush.graphics.entity.BoatGroup;
import nl.tudelft.ti2806.riverrush.graphics.entity.Team;

import java.util.Collection;
import java.util.HashMap;

/**
 * Graphics game.
 */
@Singleton
public class Game extends GdxGame {

    private final EventDispatcher dispatcher;
    private final HandlerLambda<AnimalFellOffEvent> animalFellOffEventHandlerLambda;
    private final HandlerLambda<AnimalRemovedEvent> removeAnimalHandlerLambda;
    private GameState currentGameState;

    private HashMap<Integer, Team> teams;

    /**
     * Creates a game class.
     *
     * @param eventDispatcher the dispatcher that handles the events that are relevant to the game
     *                        class.
     */
    @Inject
    public Game(final EventDispatcher eventDispatcher) {
        this.dispatcher = eventDispatcher;
        this.teams = new HashMap<>();

        this.animalFellOffEventHandlerLambda =
            (e) -> this.getTeam(e.getTeam()).getAnimal(e.getAnimal()).fall();

        this.removeAnimalHandlerLambda = this::removeAnimalHandler;

        this.dispatcher.attach(AnimalFellOffEvent.class, this.animalFellOffEventHandlerLambda);
        this.dispatcher.attach(AnimalRemovedEvent.class, this.removeAnimalHandlerLambda);
    }

    @Override
    public void create() {
        this.currentGameState = new LoadingGameState(this.dispatcher, this);
    }

    @Override
    public void dispose() {
        this.dispatcher.detach(AnimalFellOffEvent.class, this.animalFellOffEventHandlerLambda);
        this.dispatcher.detach(AnimalRemovedEvent.class, this.removeAnimalHandlerLambda);
        this.currentGameState = this.currentGameState.stop();
        Assets.dispose();
    }

    /**
     * Reset the game to the starting state.
     */
    public void reset() {
        this.currentGameState = new WaitingGameState(dispatcher, this);
    }

    /**
     * Get a team.
     *
     * @param teamId is the identifier for the team.
     * @return The team
     */
    public Team getTeam(final Integer teamId) {
        return this.teams.get(teamId);
    }

    /**
     * Returns the teams.
     *
     * @return the teams
     */
    public Collection<Team> getTeams() {
        return this.teams.values();
    }

    /**
     * Add a new team to the current listing of teams.
     *
     * @param id is the identifier for the new team.
     * @return The team the animal is in
     */
    public Team addTeam(final Integer id) {
        Team tm = new Team(id);
        this.teams.put(id, tm);
        return tm;
    }

    /**
     * Remove an animal.
     *
     * @param event The remove event
     */
    public void removeAnimalHandler(final AnimalRemovedEvent event) {
        Integer teamId = event.getTeam();
        Integer animalId = event.getAnimal();
        Team team = this.getTeam(teamId);
        Animal animal = (Animal) team.getAnimal(animalId);
        BoatGroup boat = team.getBoat();

        if (boat != null) {
            boat.removeAnimal(animal);
        }

        team.removeAnimal(animalId);
    }

    /**
     * Starts the game. This action is relegated to the current game state.
     */
    public void start() {
        this.currentGameState = this.currentGameState.start();
    }

    /**
     * Stops the game. This action is relegated to the current game state.
     */
    public void stop() {
        this.currentGameState = this.currentGameState.stop();
    }

    /**
     * Commands the game to finish. This action is relegated to the current game state.
     *
     * @param team The winning team
     */
    public void finish(final Integer team) {
        this.currentGameState = this.currentGameState.finish(team);
    }

    /**
     * Puts the game in the waiting game state. This action is relegated to the current game state.
     */
    public void waitForPlayers() {
        this.currentGameState = this.currentGameState.waitForPlayers();
    }
}
