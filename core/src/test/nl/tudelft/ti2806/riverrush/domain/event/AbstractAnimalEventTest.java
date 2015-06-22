package nl.tudelft.ti2806.riverrush.domain.event;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Abstract test class for classes that extend {@link AbstractAnimalEvent}.
 */
public abstract class AbstractAnimalEventTest extends EventTest {

    @Override
    public int getAnimalId() {
        return -1;
    }

    @Test
    public void testSerialize() throws Exception {
        event = getInstance();
        event.setAnimal(getAnimalId());

        String s = event.serialize(protocol);
        assertTrue(s.contains(""));
    }


}
