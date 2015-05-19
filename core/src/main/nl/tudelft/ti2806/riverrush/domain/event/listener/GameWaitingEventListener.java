package nl.tudelft.ti2806.riverrush.domain.event.listener;

import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameWaitingEvent;

/**
 * Created by m.olsthoorn on 5/18/2015.
 */
public class GameWaitingEventListener extends EventListener<GameWaitingEvent> {
    @Override
    public void handle(GameWaitingEvent event, EventDispatcher dispatcher) {



    }

    @Override
    public Class<GameWaitingEvent> getEventType() {
        return GameWaitingEvent.class;
    }
}
