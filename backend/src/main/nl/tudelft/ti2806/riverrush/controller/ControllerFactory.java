package nl.tudelft.ti2806.riverrush.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.Server;

@Singleton
public class ControllerFactory {

    /**
     * Provides instances of EventDispatcher when a client joins.
     */
    private final EventDispatcher eventDispatcher;

    /**
     * Provides instance of Game when a client joins.
     */
    private final Game game;

    /**
     * @param dispatcher - A  for {@link EventDispatcher}s.
     * @param game
     */
    @Inject
    public ControllerFactory(EventDispatcher dispatcher, Game game) {
        this.eventDispatcher = dispatcher;
        this.game = game;
    }

    public Controller getController(Server server, String controller) {
        if (controller == null) {
            return null;
        }

        if (controller.equalsIgnoreCase("renderer")) {
            return new RenderController(this.eventDispatcher, server, this.game);
        }

        if (controller.equalsIgnoreCase("player")) {
            return new PlayerController(this.eventDispatcher, server, this.game);
        }

        return null;
    }
}
