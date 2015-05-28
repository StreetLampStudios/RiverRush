package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Domain event for a jumping player.
 */
public class PlayerJumpedEvent implements Event {

    private Player player;

    //FIXME: Add player to event from jump event
    @Override
    public String serialize(final Protocol protocol) {
        return "player" + protocol.getKeyValueSeperator() + "1234";
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        this.player = new Player(Long.parseLong(keyValuePairs.get("player")));
        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final Player aPlayer) {
        this.player = aPlayer;
    }
}
