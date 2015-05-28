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

    /**
     * Class that all controller should extend for basic logic.
     *
     * @param eventDispatcher The event dispatcher for dispatching events
     */
    public AbstractController(final EventDispatcher eventDispatcher) {
        this.dispatcher = eventDispatcher;
        this.handlers = new ArrayList<>();
    }

    /**
     * All handlers registered with this method will automatically be disposed
     * when the controller is disposed.
     * @param eventClass The event to listen to
     * @param handler The handler to call when the event gets fired.
     * @param <T> Event
     */
    protected <T extends Event> void listenTo(
        final Class<T> eventClass,
        final HandlerLambda<? super T> handler
    ) {
        this.handlers.add(new Pair<>(eventClass, handler));
        this.dispatcher.attach(eventClass, handler);
    }

    @Override
    public void onSocketMessage(final Event event) {
        this.dispatcher.dispatch(event);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void dispose() {
        handlers.forEach((pair) -> this.dispatcher.detach(pair.l, pair.r));
    }

    /**
     * Used for creating event listeners pairs.
     *
     * @param <L> Event name
     * @param <R> Event handler
     */
    public class Pair<L, R> {

        private L l;
        private R r;

        /**
         * Create a event listener pair.
         *
         * @param event Event name
         * @param handler Event handler
         */
        public Pair(final L event, final R handler) {
            this.l = event;
            this.r = handler;
        }

        public L getEvent() {
            return this.l;
        }

        public R getHandler() {
            return this.r;
        }
    }
}
