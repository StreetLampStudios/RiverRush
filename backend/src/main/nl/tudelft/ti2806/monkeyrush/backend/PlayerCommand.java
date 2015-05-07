package nl.tudelft.ti2806.monkeyrush.backend;

/**
 * Encapsulates a player action.
 * Concrete commands can be registered with a {@link PlayerCommandPublisher},
 * which will handle the command when the appropriate message value is recieved.
 */
public interface PlayerCommand {

    /**
     * Executes the command.
     */
    void execute();
}
