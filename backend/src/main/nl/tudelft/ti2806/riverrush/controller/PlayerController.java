package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.domain.event.*;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.Server;
import nl.tudelft.ti2806.riverrush.network.event.JumpEvent;

public class PlayerController implements Controller {

    private final Player player;
    private final EventDispatcher dispatcher;
    private final Server server;
    private final HandlerLambda<Event> onGameStateChangeLambda = this::onGameStateChange;
    private final HandlerLambda<Event> onJumpLambda = this::onJump;
    private final Game game;


    public PlayerController(final EventDispatcher dispatcher, final Server server, final Game aGame) {
        this.player = new Player();
        this.dispatcher = dispatcher;
        this.server = server;
        this.game = aGame;

        this.dispatcher.attach(JumpEvent.class, onJumpLambda);
        this.dispatcher.attach(GameAboutToStartEvent.class, onGameStateChangeLambda);
        this.dispatcher.attach(GameStartedEvent.class, onGameStateChangeLambda);
        this.dispatcher.attach(GameStoppedEvent.class, onGameStateChangeLambda);
        this.dispatcher.attach(GameFinishedEvent.class, onGameStateChangeLambda);
        this.dispatcher.attach(GameWaitingEvent.class, onGameStateChangeLambda);

        PlayerAddedEvent event = new PlayerAddedEvent();
        event.setPlayer(player);
        this.dispatcher.dispatch(event);
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
