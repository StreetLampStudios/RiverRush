package nl.tudelft.ti2806.riverrush.network.event;

import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;
import java.util.Random;

/**
 * Created by Rob on 18-5-2015.
 */
public class JumpEvent implements NetworkEvent {
    @Override
    public String serialize(final Protocol protocol) {
        return "[Serialized string van een JumpEvent]";
    }

    @Override
    public NetworkEvent deserialize(final Map<String, String> keyValuePairs) {
        return this;
    }
}
