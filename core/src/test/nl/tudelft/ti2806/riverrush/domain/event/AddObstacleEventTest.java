package nl.tudelft.ti2806.riverrush.domain.event;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Test for {@link AddObstacleEvent}
 */
public class AddObstacleEventTest extends AbstractTeamEventTest {

    private static final double DELTA = 0.001;
    protected AddObstacleEvent event;

    public final double location = 50;

    @BeforeClass
    public static void setup() {
        uglyList.add("location");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        uglyList.remove("location");
    }

    @Override
    @Test
    public void testSerialize() throws Exception{
        event = (AddObstacleEvent) getInstance();
        event.setAnimal(getAnimalId());

        String s = event.serialize(protocol);
        assertTrue(s.contains("location" + protocol.getKeyValueSeperator() + location));
        super.testSerialize();
    }



    @Override
    public AbstractTeamEvent getInstance() {
        event = new AddObstacleEvent();
        event.setLocation(location);
        super.addVariables(event);
        return event;
    }

    @Override
    public Map<String, String> getTestMap() {
        Map<String, String> testMap = new HashMap<>();
        testMap.putAll(super.getTestMap());
        testMap.put("location", Double.toString(location));
        return testMap;
    }

    @Test
    public void testDeserializeExtraFunc() throws Exception {
        event = (AddObstacleEvent) getInstance();
        assertEquals(location, event.getLocation(), DELTA);
    }

}
