package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by thomas on 21-5-15.
 */
public abstract class AbstractController implements Controller {

    private final EventDispatcher dispatcher;
    private final Map<Class<? extends  Event>, HandlerLambda> handlers;

    public AbstractController(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
        this.handlers = new TreeMap<>();
    }

    /**
     * Convenience method.
     * All handlers registerd with this method will automatically be disposed
     * when the controller is disposed.
     * @param event The event to listen to
     * @param <T> The handler to call when the event gets fired.
     */
    protected <T extends Event> void listenTo(final Class<T> eventClass, final HandlerLambda<? super T> handler) {
        this.handlers.put(eventClass, handler);
        this.dispatcher.attach(eventClass, handler);
    }

    @Override
    public void onSocketMessage(Event event) {
        this.dispatcher.dispatch(event);
    }

    @Override
    public void dispose() {
        handlers.forEach(this.dispatcher::detach);
    }
}
