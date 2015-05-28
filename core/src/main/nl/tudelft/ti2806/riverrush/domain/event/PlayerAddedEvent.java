package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Domain event for when a player is added to the game.
 */
public class PlayerAddedEvent implements Event {

    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final Player aPlayer) {
        this.player = aPlayer;
    }

    @Override
    public String serialize(final Protocol protocol) {
        return "player" + protocol.getKeyValueSeperator() + player.getId();
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        this.player = new Player(Long.parseLong(keyValuePairs.get("player")));
        return this;
    }
}
