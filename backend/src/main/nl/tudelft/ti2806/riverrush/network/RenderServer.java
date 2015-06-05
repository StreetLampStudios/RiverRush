package nl.tudelft.ti2806.riverrush.network;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import nl.tudelft.ti2806.riverrush.controller.Controller;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

/**
 * Server that connect to the renderer.
 */
@Singleton
public class RenderServer extends AbstractServer {

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
}
