package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.domain.event.Event;

/**
 * Created by thomas on 19-5-15.
 */
public interface Controller {
    void onSocketMessage(Event event);

    void detach();
}
