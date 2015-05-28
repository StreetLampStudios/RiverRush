package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.domain.event.AssetsLoadedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameStartedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;
import nl.tudelft.ti2806.riverrush.game.Game;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The render controller controls handles the game started and assets loaded events through
 * dispatching and attaching.
 */
@Singleton
public class RenderController implements Controller {
    private final EventDispatcher dispatcher;
    private final HandlerLambda<GameStartedEvent> onGameStartedLambda;
    private final HandlerLambda<AssetsLoadedEvent> onAssetsLoadedLambda;
    private final Game game;

    /**
     * Creates a render controller using the given game and event dispatcher.
     *
     * @param gm
     *            refers to the game that is to be controlled.
     * @param eventDispatcher
     *            refers to the dispatcher that sends and receives the relevant events.
     */
    @Inject
    public RenderController(final Game gm, final EventDispatcher eventDispatcher) {
        this.dispatcher = eventDispatcher;
        this.game = gm;
        this.onGameStartedLambda = (e) -> this.onGameStarted();
        this.onAssetsLoadedLambda = (e) -> this.onAssetsLoaded();
        this.dispatcher.attach(GameStartedEvent.class, this.onGameStartedLambda);
        this.dispatcher.attach(AssetsLoadedEvent.class, this.onAssetsLoadedLambda);
    }

    @Override
    public void initialize() {
        // Does not need to initialise
    }

    /**
     * Handles the assets Loaded event through sending a command to the game.
     *
     * @param givenEvent
     *            the event that was passed through the assets loaded message.
     */
    private void onAssetsLoaded() {
        this.game.waitForPlayers();
    }

    /**
     * Handles the game started event through sending a command to the game.
     *
     * @param givenEvent
     *            the event that was passed through the assets loaded message.
     */
    private void onGameStarted() {
        this.game.start();
    }

    @Override
    public void onSocketMessage(final Event givenEvent) {
        this.dispatcher.dispatch(givenEvent);
    }

    @Override
    public void dispose() {
        this.dispatcher.detach(GameStartedEvent.class, this.onGameStartedLambda);
        this.dispatcher.detach(AssetsLoadedEvent.class, this.onAssetsLoadedLambda);
    }
}
