package nl.tudelft.ti2806.riverrush.game.state;

import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameFinishedEvent;
import nl.tudelft.ti2806.riverrush.game.Game;

/**
 * When a team wins the game, it will go into this state.
 */
public class FinishedGameState implements GameState {

    private final EventDispatcher eventDispatcher;
    private Game game;

    /**
     * Initializes the state where the game is finished. A game is finished when one team has won.
     *
     * @param dispatcher The dispatcher, so we can dispatch {@link GameFinishedEvent}
     * @param gme
     */
    public FinishedGameState(final EventDispatcher dispatcher, Game gme) {
        this.eventDispatcher = dispatcher;
        this.game = gme;

        dispatcher.dispatch(new GameFinishedEvent());
    }

    @Override
    public void dispose() {
        // Nothing to dispose.
    }

    @Override
    public GameState start() {
        this.dispose();
        return new WaitingGameState(this.eventDispatcher, this.game);
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
        this.dispose();
        return new WaitingGameState(this.eventDispatcher, this.game);
    }

    @Override
    public GameState swooshThaFuckahsFromBoatThatMovedToTheWrongDirection(Direction rightOneDirection) {
        return this;
    }
}
