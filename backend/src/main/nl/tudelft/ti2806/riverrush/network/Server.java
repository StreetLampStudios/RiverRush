package nl.tudelft.ti2806.riverrush.network;

import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.controller.Controller;
import nl.tudelft.ti2806.riverrush.controller.ControllerFactory;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.failfast.FailIf;
import nl.tudelft.ti2806.riverrush.network.event.JoinEvent;
import nl.tudelft.ti2806.riverrush.network.event.RenderJoinEvent;
import nl.tudelft.ti2806.riverrush.network.protocol.InvalidActionException;
import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.LinkedHashMap;
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
     * The protocol used to serialize/deserialize network messages.
     */
    private final Protocol protocol;

    /**
     * The factory is used to create controllers.
     */
    private final ControllerFactory factory;


    /**
     * Constructs the server, does NOT start it (see the {@link #start()}
     * method).
     *
     * @param aProtocol - The protocol to use when receiving and sending messages.
     */
    @Inject
    public Server(final Protocol aProtocol, final ControllerFactory controllerFactory) {
        super(new InetSocketAddress(aProtocol.getPortNumber()));
        this.controllers = new Hashtable<>();
        this.sockets = new Hashtable<>();
        this.protocol = aProtocol;
        this.factory = controllerFactory;

        try {
            this.sendHTTPRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(final WebSocket conn, final ClientHandshake handshake) {
        FailIf.isNull(conn);
    }

    @Override
    public void onClose(final WebSocket conn, final int code,
                        final String reason, final boolean remote) {
        FailIf.isNull(conn);
        this.controllers.get(conn).detach();
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
            controller = this.factory.getController(this, "player");
            controllers.put(conn, controller);
            sockets.put(controller, conn);
            controller.initialize();
        } else if (event instanceof RenderJoinEvent) {
            controller = this.factory.getController(this, "renderer");
            controllers.put(conn, controller);
            sockets.put(controller, conn);
            controller.initialize();
        } else {
            controller = controllers.get(conn);
            controller.onSocketMessage(event);
        }

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
        String serialize = protocol.serialize(event);
        sock.send(serialize);
    }

    private void sendHTTPRequest() throws IOException {
        URL url = new URL("http://riverrush.3dsplaza.com/setserver.php");
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("password", "pizza");
        params.put("port", "82");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        StringBuilder sb = new StringBuilder();
        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        for (int c = in.read(); c != -1; c = in.read())
            sb.append((char) c);
        if (!sb.toString().equals("0")) {
            // Warning: Call to setserver.php on the server to set the server's IP address and port failed
            // Users might not be able to connect to the server now
        }
    }

}
