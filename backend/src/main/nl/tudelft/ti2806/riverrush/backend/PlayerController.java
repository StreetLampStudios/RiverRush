package nl.tudelft.ti2806.riverrush.backend;

import nl.tudelft.ti2806.riverrush.controller.Controller;
import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.domain.event.*;
import nl.tudelft.ti2806.riverrush.network.Server;
import nl.tudelft.ti2806.riverrush.network.event.JumpEvent;

public class PlayerController implements Controller {

    private final Player player;
    private final EventDispatcher dispatcher;
    private final Server server;
    private final HandlerLambda onGameStateChangeLambda = this::onGameStateChange;
    private final HandlerLambda onJumpLambda = this::onJump;


    public PlayerController(final EventDispatcher dispatcher, final Server server) {
        this.player = new Player();
        this.dispatcher = dispatcher;
        this.server = server;

        dispatcher.attach(JumpEvent.class, onJumpLambda);
        dispatcher.attach(GameAboutToStartEvent.class, onGameStateChangeLambda);
        dispatcher.attach(GameStartedEvent.class, onGameStateChangeLambda);
        dispatcher.attach(GameStoppedEvent.class, onGameStateChangeLambda);
        dispatcher.attach(GameFinishedEvent.class, onGameStateChangeLambda);
        dispatcher.attach(GameWaitingEvent.class, onGameStateChangeLambda);
    }

    @Override
    public void onSocketMessage(final Event event) {
        event.setPlayer(this.player);
        this.dispatcher.dispatch(event);
    }

    @Override
    public void detach() {
        this.dispatcher.detach(JumpEvent.class, onJumpLambda);
        this.dispatcher.detach(GameWaitingEvent.class, onGameStateChangeLambda);
        this.dispatcher.detach(GameStartedEvent.class, onGameStateChangeLambda);
        this.dispatcher.detach(GameAboutToStartEvent.class, onGameStateChangeLambda);
        this.dispatcher.detach(GameStoppedEvent.class, onGameStateChangeLambda);
        this.dispatcher.detach(GameFinishedEvent.class, onGameStateChangeLambda);
    }

    private void onGameStateChange(final Event event) {
        server.sendEvent(event, this);
    }

    private void onJump(final Event event) {
        JumpEvent jumpEvent = (JumpEvent) event;
        server.sendEvent(jumpEvent, this);
    }

}
