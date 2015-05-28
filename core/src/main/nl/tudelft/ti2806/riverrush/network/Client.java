package nl.tudelft.ti2806.riverrush.network;

import nl.tudelft.ti2806.riverrush.controller.Controller;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Web socket client for connecting to the backend endpoint.
 */
public class Client extends WebSocketClient {

    /**
     * The protocol of the websocket client.
     */
    private final Protocol protocol;

    /**
     * The controller of the websocket client.
     */
    private final Controller controller;

    /**
     * Constructs a WebSocketClient instance and sets it to the connect to the
     * specified URI. The channel does not attampt to connect automatically. You
     * must call {@code connect} first to initiate the socket connection.
     *
     * @param host - The remote hostname of the server.
     * @param prot - what protocol to use
     * @param ctrl - the controller to use.
     * @throws URISyntaxException URI is invalid
     */
    public Client(
        final String host,
        final Protocol prot,
        final Controller ctrl
    ) throws URISyntaxException {
        super(new URI("http://" + host + ":" + prot.getPortNumber()), new Draft_17());
        this.controller = ctrl;
        this.protocol = prot;
    }

    @Override
    public void onOpen(final ServerHandshake handshakedata) {
        // Nothing supposed to happen
    }

    /**
     * Send a domain event to the server.
     *
     * @param event - The event to send.
     */
    private void sendEvent(final Event event) {
        this.getConnection().send(protocol.serialize(event));
    }

    @Override
    public void onMessage(final String message) {
        Event event = this.protocol.deserialize(message);
        this.controller.onSocketMessage(event);
    }

    @Override
    public void onClose(final int code, final String reason,
                        final boolean remote) {
        System.out.println("Connection closed.");
    }

    @Override
    public void onError(final Exception ex) {
        System.out.println("Connection failed");
        ex.printStackTrace();
    }

}
