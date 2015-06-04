package nl.tudelft.ti2806.riverrush.network;

import com.google.inject.Inject;
import com.google.inject.Provider;
import nl.tudelft.ti2806.riverrush.CoreModule;
import nl.tudelft.ti2806.riverrush.controller.Controller;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.failfast.FailIf;
import nl.tudelft.ti2806.riverrush.network.protocol.InvalidActionException;
import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Web socket endpoint for the backend to dispatch incoming tcp request from the
 * client.
 */
public abstract class AbstractServer extends WebSocketServer {

    private static final Logger log = LogManager.getLogger(AbstractServer.class);
    /**
     * Maps a remote address to a handler for player actions.
     */
    private final Map<WebSocket, Controller> controllers;
    /**
     * Maps a websocket to a controller.
     */
    private final Map<Controller, WebSocket> sockets;

    /**
     * The protocol used to serialize/deserialize network messages.
     */
    private final Protocol protocol;

    /**
     * The factory is used to create controllers.
     */
    private final Provider<Controller> controllerProvider;


    /**
     * Constructs the server, does NOT start it (see the {@link #start()}
     * method).
     *
     * @param aProtocol The protocol to use when receiving and sending messages.
     * @param aProvider An injected provider used to create {@link Controller} objects.
     */
    @Inject
    public AbstractServer(final Protocol aProtocol,
                          final Provider<Controller> aProvider) {
        super(new InetSocketAddress(aProtocol.getPortNumber()));
        this.controllers = new Hashtable<>();
        this.sockets = new Hashtable<>();
        this.protocol = aProtocol;
        this.controllerProvider = aProvider;

        try {
            this.sendHTTPRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(final WebSocket conn, final ClientHandshake handshake) {
        FailIf.isNull(conn);
        log.info("Connection opened");
        createController(conn);
    }

    @Override
    public void onClose(final WebSocket conn, final int code,
                        final String reason, final boolean remote) {
        FailIf.isNull(conn);
        log.info("Connection closed.");
        this.controllers.get(conn).dispose();
        this.controllers.remove(conn);
    }

    @Override
    public void onMessage(final WebSocket conn, final String message) {
        FailIf.isNull(conn, message);
        try {
            final Event event = this.protocol.deserialize(message);
            dispatchToController(event, conn);
        } catch (InvalidProtocolException | InvalidActionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a controller for a websocket.
     *
     * @param conn - The websocket to create a controller for
     */
    protected void createController(final WebSocket conn) {
        log.info("Creating controller via " + this.controllerProvider.getClass());
        Controller controller = this.controllerProvider.get();
        controllers.put(conn, controller);
        sockets.put(controller, conn);
        controller.initialize();
    }

    /**
     * When an event is received, it will be dispatched to the correct controller.
     *
     * @param event      The event.
     * @param connection The connection on which this event was received.
     */
    protected void dispatchToController(final Event event, final WebSocket connection) {
        Controller controller = controllers.get(connection);
        controller.onSocketMessage(event);
    }

    @Override
    public void onError(final WebSocket conn, final Exception ex) {
        FailIf.isNull(ex);
        log.error("Error in socket layer: ");
        log.error(ex);
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
        log.info("Sending event over socket: " + event.getClass());
        sock.send(serialize);
    }

    /**
     * Sends a http request to register the backend server's IP and port.
     * So that clients can request the connection details.
     *
     * @throws IOException when something goes horribly wrong.
     */
    private void sendHTTPRequest() throws IOException {
        URL url = null;
        try {
            url = new URL("http://riverrush.3dsplaza.com/setserver.php");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("password", "pizza");
        params.put("port", CoreModule.CLIENT_PORT_NUMBER);

        StringBuilder postData = new StringBuilder();
        String enc = "UTF-8";
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), enc));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), enc));
        }
        byte[] postDataBytes = postData.toString().getBytes(enc);

        assert url != null;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            connection.setDoOutput(true);
            connection.getOutputStream().write(postDataBytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.getOutputStream().close();
        }
    }
}
