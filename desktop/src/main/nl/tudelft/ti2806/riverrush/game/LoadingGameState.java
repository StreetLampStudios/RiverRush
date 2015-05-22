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

    public LoadingGameState(EventDispatcher eventDispatcher, AssetManager assetManager, GdxGame game) {
        this.gameWindow = game;
        this.assets = assetManager;
        this.dispatcher = eventDispatcher;

        this.screen = new LoadingScreen(assetManager, eventDispatcher);
        gameWindow.setScreen(this.screen);
    }

    @Override
    public void dispose() {

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
        return new WaitingGameState(dispatcher, assets, gameWindow);
    }
}
