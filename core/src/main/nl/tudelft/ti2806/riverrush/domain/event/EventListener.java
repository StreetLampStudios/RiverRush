package nl.tudelft.ti2806.riverrush.domain.event;

/**
 * Event listener.
 */
@FunctionalInterface
public interface EventListener {

    /**
     * Handle a Domain Event
     *
     * @param event
     */
    void handle(Event event);
}
