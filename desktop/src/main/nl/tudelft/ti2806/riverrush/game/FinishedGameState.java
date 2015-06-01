package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.GdxGame;
import nl.tudelft.ti2806.riverrush.screen.FinishedGameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

/**
 * State for a finished game.
 */
public class FinishedGameState implements GameState {

    private final EventDispatcher dispatcher;
    private final AssetManager assets;
    private final GdxGame gameWindow;
    private final FinishedGameScreen screen;

    /**
     * The state of the game that indicates that the game has finished.
     *
     * @param eventDispatcher
     *            the dispatcher that is used to handle any relevant events for the game in this
     *            state.
     * @param assetManager
     *            has all necessary assets loaded and available for use.
     * @param game
     *            refers to the game that this state belongs to.
     */
    public FinishedGameState(final EventDispatcher eventDispatcher,
            final AssetManager assetManager, final GdxGame game) {
        this.gameWindow = game;
        this.assets = assetManager;
        this.dispatcher = eventDispatcher;

        this.screen = new FinishedGameScreen(assetManager, eventDispatcher);
        Gdx.app.postRunnable(() -> FinishedGameState.this.gameWindow
                .setScreen(FinishedGameState.this.screen));
    }

    @Override
    public void dispose() {
        // Does not need to dispose
    }

    @Override
    public GameState start() {
        return new WaitingGameState(this.dispatcher, this.assets, this.gameWindow);
    }

    @Override
    public GameState stop() {
        return new StoppedGameState(this.dispatcher, this.assets, this.gameWindow);
    }

    @Override
    public GameState finish() {
        return this;
    }

    @Override
    public GameState waitForPlayers() {
        return new WaitingGameState(this.dispatcher, this.assets, this.gameWindow);
    }
}
