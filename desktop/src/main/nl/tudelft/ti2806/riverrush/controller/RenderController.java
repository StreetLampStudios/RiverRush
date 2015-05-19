package nl.tudelft.ti2806.riverrush.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import nl.tudelft.ti2806.riverrush.domain.event.*;
import nl.tudelft.ti2806.riverrush.graphics.RiverGame;

@Singleton
public class RenderController implements Controller {
    private final EventDispatcher dispatcher;
    private final HandlerLambda onGameWaitingLambda;
    private final HandlerLambda onGameAboutToStartLambda;
    private final HandlerLambda onGameStartedLambda;
    private final HandlerLambda onAssetsLoadedLambda;
    private RiverGame game;

    @Inject
    public RenderController(final RiverGame game, final EventDispatcher eventDispatcher) {
        this.dispatcher = eventDispatcher;
        this.game = game;
        this.onGameWaitingLambda = this::onGameWaiting;
        this.onGameAboutToStartLambda = this::onGameAboutToStart;
        this.onGameStartedLambda = this::onGameStarted;
        this.onAssetsLoadedLambda = this::onAssetsLoaded;
        this.dispatcher.attach(GameWaitingEvent.class, onGameWaitingLambda);
        this.dispatcher.attach(GameAboutToStartEvent.class, onGameAboutToStartLambda);
        this.dispatcher.attach(GameStartedEvent.class, onGameStartedLambda);
        this.dispatcher.attach(AssetsLoadedEvent.class, onAssetsLoadedLambda);
    }

    private void onAssetsLoaded(Event event) {
        game.loadWaitingScreen();
    }

    private void onGameWaiting(Event event) {

    }

    private void onGameAboutToStart(Event event) {
        game.startTimer();
    }

    private void onGameStarted(Event event) {
        game.loadGameScreen();
    }

    @Override
    public void onSocketMessage(final Event event) {
        this.dispatcher.dispatch(event);
    }

    @Override
    public void detach() {
        this.dispatcher.detach(GameWaitingEvent.class, onGameWaitingLambda);
    }
}
