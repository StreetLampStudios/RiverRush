package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

public class PlayerJumpedEvent implements Event {

    private Player player;

    @Override
    public String serialize(final Protocol protocol) {
        return "player=" + player.getId();
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        //this.player = new Player(Long.parseLong(keyValuePairs.get("player")));
        return this;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }
}
