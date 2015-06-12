package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.domain.entity.Sector;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Martijn on 12-6-2015.
 */
public class AnimalCollidedEventTest extends AbstractTeamAnimalEventTest {

    @Override
    public AbstractTeamAnimalEvent  getInstance() {
        event = new AnimalCollidedEvent();
        super.addVariables(event);
        return event;
    }

}
