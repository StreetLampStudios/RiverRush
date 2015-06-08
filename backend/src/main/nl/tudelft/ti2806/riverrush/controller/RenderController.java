package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.domain.event.AnimalAddedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalFellOffEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalJumpedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalReturnedToBoatEvent;
import nl.tudelft.ti2806.riverrush.domain.event.BoatCollidedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameAboutToStartEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameFinishedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameStartedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameStoppedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameWaitingEvent;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.AbstractServer;

import com.google.inject.Inject;
import com.google.inject.name.Named;

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
     * @param aServer The server for sending the events over the network
     * @param aGame The game instance
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
        HandlerLambda<Event> onGameStateChangedLambda = (e) -> this.server.sendEvent(e, this);
        HandlerLambda<BoatCollidedEvent> boatRektHandler = this::onBoatCollided;

        this.listenTo(GameWaitingEvent.class, onGameStateChangedLambda);
        this.listenTo(GameAboutToStartEvent.class, onGameStateChangedLambda);
        this.listenTo(GameStartedEvent.class, onGameStateChangedLambda);
        this.listenTo(GameStoppedEvent.class, onGameStateChangedLambda);
        this.listenTo(GameFinishedEvent.class, onGameStateChangedLambda);
        this.listenTo(AnimalAddedEvent.class, onGameStateChangedLambda);
        this.listenTo(AnimalJumpedEvent.class, onGameStateChangedLambda);
        this.listenTo(AnimalFellOffEvent.class, onGameStateChangedLambda);
        this.listenTo(AnimalReturnedToBoatEvent.class, onGameStateChangedLambda);
        this.listenTo(BoatCollidedEvent.class, boatRektHandler);

        this.game.waitForPlayers();
    }

    private void onBoatCollided(final BoatCollidedEvent event) {
        this.game.swooshThaFuckahsFromBoatThatMovedToTheWrongDirection(event.getDirection(),
                event.getTeam());
    }

    @Override
    public void onSocketMessage(final Event event) {
        this.dispatcher.dispatch(event);
    }
}
