package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

public class PlayerJumpedEvent implements Event {

    private final Player player;

    public PlayerJumpedEvent(Player player) {
        this.player = player;
    }

    @Override
    public String serialize(Protocol protocol) {
        return "player="+player.getId();
    }

    @Override
    public Event deserialize(Map<String, String> keyValuePairs) {
        player
        return this;
    }
}
