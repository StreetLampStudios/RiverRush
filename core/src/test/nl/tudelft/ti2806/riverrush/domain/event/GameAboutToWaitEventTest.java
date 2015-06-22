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
public class GameAboutToWaitEventTest extends AbstractAnimalEventTest {

    protected GameAboutToWaitEvent event;

    public final int time = 50;

    @Test
    public void testSerialize() throws Exception {
        event = (GameAboutToWaitEvent) getInstance();
        event.setAnimal(getAnimalId());

        String s = event.serialize(protocol);
        assertTrue(s.contains("time" + protocol.getKeyValueSeperator() + time));
    }

    @BeforeClass
    public static void setup() {
        uglyList.add("time");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        uglyList.remove("time");
    }

    @Override
    public Event getInstance() {
        event = new GameAboutToWaitEvent();
        event.setTimeTillWait(time);
        super.addVariables(event);
        return event;
    }

    @Override
    public Map<String, String> getTestMap() {
        Map<String, String> testMap = new HashMap<>();
        testMap.putAll(super.getTestMap());
        testMap.put("time", Integer.toString(time));
        return testMap;
    }

    @Test
    public void testDeserializeExtraFunc() throws Exception {
        event = (GameAboutToWaitEvent) getInstance();
        assertEquals(time, event.getTimeTillWait());
    }

}
