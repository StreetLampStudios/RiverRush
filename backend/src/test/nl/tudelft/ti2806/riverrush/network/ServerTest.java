package nl.tudelft.ti2806.riverrush.network;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import nl.tudelft.ti2806.riverrush.controller.Controller;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;
import org.java_websocket.WebSocket;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by thomas on 21-5-15.
 */
public abstract class ServerTest extends AbstractModule {


    /**
     * Placeholder socket for server calls.
     */
    @Mock
    protected WebSocket webSocketMock;
    /**
     * Used to verify calls from the server.
     */
    @Mock
    protected EventDispatcher dispatcherMock;
    /**
     * Used to verify calls from the server.
     */
    @Mock
    protected Protocol protocolMock;

    @Mock
    protected Provider<Controller> controllerProviderMock;

    @Mock
    protected Controller controllerMock;
    /**
     * Class under test.
     */
    protected Server server;

    /**
     * Every test needs a fresh {@link Server} instance.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(this.protocolMock.deserialize("hello"))
            .thenReturn(new StubEvent());

        Mockito.when(this.controllerProviderMock.get())
            .thenReturn(this.controllerMock);

        Injector injector = Guice.createInjector(this);
        this.server = injector.getInstance(Server.class);
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
     * When onMessage has registered a controller,
     * and receives a JumpEvent,
     * it should call the right Controller's onSocketMessage
     */
    @Test
    public void onMessage_callsController() {
        this.server.onMessage(this.webSocketMock, "join");
        this.server.onMessage(this.webSocketMock, "hello");
        verify(this.controllerMock).onSocketMessage(any(StubEvent.class));
    }

    protected class StubEvent implements Event {
        @Override
        public String serialize(Protocol protocol) {
            return "";
        }

        @Override
        public Event deserialize(Map<String, String> keyValuePairs) {
            return this;
        }
    }
}
