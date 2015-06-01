package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.GdxGame;
import nl.tudelft.ti2806.riverrush.screen.StoppedScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

/**
 * State for a stopped game.
 */
public class StoppedGameState implements GameState {

    private final GdxGame gameWindow;
    private final StoppedScreen screen;

    /**
     * The state of the game that indicates that the game has stopped. In this state the game has
     * ended and cannot be restarted.
     *
     * @param eventDispatcher
     *            the dispatcher that is used to handle any relevant events for the game in this
     *            state.
     * @param assetManager
     *            has all necessary assets loaded and available for use.
     * @param game
     *            refers to the game that this state belongs to.
     */
    public StoppedGameState(final EventDispatcher eventDispatcher, final AssetManager assetManager,
            final GdxGame game) {
        this.gameWindow = game;

        this.screen = new StoppedScreen(assetManager, eventDispatcher);
        Gdx.app.postRunnable(() -> StoppedGameState.this.gameWindow
                .setScreen(StoppedGameState.this.screen));
    }

    @Override
    public void dispose() {
        // Does not need to dispose
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
    public GameState finish() {
        return this;
    }

    @Override
    public GameState waitForPlayers() {
        return this;
    }
}
