package nl.tudelft.ti2806.riverrush.domain.event;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Basic test for every class that implements {@link AbstractTeamAnimalEvent}
 */
public abstract class AbstractTeamAnimalEventTest extends EventTest {

    protected AbstractTeamAnimalEvent event;

    protected int teamId = 10;


    @BeforeClass
    public static void addParameters() {
        EventTest.getParameters();
        uglyList.add("team");
        uglyList.add("animal");

    }

    @AfterClass
    public static void tearDown() throws Exception {
        uglyList.remove("team");
        uglyList.remove("animal");

    }

    @Override
    public Map<String, String> getTestMap() {
        Map<String, String> testMap = new HashMap<>();
        testMap.putAll(super.getTestMap());
        testMap.put("team", Integer.toString(teamId));
        return testMap;
    }

    @Test
    public void testSerialize() throws Exception {
        event = getInstance();
        event.setAnimal(getAnimalId());

        String s = event.serialize(protocol);
        assertTrue(s.contains("animal" + protocol.getKeyValueSeperator() + getAnimalId()));
        assertTrue(s.contains("team" + protocol.getKeyValueSeperator() + getTeamId()));
    }

    @Override
    public AbstractTeamAnimalEvent getInstance() {
        return event;
    }

    @Test
    public void testDeserializeExtraFunc() throws Exception {
        event = getInstance();
        assertEquals(new Integer(teamId), event.getTeam());
    }

    public void addVariables(AbstractTeamAnimalEvent event) {
        event.setTeam(teamId);
        super.addVariables(event);
    }

    public int getTeamId() {
        return teamId;
    }
}
