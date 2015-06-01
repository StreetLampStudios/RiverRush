package nl.tudelft.ti2806.riverrush.game;

import com.badlogic.gdx.assets.AssetManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.failfast.FailIf;
import nl.tudelft.ti2806.riverrush.game.state.GameState;
import nl.tudelft.ti2806.riverrush.game.state.LoadingGameState;
import nl.tudelft.ti2806.riverrush.graphics.GdxGame;
import nl.tudelft.ti2806.riverrush.graphics.entity.Team;

import java.util.HashMap;

/**
 * Shared application class.
 */
@Singleton
public class Game extends GdxGame {

    private final AssetManager assets;
    private final EventDispatcher dispatcher;
    private GameState currentGameState;
    private HashMap<Integer, Team> teams;

    /**
     * Creates a game class.
     *
     * @param eventDispatcher the dispatcher that handles the events that are relevant to the game class.
     * @param assetManager    has all necessary assets loaded and available for use.
     */
    @Inject
    public Game(final EventDispatcher eventDispatcher, final AssetManager assetManager) {
        this.assets = assetManager;
        this.dispatcher = eventDispatcher;
        this.teams = new HashMap<Integer, Team>();
    }

    /**
     * Add a new team to the current listing of teams.
     *
     * @param id is the identifier for the new team.
     */
    public void addTeam(int id) {
        Team tm = new Team(id);
        this.teams.put(id, tm);
    }

    /**
     * Add a new animal to the given team.
     *
     * @param anim   add this animal
     * @param teamId is the identifier for the team to which the animal should be added.
     */
    public void addAnimal(AbstractAnimal anim, Integer teamId) {
        Team tm = this.teams.get(teamId);
        FailIf.isNull(tm);
        tm.addAnimal(anim);
    }

    @Override
    public void create() {
        this.currentGameState = new LoadingGameState(this.dispatcher, this.assets, this);
    }

    @Override
    public void dispose() {
        this.currentGameState = this.currentGameState.stop();
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
     */
    public void finish() {
        this.currentGameState = this.currentGameState.finish();
    }

    /**
     * Puts the game in the waiting game state. This action is relegated to the current game state.
     */
    public void waitForPlayers() {
        this.currentGameState = this.currentGameState.waitForPlayers();
    }

    public HashMap<Integer, Team> getTeams() {
        return this.teams;
    }
}
