package nl.tudelft.ti2806.riverrush.network.event;

import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

public class RenderJoinEvent implements Event {

    @Override
    public String serialize(Protocol protocol) {
        return null;
    }

    @Override
    public Event deserialize(Map<String, String> keyValuePairs) {
        return null;
    }

    @Override
    public void setPlayer(Player player) {

    }
}
