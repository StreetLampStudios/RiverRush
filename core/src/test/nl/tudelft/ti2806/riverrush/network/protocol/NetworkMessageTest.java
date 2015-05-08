package nl.tudelft.ti2806.riverrush.network.protocol;

import nl.tudelft.ti2806.riverrush.failfast.NullException;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Network message test.
 */
public class NetworkMessageTest {

    @Test(expected = NullException.class)
    public void testCreateNetworkMessageNullAction() throws InvalidActionException {
        Map<String, String> map = new HashMap<>();
        NetworkMessage networkMessage = new NetworkMessage(null, map);
    }

    @Test(expected = NullException.class)
    public void testCreateNetworkMessageNullMap() throws InvalidActionException {
        NetworkMessage networkMessage = new NetworkMessage(Protocol.JOIN_ACTION, null);
    }

    @Test(expected = InvalidActionException.class)
    public void testInvalidAction() throws InvalidActionException {
        Map<String, String> map = new HashMap<>();
        NetworkMessage networkMessage = new NetworkMessage("test", map);
    }

    @Test
    public void testGetAction() throws InvalidActionException {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        NetworkMessage networkMessage = new NetworkMessage(Protocol.JOIN_ACTION, map);
        assertEquals("join", networkMessage.getAction());
    }

    @Test
    public void testGetValue() throws NonExistingKeyException, InvalidActionException {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        NetworkMessage networkMessage = new NetworkMessage(Protocol.JOIN_ACTION, map);
        assertEquals("value", networkMessage.getValue("key"));
    }

    @Test(expected = NonExistingKeyException.class)
    public void testGetValueNonExistent() throws NonExistingKeyException, InvalidActionException {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        NetworkMessage networkMessage = new NetworkMessage(Protocol.JOIN_ACTION, map);
        assertEquals("", networkMessage.getValue("key2"));
    }
}