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
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

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

    protected List<Controller> controllerMocks;

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

        this.controllerMocks = new ArrayList<>();

        when(this.protocolMock.deserialize("hello"))
            .thenReturn(mock(Event.class));

        when(this.protocolMock.serialize(any(Event.class)))
            .then(invocation -> {
                Event argument = (Event) invocation.getArguments()[0];
                return argument.serialize((Protocol) invocation.getMock());
            });

        when(this.controllerProviderMock.get())
            .then(invocation -> {
                Controller c = mock(Controller.class);
                this.controllerMocks.add(c);
                return c;
            });

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
        verify(this.controllerMocks.get(0)).initialize();
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
     * and receives a join event,
     * it should call the right Controller's onSocketMessage
     */
    @Test
    public void onMessage_callsController() {
        this.server.onMessage(mock(WebSocket.class), "join");
        this.server.onMessage(this.webSocketMock, "join");
        this.server.onMessage(this.webSocketMock, "hello");
        verify(this.controllerMocks.get(0), never()).onSocketMessage(any());
        verify(this.controllerMocks.get(1)).onSocketMessage(any(Event.class));
    }

    /**
     * When onClose is called,
     * the server should call Controller.detach on the correct controller
     */
    @Test
    public void onClose_detachesController() {
        this.server.onMessage(mock(WebSocket.class), "join");
        this.server.onMessage(this.webSocketMock, "join");
        this.server.onClose(this.webSocketMock, 0, "", true);
        verify(this.controllerMocks.get(1)).detach();
    }

    /**
     * When a single connection calls join twice,
     * the server silently rejects the last one.
     */
    @Test
    public void onMessage_joinTwice_rejectLastJoin() {
        this.server.onMessage(this.webSocketMock, "join");
        this.server.onMessage(this.webSocketMock, "join");
        assertEquals(1, this.controllerMocks.size());
    }

    /**
     * When a controller calls sendEvent,
     * and the controller is associated with a connection,
     * the server calls WebSocket.send()
     */
    @Test
    public void sendEvent_callsWebSocket() {
        this.server.onMessage(this.webSocketMock, "join");

        Event eventMock = mock(Event.class);
        when(eventMock.serialize(any()))
            .thenReturn("serialized-event");

        this.server.sendEvent(eventMock, this.controllerMocks.get(0));
        verify(this.webSocketMock).send("serialized-event");
    }

    /**
     * When a controller calls sendEvent,
     * and the controller is associated with a connection,
     * the server calls Protocol.serialize() on the event.
     */
    @Test
    public void sendEvent_callsProtocol() {
        this.server.onMessage(this.webSocketMock, "join");

        Event eventMock = mock(Event.class);
        this.server.sendEvent(eventMock, this.controllerMocks.get(0));
        verify(this.protocolMock).serialize(eventMock);
    }

    /**
     * When a controller calls sendEvent,
     * but there is no connection associates with the controller,
     * the method should throw a NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void sendEvent_noConnection_errors() {
        this.server.sendEvent(mock(Event.class), mock(Controller.class));
    }
}
