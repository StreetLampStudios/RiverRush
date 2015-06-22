package nl.tudelft.ti2806.riverrush.domain.event;

/**
 * Test for {@link GameStartedEventTest}
 */
public class GameStartedEventTest extends AbstractAnimalEventTest {

    @Override
    public Event getInstance() {
        event = new GameStartedEvent();
        super.addVariables(event);
        return event;
    }

}
