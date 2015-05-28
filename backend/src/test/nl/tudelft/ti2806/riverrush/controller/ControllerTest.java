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
 * Tests the controller.
 */
public abstract class ControllerTest {
    public EventDispatcher getDispatcherMock() {
        return dispatcherMock;
    }

    public void setDispatcherMock(final EventDispatcher setDispatcherMock) {
        this.dispatcherMock = setDispatcherMock;
    }

    /**
     * Mocks a dispatcher.
     */
    @Mock
    private EventDispatcher dispatcherMock;

    public AbstractServer getServerMock() {
        return serverMock;
    }

    public void setServerMock(final AbstractServer setServerMock) {
        this.serverMock = setServerMock;
    }

    /**
     * Mocks a server.
     */
    @Mock
    private AbstractServer serverMock;

    public Controller getController() {
        return controller;
    }

    public void setController(final Controller setController) {
        this.controller = setController;
    }

    /**
     * A controller.
     */
    private Controller controller;

    /**
     * The handlers that are not removed yet.
     */
    private List<Class<?>> unRemovedHandlers;

    /**
     * Sets up for the tests.
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
