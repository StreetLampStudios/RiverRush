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

    @Test
    public void testGetValue() throws NonExistingKeyException {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        NetworkMessage networkMessage = new NetworkMessage("join", map);
        assertEquals("value", networkMessage.getValue("key"));
    }

    @Test(expected = NonExistingKeyException.class)
    public void testGetValueNonExistent() throws NonExistingKeyException {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        NetworkMessage networkMessage = new NetworkMessage("join", map);
        assertEquals("", networkMessage.getValue("key2"));
    }

    @Test(expected = NullException.class)
    public void testCreateNetworkMessageNullAction() {
        Map<String, String> map = new HashMap<>();
        NetworkMessage networkMessage = new NetworkMessage(null, map);
    }

    @Test(expected = NullException.class)
    public void testCreateNetworkMessageNullMap() {
        NetworkMessage networkMessage = new NetworkMessage("join", null);
    }

    @Test
    public void testGetAction() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        NetworkMessage networkMessage = new NetworkMessage("join", map);
        assertEquals("join", networkMessage.getAction());
    }
}