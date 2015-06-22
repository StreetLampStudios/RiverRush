package nl.tudelft.ti2806.riverrush.game.state;

import com.badlogic.gdx.Gdx;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameAboutToWaitEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameWaitingEvent;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.screen.FinishedGameScreen;

import java.util.Timer;
import java.util.TimerTask;

/**
 * State for a finished game.
 */
public class FinishedGameState extends AbstractGameState {

    private final FinishedGameScreen screen;
    private final HandlerLambda<GameAboutToWaitEvent> gameAboutToWaitHandlerLambda = this::startCountDown;
    private final HandlerLambda<GameWaitingEvent> gameWaitHandlerLambda = this::startWaiting;
    private static final int TIME_OFFSET = 100;

    /**
     * Signal the game to start waiting.
     * @param gameWaitingEvent is the event that triggers the game to start waiting.
     */
    private void startWaiting(final GameWaitingEvent gameWaitingEvent) {
        game.waitForPlayers();
    }

    /**
     * Start the countdown to indicate the time before the new game starts.
     * @param gameAboutToWaitEvent refers to the event that triggers this method.
     */
    private void startCountDown(final GameAboutToWaitEvent gameAboutToWaitEvent) {
        screen.startCountdown(gameAboutToWaitEvent.getTimeTillWait());
        Timer tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                Gdx.app.postRunnable(() -> game.reset());
            }
        }, gameAboutToWaitEvent.getTimeTillWait() - TIME_OFFSET);
    }


    /**
     * The state of the game that indicates that the game has finished.
     *
     * @param eventDispatcher the dispatcher that is used to handle any relevant events for the game in this
     *                        state.
     * @param gm              refers to the game that this state belongs to.
     * @param winningID refers to the ID of the winning team.
     */
    public FinishedGameState(final EventDispatcher eventDispatcher,
                             final Game gm, final int winningID) {
        super(eventDispatcher, gm);
        this.screen = new FinishedGameScreen(eventDispatcher);
        Gdx.app.postRunnable(() -> {
                FinishedGameState.this.game
                    .setScreen(FinishedGameState.this.screen);
                screen.drawWinningLabel(winningID);
            }
        );

        eventDispatcher.attach(GameAboutToWaitEvent.class, gameAboutToWaitHandlerLambda);
        eventDispatcher.attach(GameWaitingEvent.class, gameWaitHandlerLambda);

    }

    @Override
    public void dispose() {
        // Does not need to dispose
    }

    @Override
    public GameState start() {
        return new WaitingGameState(this.dispatcher, this.game);
    }

    @Override
    public GameState stop() {
        return new StoppedGameState(this.dispatcher, this.game);
    }

    @Override
    public GameState finish(final Integer team) {
        return this;
    }

    @Override
    public GameState waitForPlayers() {
        return new WaitingGameState(this.dispatcher, this.game);
    }

    @Override
    public Event getStateEvent() {
        return null;
    }
}
