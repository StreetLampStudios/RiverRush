package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Created by m.olsthoorn on 5/18/2015.
 */
public class GameStartedEvent implements Event {

    @Override
    public String serialize(Protocol protocol) {
        return "";
    }

    @Override
    public Event deserialize(Map<String, String> keyValuePairs) {
        return this;
    }
}
