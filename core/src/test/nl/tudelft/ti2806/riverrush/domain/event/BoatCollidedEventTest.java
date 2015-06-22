package nl.tudelft.ti2806.riverrush.domain.event;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Martijn on 12-6-2015.
 */
public class BoatCollidedEventTest extends AbstractTeamEventTest {

    protected BoatCollidedEvent event;

    public final Direction direction = Direction.LEFT;

    @BeforeClass
    public static void setup() {
        uglyList.add("direction");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        uglyList.remove("direction");
    }

    @Override
    @Test
    public void testSerialize() throws Exception {
        event = (BoatCollidedEvent) getInstance();
        event.setAnimal(getAnimalId());

        String s = event.serialize(protocol);
        assertTrue(s.contains("direction" + protocol.getKeyValueSeperator() + direction));
        super.testSerialize();
    }

    @Override
    public AbstractTeamEvent getInstance() {
        event = new BoatCollidedEvent();
        event.setDirection(Direction.LEFT);
        super.addVariables(event);
        return event;
    }

    @Override
    public Map<String, String> getTestMap() {
        Map<String, String> testMap = new HashMap<>();
        testMap.putAll(super.getTestMap());
        testMap.put("direction", direction.toString());
        return testMap;
    }

    @Test
    public void testDeserializeExtraFunc() throws Exception {
        event = (BoatCollidedEvent) getInstance();
        assertEquals(direction, event.getDirection());
    }

}
