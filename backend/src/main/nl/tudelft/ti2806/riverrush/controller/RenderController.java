package nl.tudelft.ti2806.riverrush.controller;

import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameWaitingEvent;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.Server;

public class RenderController implements Controller {
    private final Server server;
    private final EventDispatcher dispatcher;
    private final Game game;

    @Inject
    public RenderController(final EventDispatcher eventDispatcher, final Server server, final Game aGame) {
        this.dispatcher = eventDispatcher;
        this.server = server;
        this.game = aGame;

        this.game.waitForPlayers();
        this.onGameStateChanged(new GameWaitingEvent());
    }

    private void onGameStateChanged(Event event) {
        this.server.sendEvent(event, this);
    }

    @Override
    public void onSocketMessage(Event event) {
        this.dispatcher.dispatch(event);
    }

    @Override
    public void detach() {
        this.game.stop();
    }
}
