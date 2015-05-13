package nl.tudelft.ti2806.riverrush.backend.eventlisteners;

import nl.tudelft.ti2806.riverrush.network.event.EchoNetworkEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.EventListener;
import nl.tudelft.ti2806.riverrush.network.event.SendEvent;

/**
 * Created by thomas on 13-5-15.
 */
public class EchoNetworkEventListener extends EventListener<EchoNetworkEvent> {

    @Override
    public void handle(final EchoNetworkEvent event, final EventDispatcher dispatcher) {
        dispatcher.dispatch(new SendEvent(new EchoNetworkEvent()));
    }
}
