package nl.tudelft.ti2806.riverrush.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import nl.tudelft.ti2806.riverrush.domain.entity.state.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameAboutToStartEvent;
import nl.tudelft.ti2806.riverrush.graphics.GdxGame;
import nl.tudelft.ti2806.riverrush.screen.WaitingScreen;

/**
 * State for a waiting game.
 */
public class WaitingGameState implements GameState {

    private final EventDispatcher dispatcher;
    private final AssetManager assets;
    private final GdxGame gameWindow;
    private final WaitingScreen screen;
    private static final int DELAY = 5;

    /**
     * The state of the game that indicates that the game is waiting for players. In this state the
     * game can be started when enough players have connected.
     *
     * @param eventDispatcher the dispatcher that is used to handle any relevant events for the game in this
     *                        state.
     * @param assetManager    has all necessary assets loaded and available for use.
     * @param game            refers to the game that this state belongs to.
     */
    public WaitingGameState(final EventDispatcher eventDispatcher, final AssetManager assetManager,
                            final GdxGame game) {
        this.gameWindow = game;
        this.assets = assetManager;
        this.dispatcher = eventDispatcher;
        this.dispatcher.attach(GameAboutToStartEvent.class, (e) -> this.startTimer());
        this.screen = new WaitingScreen(assetManager, eventDispatcher);
        Gdx.app.postRunnable(() -> WaitingGameState.this.gameWindow.setScreen(WaitingGameState.this.screen));
    }

    /**
     * Starts the timer of 5 seconds.
     */
    private void startTimer() {
        this.screen.startTimer(DELAY);
    }

    @Override
    public void dispose() {
        this.dispatcher.detach(GameAboutToStartEvent.class, (e) -> this.startTimer());
        this.screen.dispose();
    }

    @Override
    public GameState start() {
        this.dispose();
        return new PlayingGameState(this.dispatcher, this.assets, this.gameWindow);
    }

    @Override
    public GameState stop() {
        this.dispose();
        return new StoppedGameState(this.dispatcher, this.assets, this.gameWindow);
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
