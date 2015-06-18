package nl.tudelft.ti2806.riverrush.game.state;

import com.badlogic.gdx.Gdx;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalAddedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalRemovedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameAboutToStartEvent;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.graphics.entity.Animal;
import nl.tudelft.ti2806.riverrush.graphics.entity.Team;
import nl.tudelft.ti2806.riverrush.screen.WaitingScreen;

/**
 * State for a waiting game.
 */
public class WaitingGameState extends AbstractGameState {

    private final WaitingScreen screen;

    private final HandlerLambda<GameAboutToStartEvent> timerHandler = (e) -> this.startTimer(e.getSeconds());
    private final HandlerLambda<AnimalAddedEvent> addAnimalHandler = this::addAnimalHandler;
    private final HandlerLambda<AnimalRemovedEvent> removeAnimalHandler = this::removeAnimalHandler;


    /**
     * The state of the game that indicates that the game is waiting for players. In this state the
     * game can be started when enough players have connected.
     *
     * @param eventDispatcher the dispatcher that is used to handle any relevant events for the game in this
     *                        state.
     * @param game            refers to the game that this state belongs to.
     */
    public WaitingGameState(final EventDispatcher eventDispatcher,
                            final Game game) {
        super(eventDispatcher, game);

        this.dispatcher.attach(AnimalAddedEvent.class, this.addAnimalHandler);
        this.dispatcher.attach(GameAboutToStartEvent.class, this.timerHandler);
        this.dispatcher.attach(AnimalRemovedEvent.class, this.removeAnimalHandler);
        this.screen = new WaitingScreen(eventDispatcher);

        Gdx.app.postRunnable(
            () -> WaitingGameState.this.game.setScreen(WaitingGameState.this.screen)
        );
    }

    /**
     * Starts the timer of 5 seconds.
     * @param seconds - amount of seconds that we need to wait until the game will start
     */
    private void startTimer(int seconds) {
        this.screen.startTimer(seconds);
    }

    @Override
    public void dispose() {
        this.dispatcher.detach(GameAboutToStartEvent.class, this.timerHandler);
        this.dispatcher.detach(AnimalAddedEvent.class, this.addAnimalHandler);
        this.dispatcher.detach(AnimalRemovedEvent.class, this.removeAnimalHandler);
        this.screen.dispose();
    }

    @Override
    public Event getStateEvent() {
        return null;
    }

    @Override
    public GameState start() {
        this.dispose();
        return new PlayingGameState(this.dispatcher, this.game);
    }

    @Override
    public GameState stop() {
        this.dispose();
        return new StoppedGameState(this.dispatcher, this.game);
    }

    @Override
    public GameState finish(final Integer team) {
        return this;
    }

    @Override
    public GameState waitForPlayers() {
        return this;
    }

    /**
     * Add an animal.
     *
     * @param event The add event
     */
    public void addAnimalHandler(final AnimalAddedEvent event) {

        Integer tm = event.getTeam();
        Team tim = this.game.getTeam(tm);
        if (tim == null) {
            tim = this.game.addTeam(tm);
        }
        Integer variation = event.getVariation();
        tim.addAnimal(new Animal(this.dispatcher, event.getAnimal(), tm, variation, event.getSector()));

        this.screen.addConnection();
    }

    /**
     * Is called when an animal is removed.
     *
     * @param animalRemovedEvent - The event
     */
    private void removeAnimalHandler(final AnimalRemovedEvent animalRemovedEvent) {
        this.screen.removeConnection();
    }
}
