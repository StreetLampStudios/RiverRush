package nl.tudelft.ti2806.riverrush.domain.event;

import static org.junit.Assert.*;

/**
 * Test for {@link GameFinishedEvent}
 */
public class GameFinishedEventTest extends AbstractTeamEventTest {

    @Override
    public AbstractTeamEvent getInstance() {
        event = new GameFinishedEvent();
        addVariables(event);
        return event;
    }

}
