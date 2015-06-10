package nl.tudelft.ti2806.riverrush.game.state;

import nl.tudelft.ti2806.riverrush.domain.event.AnimalCollidedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameStartedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.event.JumpCommand;

/**
 * State when the game is ongoing.
 */
public class PlayingGameState implements GameState {

    private final EventDispatcher eventDispatcher;
    private Game game;
    private final HandlerLambda<AnimalCollidedEvent> animalCollidedLambda;

    /**
     * The game transitions to this state when the game starts.
     *
     * @param dispatcher The dispatcher used to listen to {@link JumpCommand}.
     * @param gme
     */
    public PlayingGameState(final EventDispatcher dispatcher, final Game gme) {
        this.eventDispatcher = dispatcher;
        this.game = gme;



        this.animalCollidedLambda = (e) -> this.game.collideAnimal(e.getAnimal(), e.getTeam());

        this.eventDispatcher.dispatch(new GameStartedEvent());
        this.eventDispatcher.attach(AnimalCollidedEvent.class, this.animalCollidedLambda);
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
        return new StoppedGameState(this.eventDispatcher, this.game);
    }

    @Override
    public GameState finish() {
        this.dispose();
        return new FinishedGameState(this.eventDispatcher, this.game);
    }

    @Override
    public GameState waitForPlayers() {
        return this;
    }
}
