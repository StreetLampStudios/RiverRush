package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.domain.event.Event;

/**
 * Created by thomas on 19-5-15.
 */
public interface Controller {
    /**
     * Handles incoming socket messages.
     * @param event - The received event
     */
    void onSocketMessage(Event event);

    /**
     * Disposes the controller.
     */
    void dispose();

    /**
     * Initializes the controller.
     */
    void initialize();
}
