package nl.tudelft.ti2806.riverrush.domain.event;

/**
 * Event listener.
 */
public interface Listener {

    /**
     * Handle a Domain Event
     *
     * @param event
     */
    void handle(Event event);
}
