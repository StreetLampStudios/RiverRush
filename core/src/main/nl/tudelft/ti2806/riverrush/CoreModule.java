package nl.tudelft.ti2806.riverrush;

import com.google.inject.AbstractModule;

import nl.tudelft.ti2806.riverrush.domain.event.BasicEventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.network.event.JoinEvent;
import nl.tudelft.ti2806.riverrush.network.event.NetworkEvent;
import nl.tudelft.ti2806.riverrush.network.protocol.BasicProtocol;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol.EventInstantiator;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;

/**
 * Configures dependency injection.
 */
public abstract class CoreModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(Protocol.class).toInstance(this.configureProtocol());
        this.bind(EventDispatcher.class).toProvider(
                new Provider<EventDispatcher>() {

                    @Override
                    public EventDispatcher get() {
                        return new BasicEventDispatcher();
                    }
                });
    }

    /**
     * Configure the protocol by registering all valid messages that can be
     * sent.
     *
     * @return The fully configured protocol.
     */
    protected Protocol configureProtocol() {
        Protocol protocol = BasicProtocol.getInstance();
        // Register available network actions
        // protocol.registerNetworkAction(...);
        protocol.registerNetworkAction(JoinEvent.class,
                new EventInstantiator() {

                    @Override
                    public NetworkEvent instantiate() {
                        return new JoinEvent();
                    }
                });

        return protocol;
    }

    /**
     * Creates instances of EventDispatcher
     * Override for the ability to pre-register any listeners.
     * @return A fresh dispatcher.
     */
    protected abstract EventDispatcher configureEventDispatcher();
}
