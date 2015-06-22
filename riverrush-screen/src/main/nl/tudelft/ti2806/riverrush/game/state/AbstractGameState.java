package nl.tudelft.ti2806.riverrush.game.state;

import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.game.Game;

/**
 * Is the game state which houses all the code to represent a GameState.
 */
public abstract class AbstractGameState implements GameState {

    protected final EventDispatcher dispatcher;
    protected final Game game;

    /**
     * Constructor.
     *
     * @param eventDispatcher - the eventDispatcher
     * @param aGame           - the game
     */
    public AbstractGameState(final EventDispatcher eventDispatcher,
                             final Game aGame) {
        this.game = aGame;
        this.dispatcher = eventDispatcher;

    }

    @Override
    public void dispose() {
        // Has to be empty
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
    public GameState finish(final Integer team) {
        return this;
    }

    @Override
    public GameState waitForPlayers() {
        return this;
    }
}
