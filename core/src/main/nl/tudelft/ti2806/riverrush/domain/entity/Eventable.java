package nl.tudelft.ti2806.riverrush.domain.entity;

import nl.tudelft.ti2806.riverrush.domain.event.Event;

/**
 * Event aware enitities
 */
public abstract class Eventable {

    private Event[] events;

    public Event[] releaseEvents() {
        return this.events;
    }
}
