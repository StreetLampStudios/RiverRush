package nl.tudelft.ti2806.riverrush.game.state;

import com.badlogic.gdx.assets.AssetManager;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.game.Game;

public class AbstractGameState implements GameState {

    protected final EventDispatcher dispatcher;
    protected final AssetManager assets;
    protected final Game game;

    public AbstractGameState(final EventDispatcher eventDispatcher,
                             final AssetManager assetManager, final Game gm) {
        this.game = gm;
        this.assets = assetManager;
        this.dispatcher = eventDispatcher;

    }

    @Override
    public void dispose() {
        // Has to be empty
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

    @Override
    public GameState swooshThaFuckahsFromBoatThatMovedToTheWrongDirection(final Direction rightOneDirection) {
        return this;
    }

}
