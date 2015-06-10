package nl.tudelft.ti2806.riverrush.game.state;

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
    private Game game;
    private static final int WAITTOSTARTGAME = 5000;

    /**
     * Initializes the state where the game is finished. A game is finished when one team has won.
     *
     * @param dispatcher The dispatcher, so we can dispatch {@link GameFinishedEvent}
     * @param gme - the game. see {@link Game}
     */
    public FinishedGameState(final EventDispatcher dispatcher, final Game gme) {
        this.eventDispatcher = dispatcher;
        this.game = gme;

        Timer tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                game.reset();
                tmr.cancel();
            }
        }, WAITTOSTARTGAME, WAITTOSTARTGAME);

        GameFinishedEvent event2 = new GameFinishedEvent();
        event2.setTeam(-1);
        dispatcher.dispatch(event2);

        GameAboutToWaitEvent event = new GameAboutToWaitEvent();
        event.setTimeTillWait(WAITTOSTARTGAME);
        eventDispatcher.dispatch(event);
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
        this.dispose();
        return new WaitingGameState(this.eventDispatcher, this.game);
    }
}
