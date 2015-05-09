package nl.tudelft.ti2806.riverrush.application;

import nl.tudelft.ti2806.riverrush.application.command.Command;
import nl.tudelft.ti2806.riverrush.application.inflector.Inflector;

/**
 * Command bus for executing commands from the server endpoint
 */
public class CommandBus {

    private Inflector inflector;

    public CommandBus(Inflector inflector) {
        this.inflector = inflector;
    }

    public void execute(Command command) {
        this.inflector.inflect(command).handle(command);
    }
}
