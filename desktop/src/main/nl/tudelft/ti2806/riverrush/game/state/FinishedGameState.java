package nl.tudelft.ti2806.riverrush.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import nl.tudelft.ti2806.riverrush.domain.event.*;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.screen.FinishedGameScreen;
import nl.tudelft.ti2806.riverrush.screen.WaitingScreen;

import java.util.Timer;
import java.util.TimerTask;

/**
 * State for a finished game.
 */
public class FinishedGameState extends AbstractGameState {

    private final FinishedGameScreen screen;
    private final HandlerLambda<GameAboutToWaitEvent> gameAboutToWaitHandlerLambda = this::startCountDown;
    private final HandlerLambda<GameWaitingEvent> gameWaitHandlerLambda = this::startWaiting;

    private void startWaiting(GameWaitingEvent gameWaitingEvent) {
        game.waitForPlayers();
    }

    private void startCountDown(final GameAboutToWaitEvent gameAboutToWaitEvent) {
        screen.startCountdown(gameAboutToWaitEvent.getTimeTillWait());
        Timer tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                Gdx.app.postRunnable(() -> game.reset());
            }
        }, gameAboutToWaitEvent.getTimeTillWait());
    }


    /**
     * The state of the game that indicates that the game has finished.
     *
     * @param eventDispatcher the dispatcher that is used to handle any relevant events for the game in this
     *                        state.
     * @param assetManager    has all necessary assets loaded and available for use.
     * @param gm              refers to the game that this state belongs to.
     */
    public FinishedGameState(final EventDispatcher eventDispatcher,
                             final AssetManager assetManager, final Game gm) {
        super(eventDispatcher, assetManager, gm);
        this.screen = new FinishedGameScreen(assetManager, eventDispatcher);
        Gdx.app.postRunnable(() -> FinishedGameState.this.game
            .setScreen(FinishedGameState.this.screen));

        eventDispatcher.attach(GameAboutToWaitEvent.class, gameAboutToWaitHandlerLambda);
        eventDispatcher.attach(GameWaitingEvent.class, gameWaitHandlerLambda);

    }

    @Override
    public void dispose() {
        // Does not need to dispose
    }

    @Override
    public GameState start() {
        return new WaitingGameState(this.dispatcher, this.assets, this.game);
    }

    @Override
    public GameState stop() {
        return new StoppedGameState(this.dispatcher, this.assets, this.game);
    }

    @Override
    public GameState finish() {
        return this;
    }

    @Override
    public GameState waitForPlayers() {
        return new WaitingGameState(this.dispatcher, this.assets, this.game);
    }
}
