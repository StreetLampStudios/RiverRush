package nl.tudelft.ti2806.riverrush.backend.eventlisteners;

import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.listener.EventListener;
import nl.tudelft.ti2806.riverrush.network.event.JoinEvent;

/**
 * Listens to join events.
 */
public class JoinEventListener extends EventListener<JoinEvent> {

    @Override
    public void handle(final JoinEvent event, final EventDispatcher dispatcher) {
        // ...
    }

    @Override
    public Class<JoinEvent> getEventType() {
        return JoinEvent.class;
    }

}
