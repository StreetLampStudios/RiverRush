package nl.tudelft.ti2806.riverrush.domain.event;

import static org.junit.Assert.*;

/**
 * Created by Martijn on 12-6-2015.
 */
public class AnimalRemovedEventTest extends AbstractTeamAnimalEventTest {

    @Override
    public AbstractTeamAnimalEvent  getInstance() {
        event = new AnimalRemovedEvent();
        super.addVariables(event);
        return event;
    }

}
