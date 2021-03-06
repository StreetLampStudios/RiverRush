package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.domain.event.AnimalAddedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalFellOffEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalJumpedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameAboutToStartEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameFinishedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameStartedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameStoppedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameWaitingEvent;
import nl.tudelft.ti2806.riverrush.network.RenderServer;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Parameterized test hierarchy.
 * Tests whether RenderController register the right events.
 */
@RunWith(Parameterized.class)
public class RenderControllerRegisteredEventsTest extends RegisteredEventsTest {

    @Mock
    protected RenderServer serverMock;

    /**
     * RenderController should attach handlers for at least
     * these events to notify the player of game state changes.
     *
     * @return All events that controllers have to handle.
     */
    @Parameterized.Parameters(name = "{0}")
    public static Collection<Class<?>> parameters() {
        Collection<Class<?>> events = new ArrayList<>();
        events.add(GameStartedEvent.class);
        events.add(GameStoppedEvent.class);
        events.add(GameFinishedEvent.class);
        events.add(GameWaitingEvent.class);
        events.add(GameAboutToStartEvent.class);
        events.add(AnimalJumpedEvent.class);
        events.add(AnimalAddedEvent.class);
        events.add(AnimalFellOffEvent.class);
        return events;
    }

    @Override
    public void setup() {
        super.setup();
        this.controller = new RenderController(this.dispatcherMock, this.serverMock, this.gameMock);
    }

    /**
     * Construct the parameterized test.
     *
     * @param eventClass The event that the RenderController has to attach to.
     */
    public RenderControllerRegisteredEventsTest(final Class<?> eventClass) {
        super(eventClass);
    }
}
