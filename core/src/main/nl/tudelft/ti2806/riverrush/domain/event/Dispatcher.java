package nl.tudelft.ti2806.riverrush.domain.event;

import java.util.HashMap;

/**
 * Domain event dispatcher
 */
public class Dispatcher {

    /**
     *
     */
    private HashMap<String, Listener> listeners;

    /**
     * Add a Listener
     */
    public void add(String name, Listener listener) {
        this.listeners.put(name, listener);
    }

    /**
     * Return the registered Listeners for a given Event name
     */
    public boolean isRegistered(String name) {
        return this.listeners.containsKey(name);
    }

    public void dispatch(Event[] events) {
        for (Event event : events) {
            this.listeners.get(event.getName()).handle(event);
        }
    }
}
