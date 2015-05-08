package nl.tudelft.ti2806.riverrush.network;

import nl.tudelft.ti2806.riverrush.network.Server;
import nl.tudelft.ti2806.riverrush.network.protocol.NetworkMessage;
import nl.tudelft.ti2806.riverrush.domain.entity.Command;
import nl.tudelft.ti2806.riverrush.failfast.FailIf;

import java.util.HashMap;
import java.util.Map;

/**
 * Receives incomming messages from the socket layer
 * and dispatches execution of the correct command to the game.
 *
 * This class is unique per player.
 */
public class PlayerInteractionHandler {

    /**
     * All registered commands are here.
     */
    private final Map<String, Command> registeredCommands;

    /**
     * Fired when the player disconnects.
     * Initializes empty.
     */
    private Command leaveCommand = () -> { };

    /**
     * Constructs a handler for commands that an individual player receives.
     */
    public PlayerInteractionHandler() {
        this.registeredCommands = new HashMap<>();
    }

    /**
     * Register a command to be dispatched when {@code message} is received.
     * @param message - The message byte that indicates what operation the player wants to perform.
     * @param command - The command associated with this message.
     */
    public void onReceive(final String message, final Command command) {
        FailIf.isNull(message, command);
        registeredCommands.put(message, command);
    }

    /**
     * Register a command to be dispatched when the player disconnects.
     * @param command - The command associated with diconnecting.
     */
    public void onLeave(final Command command) {
        FailIf.isNull(command);
        leaveCommand = command;
    }

    /**
     * Check whether a command has been registered under {@code message}.
     * @param message - The message to check for.
     * @return True when registered.
     */
    public boolean isRegistered(final String message) {
        FailIf.isNull(message);
        return registeredCommands.containsKey(message);
    }

    /**
     * Called by the {@link Server} when a message arrives.
     * @param message - The received message.
     */
    public void receive(final NetworkMessage message) {
        FailIf.isNull(message);

        Command command = registeredCommands.get(message.getAction());
        FailIf.isNull(command);
        command.execute();
    }

    /**
     * Called by the {@link Server} when the player leaves.
     */
    public void leave() {
        leaveCommand.execute();
    }
}
