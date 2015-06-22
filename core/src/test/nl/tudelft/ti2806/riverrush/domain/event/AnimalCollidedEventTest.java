package nl.tudelft.ti2806.riverrush.domain.event;

/**
 * Created by Martijn on 12-6-2015.
 */
public class AnimalCollidedEventTest extends AbstractTeamAnimalEventTest {

    @Override
    public AbstractTeamAnimalEvent getInstance() {
        event = new AnimalCollidedEvent();
        super.addVariables(event);
        return event;
    }

}
