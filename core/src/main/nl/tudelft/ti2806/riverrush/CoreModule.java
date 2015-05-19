package nl.tudelft.ti2806.riverrush;

import com.google.inject.AbstractModule;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.network.event.JoinEvent;
import nl.tudelft.ti2806.riverrush.network.protocol.BasicProtocol;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

/**
 * Configures dependency injection.
 */

public abstract class CoreModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(EventDispatcher.class).toInstance(this.configureEventDispatcher());
    }

    /**
     * Creates instances of EventDispatcher
     * Override for the ability to pre-attatch any listeners.
     *
     * @return A fresh dispatcher.
     */
    protected abstract EventDispatcher configureEventDispatcher();

    /**
     * Configure the renderer protocol by registering all valid messages that can be
     * sent.
     *
     * @return The fully configured protocol.
     */
    protected Protocol configureRendererProtocol() {
        Protocol protocol = new BasicProtocol(81);
        // Register available network actions
        // protocol.registerNetworkAction(...);

        return protocol;
    }

    /**
     * Configure the client protocol by registering all valid messages that can be
     * sent.
     *
     * @return The fully configured protocol.
     */
    protected Protocol configureClientProtocol() {
        Protocol protocol = new BasicProtocol(82);
        // Register available network actions
        // protocol.registerNetworkAction(...);

        protocol.registerNetworkAction(JoinEvent.class, JoinEvent::new);

        return protocol;
    }

}
