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
 * The endpoint for the clients to connect.
 */
@Singleton
public class UserServer extends AbstractServer {

    private static final Logger log = LogManager.getLogger(UserServer.class);
    public static final int MAX_CLIENTS = 100;

    /**
     * Create the user server.
     *
     * @param aProtocol          The protocol for the clients
     * @param controllerProvider The controller provider for creating the controllers
     */
    @Inject
    public UserServer(@Named("clientProtocol") final Protocol aProtocol,
                      @Named("clientController") final Provider<Controller> controllerProvider) {
        super(aProtocol, controllerProvider);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        FailIf.isNull(conn);
        log.info("Connection opened");

        if (this.getSockets().size() < MAX_CLIENTS) {
            this.createController(conn);
        } else {
            conn.close(CloseFrame.REFUSE, "Maximum number of clients reached");
            log.info("Connection rejected");
        }
    }
}
