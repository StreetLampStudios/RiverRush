package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.state.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.GdxGame;
import nl.tudelft.ti2806.riverrush.screen.FinishedGameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by thomas on 19-5-15.
 */
public class FinishedGameState implements GameState {

    private final EventDispatcher dispatcher;
    private final AssetManager assets;
    private final GdxGame gameWindow;
    private final FinishedGameScreen screen;

    public FinishedGameState(EventDispatcher eventDispatcher,
            AssetManager assetManager, GdxGame game) {
        this.gameWindow = game;
        this.assets = assetManager;
        this.dispatcher = eventDispatcher;

        this.screen = new FinishedGameScreen(assetManager, eventDispatcher);
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                FinishedGameState.this.screen.init();
                FinishedGameState.this.gameWindow
                        .setScreen(FinishedGameState.this.screen);
            }
        });
    }

    @Override
    public GameState start() {
        return new WaitingGameState(this.dispatcher, this.assets,
                this.gameWindow);
    }

    @Override
    public GameState stop() {
        return new StoppedGameState(this.dispatcher, this.assets,
                this.gameWindow);
    }

    @Override
    public GameState finish() {
        return this;
    }

    @Override
    public GameState waitForPlayers() {
        return new WaitingGameState(this.dispatcher, this.assets,
                this.gameWindow);
    }
}
