package nl.tudelft.ti2806.riverrush;

import com.google.inject.AbstractModule;
import nl.tudelft.ti2806.riverrush.domain.event.*;
import nl.tudelft.ti2806.riverrush.network.event.JoinEvent;
import nl.tudelft.ti2806.riverrush.network.event.JumpEvent;
import nl.tudelft.ti2806.riverrush.network.event.RenderJoinEvent;
import nl.tudelft.ti2806.riverrush.network.protocol.BasicProtocol;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

/**
 * Configures dependency injection.
 */

public abstract class CoreModule extends AbstractModule {

    public static final int RENDER_PORT_NUMBER = 51337;
    public static final int CLIENT_PORT_NUMBER = 41337;

    @Override
    protected void configure() {
        this.bind(EventDispatcher.class).toInstance(this.configureEventDispatcher());
    }

    /**
     * Creates instances of EventDispatcher
     * Override for the ability to pre-attach any listeners.
     *
     * @return A fresh dispatcher.
     */
    protected EventDispatcher configureEventDispatcher() {
        return new BasicEventDispatcher();
    }

    /**
     * Configure the renderer protocol by registering all valid messages that can be
     * sent.
     *
     * @return The fully configured protocol.
     */
    protected Protocol configureRendererProtocol() {
        Protocol protocol = new BasicProtocol(RENDER_PORT_NUMBER);
        // Register available network actions
        // protocol.registerNetworkAction(...);

        protocol.registerNetworkAction(RenderJoinEvent.class, RenderJoinEvent::new);
        protocol.registerNetworkAction(GameWaitingEvent.class, GameWaitingEvent::new);
        protocol.registerNetworkAction(GameAboutToStartEvent.class, GameAboutToStartEvent::new);
        protocol.registerNetworkAction(GameStartedEvent.class, GameStartedEvent::new);
        protocol.registerNetworkAction(GameFinishedEvent.class, GameFinishedEvent::new);
        protocol.registerNetworkAction(GameStoppedEvent.class, GameStoppedEvent::new);
        protocol.registerNetworkAction(PlayerAddedEvent.class, PlayerAddedEvent::new);
        protocol.registerNetworkAction(PlayerJumpedEvent.class, PlayerJumpedEvent::new);

        return protocol;
    }

    /**
     * Configure the client protocol by registering all valid messages that can be
     * sent.
     *
     * @return The fully configured protocol.
     */
    protected Protocol configureClientProtocol() {
        Protocol protocol = new BasicProtocol(CLIENT_PORT_NUMBER);
        // Register available network actions
        // protocol.registerNetworkAction(...);

        protocol.registerNetworkAction(JoinEvent.class, JoinEvent::new);
        protocol.registerNetworkAction(JumpEvent.class, JumpEvent::new);

        return protocol;
    }

}
