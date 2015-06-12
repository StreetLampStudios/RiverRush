package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test for {@link AddObstacleEvent}
 */
public class AddObstacleEventTest {

    private AddObstacleEvent event;
    private Protocol protocol;

    @Before
    public void setUp() {
        event = new AddObstacleEvent();
        event.setTeam(10);
        event.setLocation(0.1);
        protocol = mock(Protocol.class);
        when(protocol.getPairSeperator()).thenReturn(";");
        when(protocol.getKeyValueSeperator()).thenReturn("=");
    }

    @Test
    public void testSerialize() throws Exception {
        event.serialize(protocol);
    }

    @Test
    public void testDeserialize() throws Exception {

    }

    @Test
    public void testGetAnimal() throws Exception {

    }

    @Test
    public void testSetAnimal() throws Exception {

    }

    @Test
    public void testGetLocation() throws Exception {

    }

    @Test
    public void testSetLocation() throws Exception {

    }

    @Test
    public void testGetTeam() throws Exception {

    }

    @Test
    public void testSetTeam() throws Exception {

    }
}
