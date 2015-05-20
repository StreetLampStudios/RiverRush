package nl.tudelft.ti2806.riverrush.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import nl.tudelft.ti2806.riverrush.domain.entity.state.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.GdxGame;
import nl.tudelft.ti2806.riverrush.screen.FinishedGameScreen;

/**
 * Created by thomas on 19-5-15.
 */
public class FinishedGameState implements GameState {

    private final EventDispatcher dispatcher;
    private final AssetManager assets;
    private final GdxGame gameWindow;
    private final Screen screen;

    public FinishedGameState(EventDispatcher eventDispatcher, AssetManager assetManager, GdxGame game) {
        this.gameWindow = game;
        this.assets = assetManager;
        this.dispatcher = eventDispatcher;

        this.screen = new FinishedGameScreen(assetManager, eventDispatcher);
        gameWindow.setScreen(this.screen);
    }

    @Override
    public void dispose() {

    }

    @Override
    public GameState start() {
        return new WaitingGameState(dispatcher, assets, gameWindow);
    }

    @Override
    public GameState stop() {
        return new StoppedGameState(dispatcher, assets, gameWindow);
    }

    @Override
    public GameState finish() {
        return this;
    }

    @Override
    public GameState waitForPlayers() {
        return new WaitingGameState(dispatcher, assets, gameWindow);
    }
}
