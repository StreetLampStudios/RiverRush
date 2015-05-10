package nl.tudelft.ti2806.riverrush.network.event;

import java.util.Map;

/**
 * Hmmmm data bag..?
 */
public class JoinEvent implements NetworkEvent {

    @Override
    public String serialize() {
        return "";
    }

    @Override
    public NetworkEvent deserialize(Map<String, String> keyValuePairs) {
        return this;
    }
}
