package nl.tudelft.ti2806.riverrush;

import com.google.inject.AbstractModule;
import nl.tudelft.ti2806.riverrush.domain.event.AddObstacleEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AddRockEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalAddedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalCollidedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalDroppedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalFellOffEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalJumpedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalMovedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalRemovedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalReturnedToBoatEvent;
import nl.tudelft.ti2806.riverrush.domain.event.BasicEventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.BoatCollidedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameAboutToStartEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameFinishedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameStartedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameStoppedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameWaitingEvent;
import nl.tudelft.ti2806.riverrush.domain.event.TeamProgressEvent;
import nl.tudelft.ti2806.riverrush.network.event.JoinTeamCommand;
import nl.tudelft.ti2806.riverrush.network.event.JumpCommand;
import nl.tudelft.ti2806.riverrush.network.event.VoteBoatMoveCommand;
import nl.tudelft.ti2806.riverrush.network.protocol.BasicProtocol;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

/**
 * Configures dependency injection.
 */

public abstract class CoreModule extends AbstractModule {

    /**
     * Specifies the render port.
     */
    public static final int RENDER_PORT_NUMBER = 51337;
    /**
     * Specifies the client port.
     */
    public static final int CLIENT_PORT_NUMBER = 41337;

    @Override
    protected void configure() {
        this.bind(EventDispatcher.class).toInstance(this.configureEventDispatcher());
    }

    /**
     * Creates instances of EventDispatcher Override for the ability to pre-attach any listeners.
     *
     * @return A fresh dispatcher.
     */
    protected EventDispatcher configureEventDispatcher() {
        return new BasicEventDispatcher();
    }

    /**
     * Configure the renderer protocol by registering all valid messages that can be sent.
     *
     * @return The fully configured protocol.
     */
    protected Protocol configureRendererProtocol() {
        Protocol protocol = new BasicProtocol(RENDER_PORT_NUMBER);
        // Register available network actions
        // protocol.registerNetworkMessage(...);

        this.registerStateMessages(protocol);

        return protocol;
    }

    /**
     * Register all the events allowed for this protocol.
     *
     * @param protocol The protocol for this server
     */
    private void registerStateMessages(final Protocol protocol) {
        protocol.registerNetworkMessage(AddObstacleEvent.class, AddObstacleEvent::new);
        protocol.registerNetworkMessage(AddRockEvent.class, AddRockEvent::new);
        protocol.registerNetworkMessage(AnimalAddedEvent.class, AnimalAddedEvent::new);
        protocol.registerNetworkMessage(AnimalCollidedEvent.class, AnimalCollidedEvent::new);
        protocol.registerNetworkMessage(AnimalDroppedEvent.class, AnimalDroppedEvent::new);
        protocol.registerNetworkMessage(AnimalFellOffEvent.class, AnimalFellOffEvent::new);
        protocol.registerNetworkMessage(AnimalJumpedEvent.class, AnimalJumpedEvent::new);
        protocol.registerNetworkMessage(AnimalMovedEvent.class, AnimalMovedEvent::new);
        protocol.registerNetworkMessage(AnimalRemovedEvent.class, AnimalRemovedEvent::new);
        protocol.registerNetworkMessage(AnimalReturnedToBoatEvent.class, AnimalReturnedToBoatEvent::new);
        protocol.registerNetworkMessage(BoatCollidedEvent.class, BoatCollidedEvent::new);
        protocol.registerNetworkMessage(GameAboutToStartEvent.class, GameAboutToStartEvent::new);
        protocol.registerNetworkMessage(GameFinishedEvent.class, GameFinishedEvent::new);
        protocol.registerNetworkMessage(GameStartedEvent.class, GameStartedEvent::new);
        protocol.registerNetworkMessage(GameStoppedEvent.class, GameStoppedEvent::new);
        protocol.registerNetworkMessage(GameWaitingEvent.class, GameWaitingEvent::new);
        protocol.registerNetworkMessage(TeamProgressEvent.class, TeamProgressEvent::new);
    }

    /**
     * Configure the client protocol by registering all valid messages that can be sent.
     *
     * @return The fully configured protocol.
     */
    protected Protocol configureClientProtocol() {
        Protocol protocol = new BasicProtocol(CLIENT_PORT_NUMBER);
        // Register available network actions
        // protocol.registerNetworkMessage(...);

        protocol.registerNetworkMessage(JumpCommand.class, JumpCommand::new);
        protocol.registerNetworkMessage(JoinTeamCommand.class, JoinTeamCommand::new);
        protocol.registerNetworkMessage(VoteBoatMoveCommand.class, VoteBoatMoveCommand::new);
        this.registerStateMessages(protocol);

        this.registerStateMessages(protocol);

        return protocol;
    }

}
