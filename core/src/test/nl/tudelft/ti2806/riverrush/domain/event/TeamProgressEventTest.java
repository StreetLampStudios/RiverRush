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
public class TeamProgressEventTest extends AbstractTeamEventTest{

    protected TeamProgressEvent event;

    public final double progress = 50;

    @BeforeClass
    public static void setup() {
        uglyList.add("progress");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        uglyList.remove("progress");
    }

    @Override
    @Test
    public void testSerialize() throws Exception{
        event = (TeamProgressEvent) getInstance();
        event.setAnimal(getAnimalId());

        String s = event.serialize(protocol);
        assertTrue("Did not find progress", s.contains("progress" + protocol.getKeyValueSeperator() + progress));
        super.testSerialize();
    }

    @Override
    public AbstractTeamEvent getInstance() {
        event = new TeamProgressEvent();
        event.setProgress(progress);
        super.addVariables(event);
        return event;
    }

    @Override
    public Map<String, String> getTestMap() {
        Map<String, String> testMap = new HashMap<>();
        testMap.putAll(super.getTestMap());
        testMap.put("progress", Double.toString(progress));
        return testMap;
    }

    @Test
    public void testDeserializeExtraFunc() throws Exception {
        event = (TeamProgressEvent) getInstance();
        assertEquals(new Double(progress), event.getProgress());
    }
}
