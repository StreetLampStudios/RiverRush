package nl.tudelft.ti2806.riverrush.game.state;


import nl.tudelft.ti2806.riverrush.domain.event.Direction;

/**
 * Represents the current state of the game.
 */
public interface GameState {

    /**
     * Disposes a state. This method should be called to give states the opportunity to detach event
     * handlers.
     */
    void dispose();

    /**
     * Transition when the game wants to start.
     *
     * @return The new state.
     */
    GameState start();

    /**
     * Transition when the game wants to stop.
     *
     * @return The new state.
     */
    GameState stop();

    /**
     * Transition when the game wants to finish.
     *
     * @return The new state.
     * @param team
     */
    GameState finish(Integer team);

    /**
     * Transition when the game is ready to add players.
     *
     * @return The new state.
     */
    GameState waitForPlayers();
}
