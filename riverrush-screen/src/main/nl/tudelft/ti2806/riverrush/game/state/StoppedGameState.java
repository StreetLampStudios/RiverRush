package nl.tudelft.ti2806.riverrush.game.state;

import com.badlogic.gdx.Gdx;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.screen.StoppedScreen;

/**
 * State for a stopped game.
 */
public class StoppedGameState extends AbstractGameState {

    /**
     * The state of the game that indicates that the game has stopped. In this state the game has
     * ended and cannot be restarted.
     *
     * @param eventDispatcher the dispatcher that is used to handle any relevant events for the game in this
     *                        state.
     * @param gm              refers to the game that this state belongs to.
     */
    public StoppedGameState(final EventDispatcher eventDispatcher,
                            final Game gm) {
        super(eventDispatcher, gm);
        Gdx.app.postRunnable(() -> StoppedGameState.this.game
                .setScreen(new StoppedScreen()));
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
    public GameState finish(final Integer team) {
        return this;
    }

    @Override
    public GameState waitForPlayers() {
        return this;
    }

    @Override
    public Event getStateEvent() {
        return null;
    }
}
