package nl.tudelft.ti2806.riverrush.controller;

import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.domain.event.*;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.RenderServer;
import nl.tudelft.ti2806.riverrush.network.Server;

public class RenderController implements Controller {
    private final Server server;
    private final EventDispatcher dispatcher;
    private final Game game;
    private final HandlerLambda<Event> onGameStateChangedLambda;
    private final HandlerLambda<PlayerJumpedEvent> onJump;

    @Inject
    public RenderController(final EventDispatcher eventDispatcher, final RenderServer server, final Game aGame) {
        this.dispatcher = eventDispatcher;
        this.server = server;
        this.game = aGame;

        this.onGameStateChangedLambda = (e) -> this.server.sendEvent(e, this);
        this.onJump = (e) -> this.server.sendEvent(e, this);

        this.dispatcher.attach(PlayerAddedEvent.class, onGameStateChangedLambda);
        this.dispatcher.attach(GameWaitingEvent.class, onGameStateChangedLambda);
        this.dispatcher.attach(GameAboutToStartEvent.class, onGameStateChangedLambda);
        this.dispatcher.attach(GameStartedEvent.class, onGameStateChangedLambda);
        this.dispatcher.attach(GameFinishedEvent.class, onGameStateChangedLambda);
        this.dispatcher.attach(GameStoppedEvent.class, onGameStateChangedLambda);
        this.dispatcher.attach(PlayerJumpedEvent.class, onJump);

    }

    @Override
    public void initialize() {
        this.game.waitForPlayers();
    }

    @Override
    public void onSocketMessage(final Event event) {
        this.dispatcher.dispatch(event);
    }

    @Override
    public void detach() {
        this.dispatcher.detach(PlayerAddedEvent.class, this.onGameStateChangedLambda);
        this.dispatcher.detach(GameAboutToStartEvent.class, this.onGameStateChangedLambda);
        this.dispatcher.detach(GameStartedEvent.class, this.onGameStateChangedLambda);
        this.dispatcher.detach(GameWaitingEvent.class, this.onGameStateChangedLambda);
        this.dispatcher.detach(GameFinishedEvent.class, this.onGameStateChangedLambda);
        this.dispatcher.detach(GameStoppedEvent.class, this.onGameStateChangedLambda);
        this.dispatcher.detach(PlayerJumpedEvent.class, this.onJump);
        this.game.stop();
    }


}
