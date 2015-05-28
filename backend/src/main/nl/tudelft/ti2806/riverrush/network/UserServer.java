package nl.tudelft.ti2806.riverrush.network;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import nl.tudelft.ti2806.riverrush.controller.Controller;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.network.event.JoinEvent;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;
import org.java_websocket.WebSocket;

/**
 * The endpoint for the clients to connect.
 */
@Singleton
public class UserServer extends AbstractServer {

    /**
     * Create the user server.
     *
     * @param aProtocol The protocol for the clients
     * @param controllerProvider The controller provider for creating the controllers
     */
    @Inject
    public UserServer(@Named("clientProtocol") final Protocol aProtocol,
                      @Named("clientController") final Provider<Controller> controllerProvider) {
        super(aProtocol, controllerProvider);
    }

    @Override
    protected void filterJoinEvents(final WebSocket connection, final Event event) {
        if (event instanceof JoinEvent) {
            createController(connection);
        } else {
            dispatchToController(event, connection);
        }
    }
}
