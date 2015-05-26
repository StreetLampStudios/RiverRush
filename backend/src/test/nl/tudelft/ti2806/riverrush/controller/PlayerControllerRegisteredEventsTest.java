package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.domain.event.AnimalFellOff;
import nl.tudelft.ti2806.riverrush.domain.event.GameAboutToStartEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameFinishedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameStartedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameStoppedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameWaitingEvent;
import nl.tudelft.ti2806.riverrush.domain.event.PlayerJumpedEvent;
import nl.tudelft.ti2806.riverrush.network.UserServer;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;


@RunWith(Parameterized.class)
public class PlayerControllerRegisteredEventsTest extends RegisteredEventsTest {

    @Mock
    protected UserServer serverMock;

    /**
     * PlayerController should attach handlers for at least
     * these events to notify the player of game state changes.
     */
    @Parameters(name = "{0}")
    public static Collection<Class<?>> parameters() {
        Collection<Class<?>> events = new ArrayList<>();
        events.add(GameStartedEvent.class);
        events.add(GameStoppedEvent.class);
        events.add(GameFinishedEvent.class);
        events.add(GameWaitingEvent.class);
        events.add(GameAboutToStartEvent.class);
        events.add(PlayerJumpedEvent.class);
        events.add(AnimalFellOff.class);
        return events;

    }

    @Override
    public void setup() {
        super.setup();
        this.controller = new PlayerController(this.dispatcherMock, this.serverMock);
    }

    public PlayerControllerRegisteredEventsTest(final Class<?> eventClass) {
        super(eventClass);
    }
}