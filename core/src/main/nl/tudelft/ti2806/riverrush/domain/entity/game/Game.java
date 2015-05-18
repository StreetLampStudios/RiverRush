package nl.tudelft.ti2806.riverrush.domain.entity.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import nl.tudelft.ti2806.riverrush.GameConfiguration;
import nl.tudelft.ti2806.riverrush.domain.entity.Team;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

import java.util.ArrayList;

/**
 * Represents an ongoing or waiting game.
 */
@Singleton
public class Game {

    /**
     * The current state of the game.
     */
    private GameState gameState;

    /**
     * Construct an application.
     */
    @Inject
    public Game(final EventDispatcher dispatcher) {
        this.gameState = new WaitingGameState(dispatcher);
    }

    public void play() {
        this.gameState = this.gameState.play();
    }

    public void stop() {
        this.gameState = this.gameState.stop();
    }

    public void finish() {
        this.gameState = this.gameState.finish();
    }
}
