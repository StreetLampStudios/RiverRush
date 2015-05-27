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
 * Created by thomas on 21-5-15.
 */
@Singleton
public class UserServer extends AbstractServer {

    @Inject
    public UserServer(@Named("clientProtocol") final Protocol aProtocol,
                      @Named("clientController") final Provider<Controller> controllerProvider) {
        super(aProtocol, controllerProvider);
    }

    @Override
    protected void filterJoinEvents(WebSocket connection, Event event) {
        if (event instanceof JoinEvent) {
            createController(connection);
        } else {
            dispatchToController(event, connection);
        }
    }
}
