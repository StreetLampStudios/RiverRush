package nl.tudelft.ti2806.riverrush.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.event.*;
import nl.tudelft.ti2806.riverrush.network.AbstractServer;

/**
 * Controller for the individual players.
 */
public class UserController extends AbstractController {

    private final AbstractAnimal animal;
    private final EventDispatcher dispatcher;
    private final AbstractServer server;

    /**
     * Create a player controller.
     *
     * @param aDispatcher The event dispatcher for dispatching the events
     * @param aServer     The server for sending the events over the network
     */
    @Inject
    public UserController(final EventDispatcher aDispatcher,
                          @Named("playerServer") final AbstractServer aServer) {
        super(aDispatcher);
        this.animal = null; //FIXME: make an actual animal
        this.dispatcher = aDispatcher;
        this.server = aServer;
    }

    @Override
    public void initialize() {
        final HandlerLambda<Event> onGameStateChangedLambda = (e) -> this.server.sendEvent(e, this);

        this.listenTo(GameAboutToStartEvent.class, onGameStateChangedLambda);
        this.listenTo(GameStartedEvent.class, onGameStateChangedLambda);
        this.listenTo(GameStoppedEvent.class, onGameStateChangedLambda);
        this.listenTo(GameFinishedEvent.class, onGameStateChangedLambda);
        this.listenTo(GameWaitingEvent.class, onGameStateChangedLambda);
        this.listenTo(AnimalJumpedEvent.class, onGameStateChangedLambda);
        this.listenTo(AnimalFellOffEvent.class, onGameStateChangedLambda);

        AnimalAddedEvent event = new AnimalAddedEvent();
        event.setAnimal(this.animal);

        onGameStateChangedLambda.handle(event);

        this.dispatcher.dispatch(event);
    }

    @Override
    public void onSocketMessage(final Event event) {
        event.setAnimal(this.animal);
        this.dispatcher.dispatch(event);
    }
}
