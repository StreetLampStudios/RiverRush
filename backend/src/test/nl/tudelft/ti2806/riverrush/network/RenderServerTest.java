package nl.tudelft.ti2806.riverrush.network;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import nl.tudelft.ti2806.riverrush.controller.Controller;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.network.event.RenderJoinEvent;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;
import org.java_websocket.WebSocket;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.google.inject.name.Names.named;
import static org.mockito.Mockito.verify;

/**
 * Created by thomas on 21-5-15.
 */
public class RenderServerTest extends AbstractModule {
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
    private Provider<Controller> controllerProviderMock;

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
            .thenReturn(new RenderJoinEvent());

        Mockito.when(this.controllerProviderMock.get())
            .thenReturn(this.controllerMock);

        this.injector = Guice.createInjector(this);
        this.server = this.injector.getInstance(Server.class);
    }


    /**
     * When onMessage receives a JoinEvent,
     * it should create a new Controller via the injected provider
     */
    @Test
    public void onMessage_usesProviderToCreateController() {
        this.server.onMessage(this.webSocketMock, "join");
        verify(this.controllerProviderMock).get();
    }

    /**
     * When onMessage receives any event,
     * it should call the Protocol to deserialize the message.
     */
    @Test
    public void onMessage_usesProtocol() {
        this.server.onMessage(this.webSocketMock, "join");
        verify(this.protocolMock).deserialize("join");
    }

    /**
     * onClose
     */

    /**
     * Configures injection of mocks.
     */
    @Override
    protected void configure() {
        // Every time a new EventDispatcher is requested by code under test,
        // Guice will inject a fresh mock.
        this.bind(EventDispatcher.class).toInstance(dispatcherMock);

        this.bind(Controller.class)
            .annotatedWith(named("renderController"))
            .toProvider(this.controllerProviderMock);

        this.bind(Protocol.class)
            .annotatedWith(named("renderProtocol"))
            .toInstance(this.protocolMock);

        this.bind(Server.class).to(RenderServer.class);
    }
}
