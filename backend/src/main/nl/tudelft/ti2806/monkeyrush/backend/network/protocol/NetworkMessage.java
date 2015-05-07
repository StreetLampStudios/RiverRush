package nl.tudelft.ti2806.monkeyrush.backend.network.protocol;

import nl.tudelft.ti2806.monkeyrush.failfast.FailIf;

import java.util.Map;

/**
 * Created by thomas on 7-5-15.
 */
public class NetworkMessage {
    private final String action;
    private final Map<String, String> keyValueStore;
    private static Serializer serializer = new BasicProtocolSerializer();

    public NetworkMessage(String action, final Map<String, String> keyValuePairs ) {
        this.action = action;
        this.keyValueStore = keyValuePairs;
    }

    @Override
    public String toString() {
        return serializer.serialize(this);
    }

    public static NetworkMessage fromString(final String message) {
        return serializer.deserialize(message);
    }

    /**
     * Get the property value associated with the key
     * @param key - The key.
     * @return The property.
     */
    public String getValue(final String key) {
        FailIf.isNull(key);

        return  keyValueStore.get(key);
    }

    public String getAction() {
        return action;
    }
}
