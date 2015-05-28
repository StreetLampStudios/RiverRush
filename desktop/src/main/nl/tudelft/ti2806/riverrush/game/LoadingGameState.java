package nl.tudelft.ti2806.riverrush.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import nl.tudelft.ti2806.riverrush.domain.entity.state.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.GdxGame;
import nl.tudelft.ti2806.riverrush.screen.LoadingScreen;

/**
 * When the game is loading assets, no operation should change the game state.
 */
public class LoadingGameState implements GameState {

    private final EventDispatcher dispatcher;
    private final AssetManager assets;
    private final GdxGame gameWindow;
    private final Screen screen;

    /**
     * The state of the game that indicates that the game is busy loading assets. The purpose of
     * this state is to give the game a window where it can load assets to ensure that all needed
     * assets are loaded properly before use.
     *
     * @param eventDispatcher the dispatcher that is used to handle any relevant events for the game in this
     *                        state.
     * @param assetManager    has all necessary assets loaded and available for use.
     * @param game            refers to the game that this state belongs to.
     */
    public LoadingGameState(final EventDispatcher eventDispatcher, final AssetManager assetManager,
                            final GdxGame game) {
        this.gameWindow = game;
        this.assets = assetManager;
        this.dispatcher = eventDispatcher;

        this.screen = new LoadingScreen(assetManager, eventDispatcher);
        this.gameWindow.setScreen(this.screen);
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
        this.screen.dispose();
        return new WaitingGameState(this.dispatcher, this.assets, this.gameWindow);
    }
}
