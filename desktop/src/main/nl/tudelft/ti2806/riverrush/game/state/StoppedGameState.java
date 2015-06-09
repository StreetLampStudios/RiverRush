package nl.tudelft.ti2806.riverrush.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.screen.StoppedScreen;

/**
 * State for a stopped game.
 */
public class StoppedGameState extends AbstractGameState {

    private final StoppedScreen screen;

    /**
     * The state of the game that indicates that the game has stopped. In this state the game has
     * ended and cannot be restarted.
     *
     * @param eventDispatcher the dispatcher that is used to handle any relevant events for the game in this
     *                        state.
     * @param assetManager    has all necessary assets loaded and available for use.
     * @param gm              refers to the game that this state belongs to.
     */
    public StoppedGameState(final EventDispatcher eventDispatcher, final AssetManager assetManager,
                            final Game gm) {
        super(eventDispatcher, assetManager, gm);
        this.screen = new StoppedScreen(assetManager, eventDispatcher);
        Gdx.app.postRunnable(() -> StoppedGameState.this.game
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
