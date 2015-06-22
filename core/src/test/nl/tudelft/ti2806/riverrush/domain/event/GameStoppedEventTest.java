package nl.tudelft.ti2806.riverrush.domain.event;

/**
 * Created by Martijn on 12-6-2015.
 */
public class GameStoppedEventTest extends AbstractAnimalEventTest {

    @Override
    public Event getInstance() {
        event = new GameStoppedEvent();
        super.addVariables(event);
        return event;
    }

}
