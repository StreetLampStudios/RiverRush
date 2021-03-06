package nl.tudelft.ti2806.riverrush.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import nl.tudelft.ti2806.riverrush.domain.event.*;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.AbstractServer;

/**
 * Controller for the individual renderers.
 */
public class RenderController extends AbstractController {
    private final AbstractServer server;
    private final Game game;

    /**
     * Create a player controller.
     *
     * @param eventDispatcher The event dispatcher for dispatching the events
     * @param aServer         The server for sending the events over the network
     * @param aGame           The game instance
     */
    @Inject
    public RenderController(final EventDispatcher eventDispatcher,
                            @Named("renderServer") final AbstractServer aServer, final Game aGame) {
        super(eventDispatcher);
        this.server = aServer;
        this.game = aGame;
    }

    @Override
    public void initialize() {
        HandlerLambda<BoatCollidedEvent> boatRektHandler = this::onBoatCollided;
        HandlerLambda<Event> sendOverNetworkLambda = (e) -> this.server.sendEvent(e, this);

        this.listenTo(AddObstacleEvent.class, sendOverNetworkLambda);
        this.listenTo(AddRockEvent.class, sendOverNetworkLambda);
        this.listenTo(AnimalAddedEvent.class, sendOverNetworkLambda);
        this.listenTo(AnimalDroppedEvent.class, sendOverNetworkLambda);
        this.listenTo(AnimalFellOffEvent.class, sendOverNetworkLambda);
        this.listenTo(AnimalJumpedEvent.class, sendOverNetworkLambda);
        this.listenTo(AnimalMovedEvent.class, sendOverNetworkLambda);
        this.listenTo(AnimalRemovedEvent.class, sendOverNetworkLambda);
        this.listenTo(AnimalReturnedToBoatEvent.class, sendOverNetworkLambda);
        this.listenTo(GameAboutToStartEvent.class, sendOverNetworkLambda);
        this.listenTo(GameFinishedEvent.class, sendOverNetworkLambda);
        this.listenTo(GameStartedEvent.class, sendOverNetworkLambda);
        this.listenTo(GameStoppedEvent.class, sendOverNetworkLambda);
        this.listenTo(GameWaitingEvent.class, sendOverNetworkLambda);
        this.listenTo(TeamProgressEvent.class, sendOverNetworkLambda);
        this.listenTo(BoatCollidedEvent.class, boatRektHandler);
        this.listenTo(GameAboutToWaitEvent.class, sendOverNetworkLambda);

        this.game.waitForPlayers();
    }

    /**
     * Called when a boat collides.
     *
     * @param event The collision event
     */
    private void onBoatCollided(final BoatCollidedEvent event) {
        Direction direction = event.getDirection();
        Integer teamId = event.getTeam();

        this.game.sweepAnimals(direction, teamId);
    }

    @Override
    public void onSocketMessage(final Event event) {
        this.getDispatcher().dispatch(event);
    }
}
