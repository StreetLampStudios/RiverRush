package nl.tudelft.ti2806.riverrush.network.event;

import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

public class RenderJoinEvent implements Event {

    @Override
    public String serialize(Protocol protocol) {
        return "key=value";
    }

    @Override
    public Event deserialize(Map<String, String> keyValuePairs) {
        return this;
    }
}
