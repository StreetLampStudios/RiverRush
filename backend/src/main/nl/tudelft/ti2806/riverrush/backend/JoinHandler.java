package nl.tudelft.ti2806.riverrush.backend;

import nl.tudelft.ti2806.riverrush.backend.network.PlayerInteractionHandler;
import org.java_websocket.WebSocket;

/**
 * Interface for handling new connections.
 */
public interface JoinHandler {

    /**
     * Called by the server when a new connection is established.
     * @param connection - The socket that connects the player.
     * @return A handler that is configured to dispatch protocol messages to the animal entity
     */
    PlayerInteractionHandler joinPlayer(WebSocket connection);

}
