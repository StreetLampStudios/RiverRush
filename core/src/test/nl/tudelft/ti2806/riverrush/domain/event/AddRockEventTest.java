package nl.tudelft.ti2806.riverrush.domain.event;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Martijn on 12-6-2015.
 */
public class AddRockEventTest extends AbstractTeamEventTest {

    protected AddRockEvent event;

    public final Direction direction = Direction.LEFT;

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
        event = (AddRockEvent) getInstance();
        event.setAnimal(getAnimalId());

        String s = event.serialize(protocol);
        assertTrue(s.contains("location" + protocol.getKeyValueSeperator() + direction));
        super.testSerialize();
    }

    @Override
    public AbstractTeamEvent getInstance() {
        event = new AddRockEvent();
        event.setLocation(Direction.LEFT);
        super.addVariables(event);
        return event;
    }

    @Override
    public Map<String, String> getTestMap() {
        Map<String, String> testMap = new HashMap<>();
        testMap.putAll(super.getTestMap());
        testMap.put("location", direction.toString());
        return testMap;
    }

    @Test
    public void testDeserializeExtraFunc() throws Exception {
        event = (AddRockEvent) getInstance();
        assertEquals(direction, event.getLocation());
    }

}
