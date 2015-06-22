package nl.tudelft.ti2806.riverrush.domain.event;

/**
 * Test for {@link AnimalFellOffEvent}
 */
public class AnimalFellOffEventTest extends AbstractTeamAnimalEventTest {

    @Override
    public AbstractTeamAnimalEvent getInstance() {
        event = new AnimalFellOffEvent();
        super.addVariables(event);
        return event;
    }

}
