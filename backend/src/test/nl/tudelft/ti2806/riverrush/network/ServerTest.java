package nl.tudelft.ti2806.riverrush.network;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;
import org.java_websocket.WebSocket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for the socket layer Server class.
 */
@RunWith(MockitoJUnitRunner.class)
public class ServerTest extends AbstractModule {

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

    /**
     * Class under test.
     */
    private Server server;

    @Before
    public void setUp() {
        Injector injector = Guice.createInjector(this);
        server = injector.getInstance(Server.class);
    }

    @Test
    public void onOpen() throws Exception {

    }

    @Override
    protected void configure() {
        bind(Protocol.class).toInstance(protocolMock);
        bind(EventDispatcher.class).toProvider(() -> dispatcherMock);
    }
}
