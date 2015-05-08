package nl.tudelft.ti2806.riverrush.backend;

import nl.tudelft.ti2806.riverrush.backend.network.PlayerInteractionHandler;
import nl.tudelft.ti2806.riverrush.backend.network.Server;
import org.java_websocket.WebSocket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for the socket layer Server class.
 */
@RunWith(MockitoJUnitRunner.class)
public class ServerTest {

    /**
     * Command handler to verify interactions from Server.
     */
    @Mock
    private PlayerInteractionHandler commandHandler;

    /**
     * Placeholder socket for server calls.
     */
    @Mock
    private WebSocket webSocket;

    /**
     * Used to verify calls from the server.
     */
    @Mock
    private JoinHandler joinHandler;

    @Test
    public void onMessage_delegatesToHandler() throws Exception {
        Server server = new Server(0, joinHandler);

    }
}
