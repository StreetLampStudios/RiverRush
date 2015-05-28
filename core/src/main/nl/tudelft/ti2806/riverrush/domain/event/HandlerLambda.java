package nl.tudelft.ti2806.riverrush.domain.event;

/**
 * Can be used to create lambda's that handle events of a certain type.
 *
 * @param <T> The type of event to handle.
 */
@FunctionalInterface
public interface HandlerLambda<T extends Event> {

    /**
     * Handles an event.
     *
     * @param event The event to handle
     */
    void handle(T event);
}
