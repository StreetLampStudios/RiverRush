package nl.tudelft.ti2806.riverrush.domain.event;

import static org.junit.Assert.*;

/**
 * Test for {@link GameWaitingEvent}
 */
public class GameWaitingEventTest extends AbstractAnimalEventTest {

    @Override
    public Event  getInstance() {
        event = new GameWaitingEvent();
        super.addVariables(event);
        return event;
    }

}
