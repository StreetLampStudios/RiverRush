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
public class TeamProgressEventTest extends AbstractTeamEventTest {

    protected TeamProgressEvent event;

    public final double progress = 50;
    public final double speed = 10;


    @BeforeClass
    public static void setup() {
        uglyList.add("progress");
        uglyList.add("speed");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        uglyList.remove("progress");
        uglyList.remove("speed");
    }

    @Override
    @Test
    public void testSerialize() throws Exception {
        event = (TeamProgressEvent) getInstance();
        event.setAnimal(getAnimalId());

        String s = event.serialize(protocol);
        assertTrue("Did not find progress", s.contains("progress" + protocol.getKeyValueSeperator() + progress));
        assertTrue("Did not find speed", s.contains("speed" + protocol.getKeyValueSeperator() + speed));
        super.testSerialize();
    }

    @Override
    public AbstractTeamEvent getInstance() {
        event = new TeamProgressEvent();
        event.setProgress(progress);
        event.setSpeed(speed);
        super.addVariables(event);
        return event;
    }

    @Override
    public Map<String, String> getTestMap() {
        Map<String, String> testMap = new HashMap<>();
        testMap.putAll(super.getTestMap());
        testMap.put("progress", Double.toString(progress));
        testMap.put("speed", Double.toString(speed));
        return testMap;
    }

    @Test
    public void testDeserializeExtraFunc() throws Exception {
        event = (TeamProgressEvent) getInstance();
        assertEquals(new Double(progress), event.getProgress());
    }
}
