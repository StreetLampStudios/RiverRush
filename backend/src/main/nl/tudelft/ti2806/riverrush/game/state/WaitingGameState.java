package nl.tudelft.ti2806.riverrush.game.state;

import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameWaitingEvent;
import nl.tudelft.ti2806.riverrush.game.Game;

/**
 * State in which the game is waiting for players to join.
 */
public class WaitingGameState implements GameState {

    private final EventDispatcher eventDispatcher;
    private Game game;

    /**
     * Create the waiting game state.
     *
     * @param dispatcher The event dispatcher for firing events
     * @param theGame    The game
     */
    public WaitingGameState(final EventDispatcher dispatcher, final Game theGame) {
        this.eventDispatcher = dispatcher;
        this.game = theGame;
        this.eventDispatcher.dispatch(new GameWaitingEvent());
    }

    @Override
    public void dispose() {
        // Nothing to dispose.
    }

    @Override
    public GameState start() {
        this.dispose();
        return new PlayingGameState(this.eventDispatcher, this.game);
    }

    @Override
    public GameState stop() {
        this.dispose();
        return new StoppedGameState(this.eventDispatcher, this.game);
    }

    @Override
    public GameState finish() {
        return this;
    }

    @Override
    public GameState waitForPlayers() {
        return this;
    }
}
