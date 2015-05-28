package nl.tudelft.ti2806.riverrush.domain.event;

import com.google.inject.Singleton;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Domain event dispatcher.
 * Allows registeredListeners to attach to an event.
 * They get a call whenever an event of their type is fired.
 */
@Singleton
public class BasicEventDispatcher implements EventDispatcher {

    /**
     * Maps event types to a list of listeners.
     */
    private final Map<Class<? extends Event>, List<HandlerLambda>> registeredLambdas = new Hashtable<>();


    @Override
    public <T extends Event> void attach(final Class<T> eventType, final HandlerLambda<? super T> handler) {
        List<HandlerLambda> handlers = registeredLambdas.get(eventType);

        if (handlers == null) {
            handlers = new LinkedList<>();
        }

        handlers.add(handler);
        this.registeredLambdas.put(eventType, handlers);
    }

    @Override
    public <T extends Event> void detach(final Class<T> eventType,
                                         final HandlerLambda<? super T> handlerLambda) {
        List<HandlerLambda> handlers = registeredLambdas.get(eventType);

        if (handlers != null) {
            handlers.remove(handlerLambda);
        }
    }


    @Override
    public int countRegistered(final Class<? extends Event> eventType) {
        List<HandlerLambda> listeners = this.registeredLambdas.get(eventType);
        if (listeners == null) {
            return 0;
        } else {
            return listeners.size();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void dispatch(final Event event) {
        List<HandlerLambda> handlers = this.registeredLambdas.get(event.getClass());
        if (handlers != null) {
            handlers.forEach(
                (f) -> f.handle(event)
            );
        }
    }
}
