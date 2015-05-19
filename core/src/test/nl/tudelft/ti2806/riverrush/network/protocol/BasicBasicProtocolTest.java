package nl.tudelft.ti2806.riverrush.network.protocol;

import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the basic protocol.
 */
@RunWith(MockitoJUnitRunner.class)
public class BasicBasicProtocolTest {

    /**
     * Stub for the event to send over the network.
     */
    private Event eventStub;

    /**
     * Mock for an event.
     */
    @Mock
    private Event eventMock;

    /**
     * Class under test.
     */
    private Protocol protocol;

    /**
     * String representation of a valid serialized event.
     */
    private String stubEventSerialized;

    /**
     * String representation of an invalid serialized event.
     */
    private String unknownEventSerialized;

    /**
     * Initialize the protoco.
     */
    @Before
    public void setUp() {
        this.protocol = new BasicProtocol(0);
        this.eventStub = new StubEvent();
        this.stubEventSerialized = this.protocol.getEventTypeFieldKey()
            + this.protocol.getKeyValueSeperator()
            + this.eventStub.getClass().getSimpleName();

        this.unknownEventSerialized = this.protocol.getEventTypeFieldKey()
            + this.protocol.getKeyValueSeperator()
            + "SomeUnknownEventClass";
    }

    @Test
    public void serialize_callsEvent() throws InvalidActionException {
        this.protocol.serialize(this.eventMock);
        Mockito.verify(this.eventMock).serialize(this.protocol);
    }

    @Test
    public void testRegister() {
        this.protocol.registerNetworkAction(StubEvent.class,
            () -> this.eventStub);
        assertTrue(this.protocol.isRegistered(StubEvent.class));
    }

    @Test
    public void testDeserializeActionOnly() throws InvalidProtocolException,
        InvalidActionException {
        Event expected = new StubEvent();
        this.protocol.registerNetworkAction(StubEvent.class, () -> expected);
        Event actualEvent = this.protocol.deserialize(this.stubEventSerialized);
        assertEquals(expected, actualEvent);
    }

    @Test
    public void testDeserializeWithField() throws InvalidProtocolException,
        InvalidActionException {
        this.protocol.registerNetworkAction(StubEvent.class, StubEvent::new);

        final String expectedField = "field"
                + this.protocol.getKeyValueSeperator() + "HelloWorld"
                + this.protocol.getPairSeperator();
        Event networkMessage = this.protocol.deserialize(expectedField
                + this.stubEventSerialized);
        assertTrue(networkMessage instanceof StubEvent);
        assertEquals("HelloWorld", ((StubEvent) networkMessage).getField());
    }

    @Test(expected = InvalidProtocolException.class)
    public void testDeserializeInvalidProtocol()
        throws InvalidProtocolException, InvalidActionException {
        this.protocol.deserialize("key=a=value");
    }

    @Test(expected = InvalidActionException.class)
    public void testDeserializeInvalidAction() throws InvalidProtocolException,
        InvalidActionException {
        this.protocol.deserialize(this.unknownEventSerialized);
    }

    /**
     * Stub event for testing.
     */
    private class StubEvent implements Event {
        /**
         * A dummy field.
         */
        private String field;

        @Override
        public void setPlayer(Player p) {

        }

        @Override
        public Player getPlayer() {
            return null;
        }

        @Override
        public String serialize(final Protocol p) {
            return "field" + p.getKeyValueSeperator() + this.field;
        }

        @Override
        public Event deserialize(final Map<String, String> keyValuePairs) {
            this.field = keyValuePairs.get("field");
            return this;
        }

        public String getField() {
            return this.field;
        }
    }
}
