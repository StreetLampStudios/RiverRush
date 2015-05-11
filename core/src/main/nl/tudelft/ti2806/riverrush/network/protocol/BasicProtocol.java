package nl.tudelft.ti2806.riverrush.network.protocol;

import com.google.inject.Singleton;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.failfast.FailIf;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Defines how events are represented as network messages. This class is
 * singleton.
 */
@Singleton
public final class BasicProtocol implements Protocol {
    /**
     * For thread synchronization.
     */
    private static final Object LOCK = new Object();
    /**
     * We seperate key-value pairs with this character. E.g.:
     * event=JoinEvent;user=bob
     */
    private static final String PAIR_SEPERATOR = ";";
    /**
     * We seperate keys from values with this thing. E.g. event=JoinEvent
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
    private static final int PORT = 8080;
    /**
     * The singleton instance.
     */
    private static BasicProtocol instance;
    /**
     * Maps event type names to the lambda that instantiates the event.
     */
    private final Map<String, EventInstantiator> eventMapping;

    /**
     * Singleton constructor.
     */
    private BasicProtocol() {
        final int expectedEventTypes = 10;
        this.eventMapping = new Hashtable<>(expectedEventTypes);
    }

    /**
     * Get the singleton instance of this protocol.
     *
     * @return One instance, always the same.
     */
    public static BasicProtocol getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new BasicProtocol();
            }
        }
        return instance;
    }

    @Override
    public void registerNetworkAction(final Class<? extends Event> eventClass,
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
                throw new InvalidProtocolException("Invalid syntax");
            }

            if (keyValue[0].equals(this.getEventTypeFieldKey())) {
                action = keyValue[1];
            } else {
                fields.put(keyValue[0], keyValue[1]);
            }
        }
        if (action == null) {
            throw new InvalidProtocolException(this.getEventTypeFieldKey()
                + " field not found but required.");
        }

        EventInstantiator eventInstatiator = this.eventMapping.get(action);
        if (eventInstatiator == null) {
            throw new InvalidActionException("Unknown "
                + this.getEventTypeFieldKey() + ": " + action);
        }
        Event result = eventInstatiator.instantiate();

        return result.deserialize(fields);
    }

    @Override
    public String serialize(final Event event) {
        return event.serialize(this) + this.getPairSeperator()
            + this.getEventTypeFieldKey()
            + event.getClass().getSimpleName();
    }

    @Override
    public int getPortNumber() {
        return PORT;
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