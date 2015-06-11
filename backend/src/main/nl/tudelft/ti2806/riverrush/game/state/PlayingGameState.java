package nl.tudelft.ti2806.riverrush.game.state;

import nl.tudelft.ti2806.riverrush.domain.event.*;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.event.JumpCommand;

/**
 * State when the game is ongoing.
 */
public class PlayingGameState implements GameState {

    private final EventDispatcher dispatcher;
    private Game game;
    private final HandlerLambda<AnimalCollidedEvent> animalCollidedLambda;

    /**
     * The game transitions to this state when the game starts.
     *
     * @param eventDispatcher The dispatcher used to listen to {@link JumpCommand}.
     * @param gme
     */
    public PlayingGameState(final EventDispatcher eventDispatcher, final Game gme) {
        this.dispatcher = eventDispatcher;
        this.game = gme;

        this.animalCollidedLambda = (e) -> this.game.collideAnimal(e.getAnimal(), e.getTeam());

        this.dispatcher.dispatch(new GameStartedEvent());
        this.dispatcher.attach(AnimalCollidedEvent.class, this.animalCollidedLambda);
    }

    @Override
    public void dispose() {
        // Is supposed to be empty
    }

    @Override
    public GameState start() {
        return this;
    }

    @Override
    public GameState stop() {
        this.dispose();
        return new StoppedGameState(this.dispatcher, this.game);
    }

    @Override
    public GameState finish(Integer team) {
        this.dispose();

        GameFinishedEvent event = new GameFinishedEvent();
        event.setTeam(team);
        this.dispatcher.dispatch(event);

        return new FinishedGameState(this.dispatcher, this.game);
    }

    @Override
    public GameState waitForPlayers() {
        return this;
    }
}
