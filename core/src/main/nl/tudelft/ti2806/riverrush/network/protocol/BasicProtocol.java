package nl.tudelft.ti2806.riverrush.network.protocol;

import com.google.inject.Singleton;
import nl.tudelft.ti2806.riverrush.failfast.FailIf;
import nl.tudelft.ti2806.riverrush.network.event.NetworkEvent;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Defines how events are represented as network messages.
 */
@Singleton
public class BasicProtocol implements Protocol {

    public static final String PAIR_SEPERATOR = ";";
    public static final String KEY_VALUE_SEPERATOR = "=";
    public static final String ACTION_KEY = "event";
    private static final int PORT = 8080;

    private final Map<String, EventInstantiator> eventMapping;

    public BasicProtocol() {
        this.eventMapping = new Hashtable<>(10);
    }

    @Override
    public void registerNetworkAction(Class<? extends NetworkEvent> eventClass, EventInstantiator eventInstatiator) {
        eventMapping.put(eventClass.getSimpleName(), eventInstatiator);
    }

    @Override
    public boolean isRegistered(Class<? extends NetworkEvent> eventClass) {
        return eventMapping.containsKey(eventClass.getSimpleName());
    }

    @Override
    public NetworkEvent deserialize(String message) throws InvalidProtocolException, InvalidActionException {
        FailIf.isNull(message);

        String action = null;

        String[] pairs = message.split(BasicProtocol.PAIR_SEPERATOR);
        Map<String, String> fields = new HashMap<>();
        for (String pair : pairs) {
            String[] keyValue = pair.split(BasicProtocol.KEY_VALUE_SEPERATOR);

            if (keyValue.length != 2) {
                throw new InvalidProtocolException("Invalid syntax");
            }

            if (keyValue[0].equals(BasicProtocol.ACTION_KEY)) {
                action = keyValue[1];
            } else {
                fields.put(keyValue[0], keyValue[1]);
            }
        }
        if (action == null) {
            throw new InvalidProtocolException(ACTION_KEY + " field not found but required.");
        }

        EventInstantiator eventInstatiator = eventMapping.get(action);
        if (eventInstatiator == null) {
            throw new InvalidActionException("Unknown " + ACTION_KEY + ": " + action);
        }
        NetworkEvent result = eventInstatiator.instantiate();

        return result.deserialize(fields);
    }

    @Override
    public String serialize(NetworkEvent event) {
        return event.serialize() + PAIR_SEPERATOR + ACTION_KEY + event.getClass().getSimpleName();
    }

    @Override
    public int getPortNumber() {
        return PORT;
    }

}
