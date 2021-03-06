package nl.tudelft.ti2806.riverrush.game.state;

import nl.tudelft.ti2806.riverrush.domain.event.Event;

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
     * @param team The team that wins
     * @return The new state.
     */
    GameState finish(final Integer team);

    /**
     * Transition when the game is ready to add players.
     *
     * @return The new state.
     */
    GameState waitForPlayers();

    /**
     * Get the event for the current state to send to new connections.
     *
     * @return The event for the current state
     */
    Event getStateEvent();
}
