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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test for the server.
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
    protected AbstractServer server;

    /**
     * Every test needs a fresh {@link AbstractServer} instance.
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
        this.server = injector.getInstance(AbstractServer.class);
    }

    /**
     * When onOpen is called,
     * it should create a new Controller via the injected provider
     */
    @Test
    public void onOpen_usesProviderToCreateController() {
        this.server.onOpen(this.webSocketMock, null);
        verify(this.controllerProviderMock).get();
        verify(this.controllerMocks.get(0)).initialize();
    }

    /**
     * When onMessage receives any event,
     * it should call the Protocol to deserialize the message.
     */
    @Test
    public void onMessage_usesProtocol() {
        this.server.onOpen(this.webSocketMock, null);
        this.server.onMessage(this.webSocketMock, "hello");
        verify(this.protocolMock).deserialize("hello");
    }

    /**
     * When two connections are active,
     * and the server receives an event on one of them,
     * it should call the right Controller's onSocketMessage
     */
    @Test
    public void onMessage_callsController() {
        this.server.onOpen(mock(WebSocket.class), null);
        this.server.onOpen(this.webSocketMock, null);
        this.server.onMessage(this.webSocketMock, "hello");
        verify(this.controllerMocks.get(0), never()).onSocketMessage(any());
        verify(this.controllerMocks.get(1)).onSocketMessage(any(Event.class));
    }

    /**
     * When onClose is called,
     * the server should call Controller.dispose on the correct controller
     */
    @Test
    public void onClose_detachesController() {
        this.server.onOpen(mock(WebSocket.class), null);
        this.server.onOpen(this.webSocketMock, null);
        this.server.onClose(this.webSocketMock, 0, "", true);
        verify(this.controllerMocks.get(1)).dispose();
    }

    /**
     * When a controller calls sendEvent,
     * and the controller is associated with a connection,
     * the server calls WebSocket.send()
     */
    @Test
    public void sendEvent_callsWebSocket() {
        this.server.onOpen(this.webSocketMock, null);

        Event eventMock = mock(Event.class);
        when(eventMock.serialize(any()))
                .thenReturn("serialized-event");

        this.server.sendEvent(eventMock, this.controllerMocks.get(0));
        verify(this.webSocketMock).send("serialized-event");
    }

    /**
     * When a controller calls sendEvent,
     * and the controller is associated with a connection,
     * the server calls Protocol.testSerialize() on the event.
     */
    @Test
    public void sendEvent_callsProtocol() {
        this.server.onOpen(this.webSocketMock, null);

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
