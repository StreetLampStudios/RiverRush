package nl.tudelft.ti2806.riverrush.domain.event;

/**
 * Eventdispatcher.
 */
public interface EventDispatcher {

    /**
     * Add a listener to an {@link Event} type.
     * Multiple registeredListeners per event are possible.
     *
     * @param eventType     - The runtime class to add a listener for.
     * @param handlerLambda - The listener itself.
     */
    <T extends Event> void attach(Class<T> eventType, HandlerLambda<? super T> handlerLambda);

    <T extends Event> void detach(Class<T> eventType, HandlerLambda<? super T> handlerLambda);

    /**
     * Mainly used for testing.
     * Check the amount of registered {@link HandlerLambda}s for a given {@link Event} type.
     *
     * @param eventType - The type of event to check.
     * @return The amount of registered listeners.
     */
    int countRegistered(Class<? extends Event> eventType);


    /**
     * Dispatch one event to registered listeners.
     *
     * @param event - The single event.
     */
    void dispatch(Event event);
}
