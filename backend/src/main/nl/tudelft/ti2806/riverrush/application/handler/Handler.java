package nl.tudelft.ti2806.riverrush.application.handler;

import nl.tudelft.ti2806.riverrush.application.command.Command;

/**
 * Interface for a command handler
 */
public interface Handler {

    /**
     * Handle a Command object
     *
     * @param command
     */
    void handle(Command command);
}
