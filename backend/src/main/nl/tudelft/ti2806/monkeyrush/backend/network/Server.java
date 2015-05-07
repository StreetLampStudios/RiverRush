package nl.tudelft.ti2806.monkeyrush.backend.network;

import nl.tudelft.ti2806.monkeyrush.backend.JoinHandler;
import nl.tudelft.ti2806.monkeyrush.failfast.FailIf;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Hashtable;
import java.util.Map;

/**
 * Handles incomming connection requests over TCP
 * in an asynchronous way.
 */
public class Server extends WebSocketServer {
    /**
     * Maps a remote address to a handler for player actions.
     */
    private final Map<InetSocketAddress, PlayerInteractionHandler> commandHandlerMap;

    /**
     * Handles all join and leave requests.
     */
    private final JoinHandler joinHandler;

    /**
     * Constructs the server, does NOT start it (see the {@link #start()} method).
     * @param port - The local port to bind the server to.
     * @param aJoinHandler - The handler for all join and leave requests.
     */
    public Server(final int port, final JoinHandler aJoinHandler) {
        super(new InetSocketAddress(port));
        this.commandHandlerMap = new Hashtable<>();
        this.joinHandler = aJoinHandler;
    }

    @Override
    public void onOpen(final WebSocket conn, final ClientHandshake handshake) {
        FailIf.isNull(conn);
        PlayerInteractionHandler playerHandler = joinHandler.joinPlayer(conn);
        commandHandlerMap.put(conn.getRemoteSocketAddress(), playerHandler);
    }

    @Override
    public void onClose(final WebSocket conn, final int code, final String reason, final boolean remote) {
        FailIf.isNull(conn);
        this.commandHandlerMap.get(conn.getRemoteSocketAddress()).leave();
    }

    @Override
    public void onMessage(final WebSocket conn, final String message) {
        FailIf.isNull(conn, message);
    }

    @Override
    public void onError(final WebSocket conn, final Exception ex) {
        FailIf.isNull(conn, ex);
    }

}
