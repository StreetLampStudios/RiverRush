package nl.tudelft.ti2806.riverrush.domain.event;

/**
 * Test for {@link AnimalJumpedEvent}
 */
public class AnimalJumpedEventTest extends AbstractTeamAnimalEventTest {
    @Override
    public AbstractTeamAnimalEvent getInstance() {
        event = new AnimalJumpedEvent();
        super.addVariables(event);
        return event;
    }
}
