package nl.tudelft.ti2806.riverrush.network;

import java.net.URI;

import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.EventListener;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import com.google.inject.Inject;

/**
 * Web socket client for connecting to the backend endpoint.
 */
public class Client extends WebSocketClient implements EventListener {

    private Protocol prot;

    private EventDispatcher dispatch;

    /**
     * Constructs a WebSocketClient instance and sets it to the connect to the
     * specified URI. The channel does not attampt to connect automatically. You
     * must call {@code connect} first to initiate the socket connection.
     *
     * @param serverUri
     *            - The remote uri of the server.
     * @param draft
     *            - The websocket draft to use.
     */
    public Client(final URI serverUri, final Draft draft) {
        super(serverUri, draft);
    }

    @Override
    public void onOpen(final ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(final String message) {
        Event event = this.prot.deserialize(message);
        this.dispatch.dispatch(event);
    }

    @Override
    public void onClose(final int code, final String reason,
            final boolean remote) {

    }

    @Override
    public void onError(final Exception ex) {

    }

    @Override
    public void handle(Event event) {
        this.send(this.prot.serialize(event));
    }

    @Inject
    public void setProt(Protocol prot) {
        this.prot = prot;
    }

    public void setDispatch(EventDispatcher dispatch) {
        this.dispatch = dispatch;
    }
}
