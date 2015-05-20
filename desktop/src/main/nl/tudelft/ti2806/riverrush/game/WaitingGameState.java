package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.state.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameAboutToStartEvent;
import nl.tudelft.ti2806.riverrush.graphics.GdxGame;
import nl.tudelft.ti2806.riverrush.screen.WaitingScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by thomas on 19-5-15.
 */
public class WaitingGameState implements GameState {

    private final EventDispatcher dispatcher;
    private final AssetManager assets;
    private final GdxGame gameWindow;
    private final WaitingScreen screen;

    public WaitingGameState(EventDispatcher eventDispatcher,
            AssetManager assetManager, GdxGame game) {
        this.gameWindow = game;
        this.assets = assetManager;
        this.dispatcher = eventDispatcher;

        this.dispatcher.attach(GameAboutToStartEvent.class,
                (e) -> this.startTimer());

        this.screen = new WaitingScreen(assetManager, eventDispatcher);
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                WaitingGameState.this.screen.init();
                WaitingGameState.this.gameWindow
                        .setScreen(WaitingGameState.this.screen);
            }
        });
    }

    private void startTimer() {
        this.screen.startTimer(30);
    }

    private void dispose() {
        this.dispatcher.detach(GameAboutToStartEvent.class,
                (e) -> this.startTimer());
        this.screen.dispose();
    }

    @Override
    public GameState start() {
        this.dispose();
        return new PlayingGameState(this.dispatcher, this.assets,
                this.gameWindow);
    }

    @Override
    public GameState stop() {
        this.dispose();
        return new StoppedGameState(this.dispatcher, this.assets,
                this.gameWindow);
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
