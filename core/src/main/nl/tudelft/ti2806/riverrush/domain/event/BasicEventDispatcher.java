package nl.tudelft.ti2806.riverrush.domain.event;

import java.util.Hashtable;


/**
 * Domain event dispatcher
 */
public class BasicEventDispatcher implements EventDispatcher {

    /**
     * Hashtable for concurrent access.
     */
    private Hashtable<Class<? extends Event>, EventListener> listeners;

    /**
     * Add a listener
     */
    @Override
    public void register(Class<? extends Event> eventType, EventListener eventListener) {
        this.listeners.put(eventType, eventListener);
    }

    /**
     * Return the registered Listeners for a given Event name
     */
    @Override
    public boolean isRegistered(Class<? extends Event> eventType) {
        return this.listeners.containsKey(eventType);
    }

    @Override
    public void dispatch(Event[] events) {
        for (Event event : events) {
            this.dispatch(event);
        }
    }

    @Override
    public void dispatch(Event event) {
        this.listeners.get(event.getClass()).handle(event);
    }
}
