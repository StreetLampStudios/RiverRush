package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.GameState;
import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;
import nl.tudelft.ti2806.riverrush.domain.event.PlayerJumpedEvent;
import nl.tudelft.ti2806.riverrush.screen.PlayingGameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

/**
 * State for a game that is playing.
 */
public class PlayingGameState extends AbstractGameState {

    private final PlayingGameScreen screen;
    private final HandlerLambda<PlayerJumpedEvent> playerJumpedEventHandlerLambda = (e) -> this
            .jump(e.getPlayer());

    /**
     * The state of the game that indicates that the game is currently playable.
     *
     * @param eventDispatcher
     *            the dispatcher that is used to handle any relevant events for the game in this
     *            state.
     * @param assetManager
     *            has all necessary assets loaded and available for use.
     * @param gm
     *            refers to the game that this state belongs to.
     */
    public PlayingGameState(final EventDispatcher eventDispatcher, final AssetManager assetManager,
            final Game gm) {
        super(eventDispatcher, assetManager, gm);
        this.dispatcher.attach(PlayerJumpedEvent.class, this.playerJumpedEventHandlerLambda);

        this.screen = new PlayingGameScreen(assetManager, eventDispatcher);
        Gdx.app.postRunnable(() -> {
            PlayingGameState.this.screen.init();
            PlayingGameState.this.game.setScreen(PlayingGameState.this.screen);
        });

    }

    /**
     * Tells a given player to perform the jump action.
     *
     * @param player
     *            refers to the player character that has to jump.
     */
    public void jump(final Player player) {
        // this.screen.jump(player);
    }

    @Override
    public void dispose() {
        this.dispatcher.detach(PlayerJumpedEvent.class, this.playerJumpedEventHandlerLambda);
        this.screen.dispose();
    }

    @Override
    public GameState start() {
        return this;
    }

    @Override
    public GameState stop() {
        this.screen.dispose();
        return new StoppedGameState(this.dispatcher, this.assets, this.game);
    }

    @Override
    public GameState finish() {
        this.screen.dispose();
        return new FinishedGameState(this.dispatcher, this.assets, this.game);
    }

    @Override
    public GameState waitForPlayers() {
        return this;
    }
}
