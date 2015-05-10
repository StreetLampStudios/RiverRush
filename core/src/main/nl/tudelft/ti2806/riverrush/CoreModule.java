package nl.tudelft.ti2806.riverrush;

import com.google.inject.AbstractModule;
import nl.tudelft.ti2806.riverrush.domain.event.BasicEventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.network.event.JoinEvent;
import nl.tudelft.ti2806.riverrush.network.protocol.BasicProtocol;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

/**
 * Configures dependency injection.
 */
public class CoreModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Protocol.class).toInstance(configureProtocol());
        bind(EventDispatcher.class).toProvider(BasicEventDispatcher::new);
    }

    private Protocol configureProtocol() {
        Protocol protocol = new BasicProtocol();
        // Register available network actions
        // protocol.registerNetworkAction(...);
        protocol.registerNetworkAction(JoinEvent.class, JoinEvent::new);

        return protocol;
    }
}