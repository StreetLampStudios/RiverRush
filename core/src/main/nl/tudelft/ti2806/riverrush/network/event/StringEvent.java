package nl.tudelft.ti2806.riverrush.network.event;

import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Created by Rob on 18-5-2015.
 */
public class StringEvent implements Event {
    private String message;

    public StringEvent(String message) {
        this.message = message;
    }

    @Override
    public String serialize(Protocol protocol) {
        return message;
    }

    @Override
    public Event deserialize(Map<String, String> keyValuePairs) {
        message = keyValuePairs.get("message");
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
