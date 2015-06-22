package nl.tudelft.ti2806.riverrush.domain.event;

/**
 * Created by Martijn on 12-6-2015.
 */
public class AnimalReturnedToBoatEventTest extends AbstractTeamAnimalEventTest {

    @Override
    public AbstractTeamAnimalEvent getInstance() {
        event = new AnimalReturnedToBoatEvent();
        super.addVariables(event);
        return event;
    }

}
