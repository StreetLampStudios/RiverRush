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

    /**
     * The event dispatcher of this class.
     */
    private final EventDispatcher dispatcher;
    /**
     * A list of handlers and their assigned action.
     */
    private final List<Pair<Class<? extends Event>, HandlerLambda>> handlers;

    /**
     *
     * @param eventDispatcher
     * - The event dispatcher of this controller.
     */
    public AbstractController(final EventDispatcher eventDispatcher) {
        this.dispatcher = eventDispatcher;
        this.handlers = new ArrayList<>();
    }

    /**
     * All handlers registerd with this method will automatically be disposed
     * when the controller is disposed.
     * @param eventClass The event to listen to
     * @param handler The handler to call when the event gets fired.
     */
    protected <T extends Event> void listenTo(final Class<T> eventClass,
                                              final HandlerLambda<? super T> handler) {
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
     * A pair.
     */
    public class Pair<L, R> {
        public L getL() {
            return l;
        }

        public void setL(final L setL) {
            this.l = setL;
        }

        public R getR() {
            return r;
        }

        public void setR(final R setR) {
            this.r = setR;
        }

        /**
         * Left side of the pair.
         */
        private L l;
        /**
         * Right side of the pair.
         */
        private R r;

        /**
         * Pairs a left and right side.
         * @param setL left side
         * @param setR right side
         */
        public Pair(final L setL, final R setR) {
            setL(setL);
            setR(setR);
        }
    }
}
