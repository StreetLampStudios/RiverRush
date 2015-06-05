package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.network.AbstractServer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Tests for each concrete controller class.
 */
public abstract class ControllerTest {

    /**
     * Mocks a dispatcher.
     */
    @Mock
    protected EventDispatcher dispatcherMock;

    /**
     * Mocks a server.
     */
    @Mock
    protected AbstractServer serverMock;

    /**
     * A controller.
     */
    protected Controller controller;

    /**
     * The handlers that are not removed yet.
     */
    private List<Class<?>> unRemovedHandlers;

    /**
     * Initialize the test.
     * When a controller attaches an event handler, this event class is added to a list.
     * When a controller detaches a handler, the event class is removed from the list.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        unRemovedHandlers = new ArrayList<>();

        Mockito.doAnswer(
            invocation -> {
                Class<?> eventClass = (Class<?>) invocation.getArguments()[0];
                unRemovedHandlers.add(eventClass);
                return null;
            }).when(dispatcherMock).attach(any(), any());

        Mockito.doAnswer(
            invocation -> {
                Class<?> eventClass = (Class<?>) invocation.getArguments()[0];
                unRemovedHandlers.remove(eventClass);
                return null;
            }).when(dispatcherMock).detach(any(), any());
    }

    /**
     * By good conventions, the constructor should do nothing but initialize fields.
     * For registering handlers etc, the initialize() method should be used.
     */
    @Test
    public void constructorCallsNothing() {
        verifyZeroInteractions(dispatcherMock, serverMock);
    }

    /**
     * Every handler registered should be detached when Controller.dispose() is called.
     */
    @Test
    public void disposeShouldDetachAll() {
        controller.initialize();
        controller.dispose();
        assertTrue(unRemovedHandlers.isEmpty());
    }
}
