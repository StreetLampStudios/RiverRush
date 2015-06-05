package nl.tudelft.ti2806.riverrush.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.event.*;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.AbstractServer;
import nl.tudelft.ti2806.riverrush.network.event.JoinTeamCommand;
import nl.tudelft.ti2806.riverrush.network.event.JumpCommand;

import java.util.Objects;

/**
 * Controller for the individual players.
 */
public class UserController extends AbstractController {

    private final AbstractAnimal animal;
    private final EventDispatcher dispatcher;
    private final AbstractServer server;
    private final Game game;

    /**
     * Create a player controller.
     *
     * @param aDispatcher The event dispatcher for dispatching the events
     * @param aServer     The server for sending the events over the network
     * @param aGame       The game instance
     */
    @Inject
    public UserController(
        final EventDispatcher aDispatcher,
        @Named("playerServer") final AbstractServer aServer,
        final Game aGame
    ) {
        super(aDispatcher);
        this.animal = new Animal(aDispatcher);
        this.dispatcher = aDispatcher;
        this.server = aServer;
        this.game = aGame;
    }

    @Override
    public void initialize() {
        final HandlerLambda<JoinTeamCommand> joinTeamHandler = this::joinTeamHandler;
        final HandlerLambda<Event> sendOverNetworkLambda = (e) -> {
            if (Objects.equals(e.getAnimal(), this.animal.getId())) {
                this.server.sendEvent(e, this);
            }
        };
        final HandlerLambda<JumpCommand> jumpCommandHandler = (e) -> {
            if (Objects.equals(this.animal.getId(), e.getAnimal())) {
                this.game.jumpAnimal(this.animal);
            }
        };

        this.listenTo(GameWaitingEvent.class, sendOverNetworkLambda);
        this.listenTo(GameAboutToStartEvent.class, sendOverNetworkLambda);
        this.listenTo(GameStartedEvent.class, sendOverNetworkLambda);
        this.listenTo(GameStoppedEvent.class, sendOverNetworkLambda);
        this.listenTo(GameFinishedEvent.class, sendOverNetworkLambda);
        this.listenTo(AnimalAddedEvent.class, sendOverNetworkLambda);
        this.listenTo(AnimalJumpedEvent.class, sendOverNetworkLambda);
        this.listenTo(AnimalFellOffEvent.class, sendOverNetworkLambda);
        this.listenTo(AnimalReturnedToBoatEvent.class, sendOverNetworkLambda);
        this.listenTo(AnimalDroppedEvent.class, sendOverNetworkLambda);
        this.listenTo(JoinTeamCommand.class, joinTeamHandler);
        this.listenTo(JumpCommand.class, jumpCommandHandler);
    }

    /**
     * Handler that a user joins a team.
     *
     * @param e The event
     */
    private void joinTeamHandler(final JoinTeamCommand e) {
        if (Objects.equals(e.getAnimal(), this.animal.getId())) {
            this.game.addPlayerToTeam(this.animal, e.getTeam());
        }
    }


    @Override
    public void onSocketMessage(final Event event) {
        event.setAnimal(this.animal.getId());
        this.dispatcher.dispatch(event);
    }

    @Override
    public void dispose() {
        super.dispose();
        AnimalRemovedEvent event = new AnimalRemovedEvent();
        event.setAnimal(this.animal.getId());
        event.setTeam(this.animal.getTeamId());
        this.dispatcher.dispatch(event);
    }
}
