package nl.tudelft.ti2806.riverrush.network;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import nl.tudelft.ti2806.riverrush.controller.Controller;
import nl.tudelft.ti2806.riverrush.failfast.FailIf;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ClientHandshake;

/**
 * Server that connect to the renderer.
 */
@Singleton
public class RenderServer extends AbstractServer {

    private static final Logger log = LogManager.getLogger(UserServer.class);
    public static final int MAX_RENDERERS = 1;

    /**
     * Constructs the server that communicates with rendering clients.
     * Does NOT start it (see the {@link #start()} method).
     *
     * @param aProtocol - The protocol to use when receiving and sending messages.
     * @param aProvider - A Provider
     */
    @Inject
    public RenderServer(@Named("renderProtocol") final Protocol aProtocol,
                        @Named("renderController") final Provider<Controller> aProvider) {
        super(aProtocol, aProvider);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        FailIf.isNull(conn);
        log.info("Connection opened");

        if (this.getSockets().size() <= MAX_RENDERERS) {
            this.createController(conn);
        } else {
            conn.close(CloseFrame.REFUSE, "Maximum number of renderers reached");
            log.info("Connection rejected");
        }
    }
}
