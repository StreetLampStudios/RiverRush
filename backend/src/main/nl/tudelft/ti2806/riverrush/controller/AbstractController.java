package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates common operations for controllers.
 * Like registering listeners for game state.
 */
public abstract class AbstractController implements Controller {

    private final EventDispatcher dispatcher;
    private final List<Pair<Class<? extends Event>, HandlerLambda>> handlers;

    public AbstractController(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
        this.handlers = new ArrayList<>();
    }

    /**
     * All handlers registerd with this method will automatically be disposed
     * when the controller is disposed.
     * @param eventClass The event to listen to
     * @param <T> The handler to call when the event gets fired.
     */
    protected <T extends Event> void listenTo(final Class<T> eventClass, final HandlerLambda<? super T> handler) {
        this.handlers.add(new Pair<>(eventClass, handler));
        this.dispatcher.attach(eventClass, handler);
    }

    @Override
    public void onSocketMessage(Event event) {
        this.dispatcher.dispatch(event);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void dispose() {
        handlers.forEach((pair) -> this.dispatcher.detach(pair.l, pair.r));
    }

    public class Pair<L,R> {
        public L l;
        public R r;
        public Pair(L l, R r){
            this.l = l;
            this.r = r;
        }
    }
}
