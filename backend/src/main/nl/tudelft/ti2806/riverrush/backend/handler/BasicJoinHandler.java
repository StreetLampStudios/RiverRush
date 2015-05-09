package nl.tudelft.ti2806.riverrush.backend.handler;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.Monkey;
import org.java_websocket.WebSocket;

/**
 * Handles join and leave requests.
 */
public class BasicJoinHandler implements JoinHandler {

    @Override
    public PlayerInteractionHandler joinPlayer(final WebSocket playerConnection) {
        AbstractAnimal player = initPlayer(playerConnection);
        PlayerInteractionHandler interactionHandler = initInteractionHandler(playerConnection, player);

        return interactionHandler;
    }

    /**
     * Creates and configures the player instance.
     * @param playerConnection - The socket connected to the player.
     * @return A new player.
     */
    private AbstractAnimal initPlayer(final WebSocket playerConnection) {
        AbstractAnimal player = new Monkey();
        player.onChanged(() -> playerConnection.send("The player state has changed."));
        return player;
    }

    /**
     * Create and configure the interactionHandler that handles player actions.
     * @param playerConnection - The socket connected to the player.
     * @param player - The player involved.
     * @return A new handler for the actions of a player.
     */
    private PlayerInteractionHandler initInteractionHandler(final WebSocket playerConnection, final AbstractAnimal player) {
        PlayerInteractionHandler interactionHandler = new PlayerInteractionHandler();

        interactionHandler.onReceive("jump", player::jump);

        interactionHandler.onLeave(() -> leavePlayer(player, playerConnection));
        return interactionHandler;
    }

    /**
     * Called by the {@link PlayerInteractionHandler} when a player has disconnected.
     * @param connection - The socket that connects the player.
     * @param player - The player leaving.
     */
    private void leavePlayer(final AbstractAnimal player, final WebSocket connection) {
        if (!connection.isClosed() || connection.isClosing()) {
            connection.close();
        }
        player.leave();
    }
}
