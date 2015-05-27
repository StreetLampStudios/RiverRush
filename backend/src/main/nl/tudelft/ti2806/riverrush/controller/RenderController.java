package nl.tudelft.ti2806.riverrush.controller;

import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.domain.event.*;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.AbstractServer;

public class RenderController extends AbstractController {
    private final AbstractServer server;
    private final Game game;

    @Inject
    public RenderController(final EventDispatcher eventDispatcher, final AbstractServer server, final Game aGame) {
        super(eventDispatcher);
        this.server = server;
        this.game = aGame;
    }

    @Override
    public void initialize() {
        HandlerLambda<Event> onGameStateChangedLambda = (e) -> this.server.sendEvent(e, this);

        this.listenTo(PlayerAddedEvent.class, onGameStateChangedLambda);
        this.listenTo(GameWaitingEvent.class, onGameStateChangedLambda);
        this.listenTo(GameAboutToStartEvent.class, onGameStateChangedLambda);
        this.listenTo(GameStartedEvent.class, onGameStateChangedLambda);
        this.listenTo(GameFinishedEvent.class, onGameStateChangedLambda);
        this.listenTo(GameStoppedEvent.class, onGameStateChangedLambda);
        this.listenTo(PlayerJumpedEvent.class, onGameStateChangedLambda);
        this.listenTo(AnimalFellOff.class, onGameStateChangedLambda);

        this.game.waitForPlayers();
    }
}
