package nl.tudelft.ti2806.riverrush.network.protocol;

import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.failfast.FailIf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Defines how events are represented as network messages. This class is
 * singleton.
 */
public final class BasicProtocol implements Protocol {

    private static final Logger LOGGER = LogManager.getLogger(BasicProtocol.class);

    /**
     * We seperate key-value pairs with this character. E.g.:
     * event=JumpCommand;user=12313
     */
    private static final String PAIR_SEPERATOR = ";";

    /**
     * We seperate keys from values with this thing. E.g. event=JumpCommand
     */
    private static final String KEY_VALUE_SEPERATOR = "=";

    /**
     * The key representing the type of event. This could be action or event or
     * whatever.
     */
    private static final String ACTION_KEY = "event";

    /**
     * The port that this protocol operates on.
     */
    private final int port;

    /**
     * Maps event type names to the lambda that instantiates the event.
     */
    private final Map<String, EventInstantiator> eventMapping;

    /**
     * Singleton constructor.
     *
     * @param portNumber - On what port to connect
     */
    public BasicProtocol(final int portNumber) {
        final int expectedEventTypes = 10;
        this.eventMapping = new Hashtable<>(expectedEventTypes);
        this.port = portNumber;
    }

    @Override
    public void registerNetworkMessage(final Class<? extends Event> eventClass,
                                       final EventInstantiator eventInstatiator) {
        this.eventMapping.put(eventClass.getSimpleName(), eventInstatiator);
    }

    @Override
    public boolean isRegistered(final Class<? extends Event> eventClass) {
        return this.eventMapping.containsKey(eventClass.getSimpleName());
    }

    @Override
    public Event deserialize(final String message)
            throws InvalidProtocolException, InvalidActionException {
        FailIf.isNull(message);

        String action = null;

        String[] pairs = message.split(this.getPairSeperator());
        Map<String, String> fields = new HashMap<>();
        for (String pair : pairs) {
            String[] keyValue = pair.split(this.getKeyValueSeperator());

            if (keyValue.length != 2) {
                LOGGER.error("Invalid protocol syntax in message: " + message);
                throw new InvalidProtocolException("Invalid protocol syntax");
            }

            if (keyValue[0].equals(this.getEventTypeFieldKey())) {
                action = keyValue[1];
            } else {
                fields.put(keyValue[0], keyValue[1]);
            }
        }
        if (action == null) {
            LOGGER.error("Protocol field not found: " + this.getEventTypeFieldKey());
            throw new InvalidProtocolException(this.getEventTypeFieldKey()
                    + " field not found but required.");
        }

        EventInstantiator eventInstatiator = this.eventMapping.get(action);
        if (eventInstatiator == null) {
            LOGGER.error("Protocol message not registered: " + action);
            throw new InvalidActionException("Protocol message not registered");
        }
        Event result = eventInstatiator.instantiate();

        return result.deserialize(fields);
    }

    @Override
    public String serialize(final Event event) {
        StringBuilder builder = new StringBuilder();
        builder.append(event.serialize(this));
        if (builder.length() > 0) {
            builder.append(this.getPairSeperator());
        }
        return builder
                .append(this.getEventTypeFieldKey())
                .append(this.getKeyValueSeperator())
                .append(event.getClass().getSimpleName())
                .toString();
    }

    @Override
    public int getPortNumber() {
        return this.port;
    }

    @Override
    public String getKeyValueSeperator() {
        return KEY_VALUE_SEPERATOR;
    }

    @Override
    public String getPairSeperator() {
        return PAIR_SEPERATOR;
    }

    @Override
    public String getEventTypeFieldKey() {
        return ACTION_KEY;
    }

}
