package nl.tudelft.ti2806.riverrush.application.inflector;

import nl.tudelft.ti2806.riverrush.application.command.Command;
import nl.tudelft.ti2806.riverrush.application.handler.Handler;

/**
 * Interface for an inflector.
 */
public interface Inflector {

    /**
     * Inflect the command
     *
     * @return Handler for the command
     */
    Handler inflect(Command command);
}
