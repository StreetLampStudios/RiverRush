package nl.tudelft.ti2806.riverrush.domain.entity.game;

/**
 * Represents the current state of the game.
 */
public interface GameState {
    GameState play();

    GameState stop();

    GameState finish();
}
