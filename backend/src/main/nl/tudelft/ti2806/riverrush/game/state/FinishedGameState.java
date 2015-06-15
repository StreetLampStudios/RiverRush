package nl.tudelft.ti2806.riverrush.game.state;

import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameAboutToWaitEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameFinishedEvent;
import nl.tudelft.ti2806.riverrush.game.Game;

import java.util.Timer;
import java.util.TimerTask;

/**
 * When a team wins the game, it will go into this state.
 */
public class FinishedGameState implements GameState {

    private final EventDispatcher eventDispatcher;
    private final Game game;
    private static final int WAIT_TO_START_GAME = 5000;

    /**
     * Initializes the state where the game is finished. A game is finished when one team has won.
     *
     * @param dispatcher The dispatcher, so we can dispatch {@link GameFinishedEvent}
     * @param aGame      - the game. see {@link Game}
     */
    public FinishedGameState(final EventDispatcher dispatcher, final Game aGame) {
        this.eventDispatcher = dispatcher;
        this.game = aGame;

        Timer tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                game.reset();
                tmr.cancel();
            }
        }, WAIT_TO_START_GAME, WAIT_TO_START_GAME);

        GameAboutToWaitEvent event = new GameAboutToWaitEvent();
        event.setTimeTillWait(WAIT_TO_START_GAME);
        this.eventDispatcher.dispatch(event);
    }

    @Override
    public void dispose() {
        // Nothing to dispose.
    }

    /**
     * Get the event for the current state to send to new connections.
     *
     * @return The event for the current state
     */
    public Event getStateEvent() {
        GameFinishedEvent event = new GameFinishedEvent();
        event.setTeam(-1);
        return event;
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
    public GameState finish(final Integer team) {
        return this;
    }

    @Override
    public GameState waitForPlayers() {
        this.dispose();
        return new WaitingGameState(this.eventDispatcher, this.game);
    }
}
