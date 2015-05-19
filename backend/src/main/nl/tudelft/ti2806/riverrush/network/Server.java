package nl.tudelft.ti2806.riverrush.network;

import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.backend.Controller;
import nl.tudelft.ti2806.riverrush.backend.PlayerController;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.failfast.FailIf;
import nl.tudelft.ti2806.riverrush.network.event.JoinEvent;
import nl.tudelft.ti2806.riverrush.network.event.RenderJoinEvent;
import nl.tudelft.ti2806.riverrush.network.protocol.InvalidActionException;
import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import javax.inject.Provider;
import java.net.InetSocketAddress;
import java.util.Hashtable;
import java.util.Map;

/**
 * Web socket endpoint for the backend to dispatch incoming tcp request from the
 * client.
 */
public class Server extends WebSocketServer {

    /**
     * Maps a remote address to a handler for player actions.
     */
    private final Map<WebSocket, Controller> controllers;
    private final Map<Controller, WebSocket> sockets;

    /**
     * Provides instances of EventDispatcher when a client joins.
     */
    private final EventDispatcher eventDispatcher;

    /**
     * The protocol used to serialize/deserialize network messages.
     */
    private final Protocol protocol;


    /**
     * Constructs the server, does NOT start it (see the {@link #start()}
     * method).
     *
     * @param dispatcher - A {@link Provider} for {@link EventDispatcher}s.
     * @param aProtocol  - The protocol to use when receiving and sending messages.
     */
    @Inject
    public Server(final EventDispatcher dispatcher,
                  final Protocol aProtocol) {
        super(new InetSocketAddress(aProtocol.getPortNumber()));
        this.controllers = new Hashtable<>();
        this.sockets = new Hashtable<>();
        this.protocol = aProtocol;
        this.eventDispatcher = dispatcher;
    }

    @Override
    public void onOpen(final WebSocket conn, final ClientHandshake handshake) {
        FailIf.isNull(conn);
    }

    @Override
    public void onClose(final WebSocket conn, final int code,
                        final String reason, final boolean remote) {
        FailIf.isNull(conn);
        this.controllers.get(conn).detatch();
        this.controllers.remove(conn);
    }

    @Override
    public void onMessage(final WebSocket conn, final String message) {
        FailIf.isNull(conn, message);
        try {
            dispatchNetworkEvent(conn, message);
        } catch (InvalidProtocolException | InvalidActionException e) {
            e.printStackTrace();
        }
    }

    private void dispatchNetworkEvent(final WebSocket conn, final String message) {
        final Event event = this.protocol.deserialize(message);

        final Controller controller;
        if (event instanceof JoinEvent) {
            controller = new PlayerController(eventDispatcher, this);
            controllers.put(conn, controller);
        } else if (event instanceof RenderJoinEvent) {
            controller = new RenderController(eventDispatcher, this);
            controllers.put(conn, controller);
        } else {
            controller = controllers.get(conn);
        }

        controller.onSocketMessage(event);
    }

    @Override
    public void onError(final WebSocket conn, final Exception ex) {
        FailIf.isNull(conn, ex);
    }

    /**
     * Handles events to send over the network.
     *
     * @param event      - The event to dispatch.
     * @param controller - The dispatcher responsible for the event.
     */
    public void sendEvent(final Event event, final Controller controller) {
        WebSocket sock = sockets.get(controller);
        sock.send(event.serialize(protocol));
    }

}
