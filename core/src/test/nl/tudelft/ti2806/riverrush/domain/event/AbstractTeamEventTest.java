package nl.tudelft.ti2806.riverrush.domain.event;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link AbstractTeamEvent}
 */
public abstract class AbstractTeamEventTest extends EventTest {
    protected AbstractTeamEvent event;

    protected int teamId = 10;


    @BeforeClass
    public static void addParameters() {
        EventTest.getParameters();
        uglyList.add("team");

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
        assertTrue(s.contains("team" + protocol.getKeyValueSeperator() + getTeamId()));
    }

    @Override
    public AbstractTeamEvent getInstance() {
        addVariables(event);
        return event;
    }

    @Test
    public void testDeserializeExtraFunc() throws Exception {
        event = (AbstractTeamEvent) getInstance().deserialize(getTestMap());
        assertEquals(new Integer(teamId), event.getTeam());
    }

    @Override
    public void addVariables(Event event) {
        this.event = (AbstractTeamEvent) event;
        this.event.setTeam(teamId);
        super.addVariables(event);
    }

    public int getTeamId() {
        return teamId;
    }

    @Override
    public int getAnimalId() {
        return -1;
    }
}
