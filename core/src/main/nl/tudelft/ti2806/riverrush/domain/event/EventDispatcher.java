package nl.tudelft.ti2806.riverrush.domain.event;

/**
 * Created by thomas on 9-5-15.
 */
public interface EventDispatcher {
    void register(Class<? extends Event> eventType, EventListener eventListener);

    boolean isRegistered(Class<? extends Event> eventType);

    void dispatch(Event[] events);

    void dispatch(Event event);
}
