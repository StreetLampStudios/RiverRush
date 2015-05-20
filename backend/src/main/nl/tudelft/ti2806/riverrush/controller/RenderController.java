package nl.tudelft.ti2806.riverrush.controller;

import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.domain.event.*;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.Server;

public class RenderController implements Controller {
    private final Server server;
    private final EventDispatcher dispatcher;
    private final Game game;
    private final HandlerLambda<PlayerAddedEvent> onPlayerJoin;
    private final HandlerLambda<Event> onGameStateChangedLambda;

    @Inject
    public RenderController(final EventDispatcher eventDispatcher, final Server server, final Game aGame) {
        this.dispatcher = eventDispatcher;
        this.server = server;
        this.game = aGame;

        this.onPlayerJoin = (e) -> this.server.sendEvent(e, this);
        this.onGameStateChangedLambda = (e) -> this.server.sendEvent(e, this);

        this.dispatcher.attach(PlayerAddedEvent.class, onPlayerJoin);
        this.dispatcher.attach(GameAboutToStartEvent.class, onGameStateChangedLambda);
        this.dispatcher.attach(GameStartedEvent.class, onGameStateChangedLambda);
    }

    @Override
    public void initialize() {
        this.game.waitForPlayers();
        this.onGameStateChangedLambda.handle(new GameWaitingEvent());
    }

    @Override
    public void onSocketMessage(final Event event) {
        this.dispatcher.dispatch(event);
    }

    @Override
    public void detach() {
        this.dispatcher.detach(PlayerAddedEvent.class, this.onPlayerJoin);
        this.dispatcher.detach(GameAboutToStartEvent.class, this.onGameStateChangedLambda);
        this.dispatcher.detach(GameStartedEvent.class, this.onGameStateChangedLambda);
        this.game.stop();
    }


}
