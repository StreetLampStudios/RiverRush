package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.game.Game;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;

/**
 * Parameterized test hierarchy.
 * Tests whether controllers register the right events.
 */
public abstract class RegisteredEventsTest {

    @Mock
    protected EventDispatcher dispatcherMock;

    @Mock
    protected Game gameMock;

    protected Controller controller;

    private List<Class<?>> registeredHandlers;

    private Class<?> mustRegister;

    /**
     * Initializes the test.
     * When a controller attaches a handler to an event,
     * the event type is added to a list for verification.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        registeredHandlers = new ArrayList<>();

        Mockito.doAnswer(
                invocation -> {
                    Class<?> eventClass = (Class<?>) invocation.getArguments()[0];
                    registeredHandlers.add(eventClass);
                    return null;
                }).when(dispatcherMock).attach(any(), any());
    }

    /**
     * Construct the parameterized test.
     *
     * @param eventClass The
     */
    public RegisteredEventsTest(final Class<?> eventClass) {
        this.mustRegister = eventClass;
    }

    /**
     * UserController should attach handlers for at least
     * some events to notify the player of game state changes.
     */
    @Test
    public void initialize_shouldRegisterStateEvents() {
        controller.initialize();
        assertTrue(registeredHandlers.contains(this.mustRegister));
    }
}
