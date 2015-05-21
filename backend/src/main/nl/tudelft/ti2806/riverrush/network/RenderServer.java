package nl.tudelft.ti2806.riverrush.network;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import nl.tudelft.ti2806.riverrush.controller.Controller;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.network.event.RenderJoinEvent;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;
import org.java_websocket.WebSocket;

/**
 * Created by thomas on 21-5-15.
 */
@Singleton
public class RenderServer extends Server {

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
    protected void filterJoinEvents(WebSocket connection, Event event) {
        if (event instanceof RenderJoinEvent) {
            createController(connection);
        } else {
            dispatchToController(event, connection);
        }
    }
}
