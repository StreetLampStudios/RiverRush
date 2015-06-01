package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.domain.event.AnimalFellOffEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameAboutToStartEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameFinishedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameStartedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameStoppedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameWaitingEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalJumpedEvent;
import nl.tudelft.ti2806.riverrush.network.UserServer;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Tests whether controllers register the right events.
 */
@RunWith(Parameterized.class)
public class UserControllerRegisteredEventsTest extends RegisteredEventsTest {

    @Mock
    protected UserServer serverMock;

    /**
     * UserController should attach handlers for at least
     * these events to notify the player of game state changes.
     *
     * @return The collection of events that have to be registered.
     */
    @Parameters(name = "{0}")
    public static Collection<Class<?>> parameters() {
        Collection<Class<?>> events = new ArrayList<>();
        events.add(GameStartedEvent.class);
        events.add(GameStoppedEvent.class);
        events.add(GameFinishedEvent.class);
        events.add(GameWaitingEvent.class);
        events.add(GameAboutToStartEvent.class);
        events.add(AnimalJumpedEvent.class);
        events.add(AnimalFellOffEvent.class);
        return events;

    }

    @Override
    public void setup() {
        super.setup();
        this.controller = new UserController(this.dispatcherMock, this.serverMock);
    }

    /**
     * Constructor for parameterized testing.
     *
     * @param eventClass The collection of events that have to be registered.
     */
    public UserControllerRegisteredEventsTest(final Class<?> eventClass) {
        super(eventClass);
    }
}
