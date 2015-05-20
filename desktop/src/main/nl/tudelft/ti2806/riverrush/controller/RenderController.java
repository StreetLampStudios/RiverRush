package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.domain.event.AssetsLoadedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameStartedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;
import nl.tudelft.ti2806.riverrush.game.Game;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class RenderController implements Controller {
    private final EventDispatcher dispatcher;
    private final HandlerLambda<GameStartedEvent> onGameStartedLambda;
    private final HandlerLambda<AssetsLoadedEvent> onAssetsLoadedLambda;
    private Game game;

    @Inject
    public RenderController(final Game game,
            final EventDispatcher eventDispatcher) {
        this.dispatcher = eventDispatcher;
        this.game = game;
        this.onGameStartedLambda = this::onGameStarted;
        this.onAssetsLoadedLambda = this::onAssetsLoaded;
        this.dispatcher.attach(
            GameStartedEvent.class,
            this.onGameStartedLambda);
        this.dispatcher.attach(
            AssetsLoadedEvent.class,
            this.onAssetsLoadedLambda);
    }

    private void onAssetsLoaded(Event event) {
        this.game.waitForPlayers();
    }

    private void onGameStarted(Event event) {
        this.game.start();
    }

    @Override
    public void onSocketMessage(final Event event) {
        this.dispatcher.dispatch(event);
    }

    @Override
    public void detach() {
        this.dispatcher
                .detach(GameStartedEvent.class, this.onGameStartedLambda);
        this.dispatcher.detach(AssetsLoadedEvent.class,
                this.onAssetsLoadedLambda);
    }
}
