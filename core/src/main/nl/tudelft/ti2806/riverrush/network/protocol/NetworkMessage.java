package nl.tudelft.ti2806.riverrush.network.protocol;

import nl.tudelft.ti2806.riverrush.failfast.FailIf;

import java.util.Map;
import java.util.Set;

/**
 * Network message to hold the invariants of the message.
 */
public class NetworkMessage {

    private final String action;
    private final Map<String, String> keyValueStore;

    public NetworkMessage(String action, final Map<String, String> keyValuePairs) throws InvalidActionException {
        FailIf.isNull(action, keyValuePairs);

        if (!Protocol.getActions().contains(action)) {
            throw new InvalidActionException();
        }

        this.action = action;
        this.keyValueStore = keyValuePairs;
    }

    /**
     * Get the property value associated with the key
     *
     * @param key String
     * @return String value
     * @throws NonExistingKeyException
     */
    public String getValue(final String key) throws NonExistingKeyException {
        FailIf.isNull(key);
        if (keyValueStore.containsKey(key)) {
            return keyValueStore.get(key);
        }

        throw new NonExistingKeyException();
    }

    /**
     * Return all keys
     *
     * @return Set<String> set of all the keys
     */
    public Set<String> getKeys() {
        return keyValueStore.keySet();
    }

    /**
     * Get the action of the network message
     *
     * @return String action
     */
    public String getAction() {
        return action;
    }
}
