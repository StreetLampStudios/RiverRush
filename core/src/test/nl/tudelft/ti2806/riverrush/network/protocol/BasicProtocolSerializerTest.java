package nl.tudelft.ti2806.riverrush.network.protocol;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * Tests for the basic protocol serializer
 */
public class BasicProtocolSerializerTest {

    BasicProtocolSerializer protocolSerializer;

    @Before
    public void setUp() throws Exception {
        protocolSerializer = new BasicProtocolSerializer();
    }

    @Test
    public void testSerialize() throws Exception, InvalidActionException {
        Map<String, String> map = new TreeMap<>();
        NetworkMessage networkMessage = new NetworkMessage(Protocol.JOIN_ACTION, map);
        assertEquals("action=join", protocolSerializer.serialize(networkMessage));
    }

    @Test
    public void testSerializeParams() throws Exception, InvalidActionException {
        Map<String, String> map = new TreeMap<>();
        map.put("key", "value");
        NetworkMessage networkMessage = new NetworkMessage(Protocol.JOIN_ACTION, map);
        assertEquals("action=join;key=value", protocolSerializer.serialize(networkMessage));
    }

    @Test
    public void testDeserialize() throws Exception, InvalidProtocolException, InvalidActionException, NonExistingKeyException {
        NetworkMessage networkMessage = protocolSerializer.deserialize("key=value;action=join");
        assertEquals("join", networkMessage.getAction());
        assertTrue(networkMessage.getKeys().contains("key"));
        assertEquals("value", networkMessage.getValue("key"));
    }

    @Test(expected = InvalidProtocolException.class)
    public void testDeserializeInvalidProtocol() throws Exception, InvalidProtocolException, InvalidActionException {
        protocolSerializer.deserialize("key=a=value");
    }

    @Test(expected = InvalidActionException.class)
    public void testDeserializeInvalidAction() throws Exception, InvalidProtocolException, InvalidActionException {
        protocolSerializer.deserialize("action=test");
    }

    @Test
    public void testDeserializeActionOnly() throws Exception, InvalidProtocolException, InvalidActionException {
        NetworkMessage networkMessage = protocolSerializer.deserialize("action=join");
        assertEquals("join", networkMessage.getAction());
    }
}