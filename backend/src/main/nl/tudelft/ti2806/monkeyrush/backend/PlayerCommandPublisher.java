package nl.tudelft.ti2806.monkeyrush.backend;

import nl.tudelft.ti2806.monkeyrush.failfast.FailIf;

import java.util.HashMap;
import java.util.Map;

/**
 * Receives incomming messages from the socket layer
 * and dispatches execution of the correct command to the game.
 *
 * This class is unique per player.
 */
public class PlayerCommandPublisher {

    /**
     * All registered commands are here.
     */
    private final Map<Byte, PlayerCommand> registeredCommands;

    /**
     * Constructs a handler for commands that an individual player receives.
     */
    public PlayerCommandPublisher() {
        this.registeredCommands = new HashMap<>();
    }

    /**
     * Register a command to be dispatched when {@code message} is received.
     * @param message - The message byte that indicates what operation the player wants to perform.
     * @param command - The command associated with this message.
     */
    public void registerCommand(final Byte message, final PlayerCommand command) {
        FailIf.isNull(message, command);
        registeredCommands.put(message, command);
    }

    /**
     * Check whether a command has been registered under {@code message}.
     * @param message - The message to check for.
     * @return True when registered.
     */
    public boolean isRegistered(final Byte message) {
        FailIf.isNull(message);
        return registeredCommands.containsKey(message);
    }
}
