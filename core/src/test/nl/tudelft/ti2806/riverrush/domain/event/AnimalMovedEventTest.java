package nl.tudelft.ti2806.riverrush.domain.event;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Test created for {@link AnimalMovedEvent}
 */
public class AnimalMovedEventTest extends AbstractTeamAnimalEventTest {

    public final Direction dir = Direction.LEFT;

    @BeforeClass
    public static void setup() {
        uglyList.add("direction");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        uglyList.remove("direction");
    }

    @Override
    public AbstractTeamAnimalEvent getInstance() {
        AnimalMovedEvent retEvent = new AnimalMovedEvent();
        event = retEvent;
        retEvent.setDirection(dir);
        super.addVariables(retEvent);
        return retEvent;
    }

    @Override
    public Map<String, String> getTestMap() {
        Map<String, String> testMap = new HashMap<>();
        testMap.putAll(super.getTestMap());
        testMap.put("direction", dir.toString());
        return testMap;
    }

    @Test
    public void testDeserializeExtraFunc() {
        event = getInstance();
        AnimalMovedEvent insEvent = (AnimalMovedEvent) event.deserialize(getTestMap());
        assertEquals(dir, insEvent.getDirection());
    }
}
