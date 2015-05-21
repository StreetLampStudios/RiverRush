package nl.tudelft.ti2806.riverrush.controller;

import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.domain.event.*;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.Server;
import nl.tudelft.ti2806.riverrush.network.UserServer;

public class PlayerController extends AbstractController {

    private final Player player;
    private final EventDispatcher dispatcher;
    private final Server server;
    private final Game game;

    @Inject
    public PlayerController(final EventDispatcher aDispatcher, final UserServer aServer, final Game aGame) {
        super(aDispatcher);
        this.player = new Player();
        this.dispatcher = aDispatcher;
        this.server = aServer;
        this.game = aGame;
    }

    @Override
    public void initialize() {
        this.listenTo(GameAboutToStartEvent.class, this::onGameStateChange);
        this.listenTo(GameStartedEvent.class, this::onGameStateChange);
        this.listenTo(GameStoppedEvent.class, this::onGameStateChange);
        this.listenTo(GameFinishedEvent.class, this::onGameStateChange);
        this.listenTo(GameWaitingEvent.class, this::onGameStateChange);

        PlayerAddedEvent event = new PlayerAddedEvent();
        event.setPlayer(this.player);

        this.dispatcher.dispatch(event);
    }

    private void onGameStateChange(final Event event) {
        server.sendEvent(event, this);
    }

}
