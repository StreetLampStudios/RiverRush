package nl.tudelft.ti2806.riverrush.network.event;

import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Created by thomas on 13-5-15.
 */
public class EchoNetworkEvent implements Event {
    @Override
    public String serialize(final Protocol protocol) {
        return "pizza";
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        return this;
    }

    @Override
    public void setPlayer(Player p) {

    }

    @Override
    public Player getPlayer() {
        return null;
    }
}
