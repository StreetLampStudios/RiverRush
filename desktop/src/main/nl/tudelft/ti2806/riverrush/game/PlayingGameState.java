package nl.tudelft.ti2806.riverrush.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.domain.entity.state.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.PlayerJumpedEvent;
import nl.tudelft.ti2806.riverrush.graphics.GdxGame;
import nl.tudelft.ti2806.riverrush.network.event.JumpEvent;
import nl.tudelft.ti2806.riverrush.screen.PlayingGameScreen;

/**
 * Created by thomas on 19-5-15.
 */
public class PlayingGameState implements GameState {

    private final EventDispatcher dispatcher;
    private final AssetManager assets;
    private final GdxGame gameWindow;
    private final PlayingGameScreen screen;

    public PlayingGameState(EventDispatcher eventDispatcher,
                            AssetManager assetManager, GdxGame game) {
        this.gameWindow = game;
        this.assets = assetManager;
        this.dispatcher = eventDispatcher;

        this.dispatcher
            .attach(PlayerJumpedEvent.class, (e) -> this.jump(null));
        this.screen = new PlayingGameScreen(assetManager, eventDispatcher);
        Gdx.app.postRunnable(() -> {
            PlayingGameState.this.screen.init();
            PlayingGameState.this.gameWindow
                .setScreen(PlayingGameState.this.screen);
        });

    }

    public void jump(Player player) {
        this.screen.jump(player);
    }

    @Override
    public void dispose() {
        this.dispatcher
            .detach(JumpEvent.class, (e) -> this.jump(e.getPlayer()));
        this.screen.dispose();
    }

    @Override
    public GameState start() {
        return this;
    }

    @Override
    public GameState stop() {
        this.screen.dispose();
        return new StoppedGameState(this.dispatcher, this.assets,
            this.gameWindow);
    }

    @Override
    public GameState finish() {
        this.screen.dispose();
        return new FinishedGameState(this.dispatcher, this.assets,
            this.gameWindow);
    }

    @Override
    public GameState waitForPlayers() {
        return this;
    }
}
