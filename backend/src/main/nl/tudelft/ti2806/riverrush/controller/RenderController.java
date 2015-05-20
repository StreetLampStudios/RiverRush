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

    @Inject
    public RenderController(final EventDispatcher eventDispatcher, final Server server, final Game aGame) {
        this.dispatcher = eventDispatcher;
        this.server = server;
        this.game = aGame;

        this.onPlayerJoin = (e) -> this.server.sendEvent(e, this);
        this.dispatcher.attach(PlayerAddedEvent.class, onPlayerJoin);
    }

    @Override
    public void initialize() {
        this.game.waitForPlayers();
        this.onGameStateChanged(new GameWaitingEvent());
    }

    private void onGameStateChanged(final Event event) {
        this.server.sendEvent(event, this);
    }

    @Override
    public void onSocketMessage(final Event event) {
        this.dispatcher.dispatch(event);
    }

    @Override
    public void detach() {
        this.dispatcher.detach(PlayerAddedEvent.class, this.onPlayerJoin);
        this.game.stop();
    }


}
