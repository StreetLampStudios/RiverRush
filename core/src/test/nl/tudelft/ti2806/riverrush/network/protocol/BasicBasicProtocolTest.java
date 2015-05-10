package nl.tudelft.ti2806.riverrush.network.protocol;

import nl.tudelft.ti2806.riverrush.network.event.NetworkEvent;
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
 * Tests for the basic protocol serializer
 */
@RunWith(MockitoJUnitRunner.class)
public class BasicBasicProtocolTest {

    /**
     * Stub for the event to send over the network.
     */
    private NetworkEvent eventStub;

    /**
     * Mock for an event.
     */
    @Mock
    private NetworkEvent eventMock;

    /**
     * Class under test.
     */
    private Protocol protocol;

    private String stubEventSerialized;

    private String unknownEventSerialized;


    @Before
    public void setUp() {
        protocol = new BasicProtocol();
        eventStub = new StubNetworkEvent();
        stubEventSerialized =
                BasicProtocol.ACTION_KEY +
                BasicProtocol.KEY_VALUE_SEPERATOR +
                eventStub.getClass().getSimpleName();

        unknownEventSerialized =
                BasicProtocol.ACTION_KEY +
                BasicProtocol.KEY_VALUE_SEPERATOR +
                "SomeUnknownEventClass";
    }

    @Test
    public void serialize_callsNetworkEvent() throws Exception, InvalidActionException {
        protocol.serialize(eventMock);
        Mockito.verify(eventMock).serialize();
    }

    @Test
    public void testRegister() {
        protocol.registerNetworkAction(StubNetworkEvent.class, () -> eventStub);
        assertTrue(protocol.isRegistered(StubNetworkEvent.class));
    }

    @Test
    public void testDeserializeActionOnly() throws InvalidProtocolException, InvalidActionException {
        NetworkEvent expected = new StubNetworkEvent();
        protocol.registerNetworkAction(StubNetworkEvent.class, () -> expected);
        NetworkEvent actualEvent = protocol.deserialize(stubEventSerialized);
        assertEquals(expected, actualEvent);
    }

    @Test
    public void testDeserializeWithField() throws InvalidProtocolException, InvalidActionException {
        protocol.registerNetworkAction(StubNetworkEvent.class, StubNetworkEvent::new);

        final String expectedField =
                "field" +
                BasicProtocol.KEY_VALUE_SEPERATOR +
                "HelloWorld" +
                BasicProtocol.PAIR_SEPERATOR;
        NetworkEvent networkMessage = protocol.deserialize(expectedField + stubEventSerialized);

        assertTrue(networkMessage instanceof StubNetworkEvent);
        assertEquals("HelloWorld", ((StubNetworkEvent)networkMessage).getField());
    }

    @Test(expected = InvalidProtocolException.class)
    public void testDeserializeInvalidProtocol() throws InvalidProtocolException, InvalidActionException {
        protocol.deserialize("key=a=value");
    }

    @Test(expected = InvalidActionException.class)
    public void testDeserializeInvalidAction() throws InvalidProtocolException, InvalidActionException {
        protocol.deserialize(unknownEventSerialized);
    }


    private class StubNetworkEvent implements NetworkEvent {
        private String field;

        @Override
        public String serialize() {
            return "field" + BasicProtocol.KEY_VALUE_SEPERATOR + field;
        }

        @Override
        public NetworkEvent deserialize(Map<String, String> keyValuePairs) {
            this.field = keyValuePairs.get("field");
            return this;
        }

        public String getField() {
            return field;
        }
    }
}