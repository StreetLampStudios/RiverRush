package nl.tudelft.ti2806.riverrush.network;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import nl.tudelft.ti2806.riverrush.controller.Controller;
import nl.tudelft.ti2806.riverrush.controller.ControllerFactory;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.network.event.JoinEvent;
import nl.tudelft.ti2806.riverrush.network.event.JumpEvent;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;
import org.java_websocket.WebSocket;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Tests for the socket layer Server class.
 */
public class ServerTest extends AbstractModule {
    /**
     * For dependency injection.
     */
    private Injector injector;

    /**
     * Placeholder socket for server calls.
     */
    @Mock
    private WebSocket webSocketMock;

    /**
     * Used to verify calls from the server.
     */
    @Mock
    private EventDispatcher dispatcherMock;


    /**
     * Used to verify calls from the server.
     */
    @Mock
    private Protocol protocolMock;

    @Mock
    private ControllerFactory factoryMock;

    @Mock
    private Controller controllerMock;

    /**
     * Class under test.
     */
    private Server server;

    /**
     * Every test needs a fresh {@link Server} instance.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(this.protocolMock.deserialize("join"))
            .thenReturn(new JoinEvent());

        Mockito.when(this.protocolMock.deserialize("jump"))
            .thenReturn(new JumpEvent());

        Mockito.when(this.factoryMock.getController(any(Server.class), any(String.class)))
            .thenReturn(this.controllerMock);

        this.injector = Guice.createInjector(this);
        this.server = this.injector.getInstance(Server.class);
    }


    /**
     * onMessage should use the Protocol to create an event, and then dispatch
     * the event via EventDispatcher.
     */
    @Test
    public void onMessage_usesProtocolAndDispatches() {
        this.server.onMessage(this.webSocketMock, "join");
        this.server.onMessage(this.webSocketMock, "jump");
        verify(this.controllerMock).onSocketMessage(any(JumpEvent.class));
    }

    /**
     * onClose
     */

    /**
     * Configures injection of mocks.
     */
    @Override
    protected void configure() {
        this.bind(Protocol.class).toInstance(this.protocolMock);

        // Every time a new EventDispatcher is requested by code under test,
        // Guice will inject a fresh mock.
        this.bind(EventDispatcher.class).toInstance(dispatcherMock);
        this.bind(Server.class);
        this.bind(ControllerFactory.class).toInstance(factoryMock);
    }
}
