package nl.tudelft.ti2806.riverrush.network;

import nl.tudelft.ti2806.riverrush.controller.Controller;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameWaitingEvent;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;

public class RenderController implements Controller {
    private final Server server;
    private final EventDispatcher dispatcher;
    private final HandlerLambda onGameStateChangedLambda = this::onGameStateChanged;

    public RenderController(EventDispatcher eventDispatcher, Server server) {
        this.dispatcher = eventDispatcher;
        this.server = server;
        this.dispatcher.attach(GameWaitingEvent.class, onGameStateChangedLambda);
    }

    private void onGameStateChanged(Event event) {
        this.server.sendEvent(event, this);
    }

    @Override
    public void onSocketMessage(Event event) {
        this.dispatcher.dispatch(event);
    }

    @Override
    public void detach() {
        this.dispatcher.detach(GameWaitingEvent.class, onGameStateChangedLambda);
    }
}
