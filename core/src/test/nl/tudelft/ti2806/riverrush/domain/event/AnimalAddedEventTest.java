package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.domain.entity.Sector;
import nl.tudelft.ti2806.riverrush.network.protocol.BasicProtocol;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Test for {@link AnimalAddedEvent}
 */
public class AnimalAddedEventTest extends AbstractTeamAnimalEventTest {

    protected AnimalAddedEvent event;

    public final int variation = 2;
    public final Sector sector = Sector.BACK;

    @BeforeClass
    public static void setup() {
        uglyList.add("sector");
        uglyList.add("variation");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        uglyList.remove("sector");
        uglyList.remove("variation");
    }

    @Override
    public AbstractTeamAnimalEvent  getInstance() {
        event = new AnimalAddedEvent();
        event.setSector(sector);
        event.setVariation(variation);
        super.addVariables(event);
        return event;
    }

    @Override
    public Map<String,String> getTestMap() {
        Map<String, String> testMap = new HashMap<>();
        testMap.putAll(super.getTestMap());
        testMap.put("sector", sector.toString());
        testMap.put("variation", Integer.toString(variation));
        return testMap;
    }

    @Test
    public void testDeserializeExtraFunc() throws Exception {
        event = (AnimalAddedEvent) getInstance();
        assertEquals(sector, event.getSector());
        assertEquals(new Integer(variation), event.getVariation());
    }

}
